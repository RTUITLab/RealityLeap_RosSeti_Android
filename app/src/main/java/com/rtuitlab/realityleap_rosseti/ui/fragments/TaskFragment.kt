package com.rtuitlab.realityleap_rosseti.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.databinding.FragmentTaskBinding
import com.rtuitlab.realityleap_rosseti.extensions.longToast
import com.rtuitlab.realityleap_rosseti.extensions.mainActivity
import com.rtuitlab.realityleap_rosseti.persistence.Storage
import com.rtuitlab.realityleap_rosseti.server.models.InspectionResult
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask
import com.rtuitlab.realityleap_rosseti.viewmodels.TaskViewModel
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TaskFragment : Fragment() {

    companion object {
        const val INSPECTION_TASK_KEY = "inspectionTask"
    }

    private val storage: Storage by inject()

    private val viewModel: TaskViewModel by viewModel{ parametersOf(extractInspectionTask()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTaskBinding>(
            inflater,
            R.layout.fragment_task,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSave()
        mainActivity().title = getString(R.string.inspection_task)
        mainActivity().navigateButtonVisible(true)
        setListeners()
    }

    override fun onPause() {
        super.onPause()
        startBtn.isEnabled = true
    }

    private fun checkSave() {
        val save = storage.getSave()
        if (save is InspectionTask && save.id == viewModel.task.id) {
            startBtn.text = getString(R.string.start_task)
        }
    }

    private fun setListeners() {
        locationBtn.setOnClickListener { intentOpenMap() }
        startBtn.setOnClickListener {
            if (startBtn.text == getString(R.string.take_task)) {
                takeTask()
            } else {
                storage.deleteCurrentTask()
                storage.storeResult(InspectionResult(
                    inspectionTask = viewModel.task,
                    startTime = System.currentTimeMillis()
                ))
                navigateToInspect()
            }
        }
    }

    private fun extractInspectionTask() = requireArguments().getSerializable(INSPECTION_TASK_KEY) as InspectionTask

    private fun intentOpenMap() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = viewModel.task.place.location.toUri()
        }
        startActivity(intent)
    }

    private fun takeTask() {
        requireContext().longToast(getString(R.string.take_task_notify)).show()
        startBtn.text = getString(R.string.start_task)
        startBtn.isEnabled = false
        lifecycleScope.launch {
            delay(3000)
            startBtn.isEnabled = true
        }
        storage.storeTask(viewModel.task)
    }

    private fun navigateToInspect() = findNavController().navigate(
        R.id.action_taskFragment_to_inspectFragment
    )
}