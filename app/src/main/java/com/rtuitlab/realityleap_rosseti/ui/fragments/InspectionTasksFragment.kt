package com.rtuitlab.realityleap_rosseti.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.extensions.mainActivity
import com.rtuitlab.realityleap_rosseti.persistence.Storage
import com.rtuitlab.realityleap_rosseti.recyclers.inspection_tasks.InspectionTasksAdapter
import com.rtuitlab.realityleap_rosseti.server.models.InspectionResult
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask
import com.rtuitlab.realityleap_rosseti.ui.fragments.TaskFragment.Companion.INSPECTION_TASK_KEY
import com.rtuitlab.realityleap_rosseti.viewmodels.InspectionTasksViewModel
import kotlinx.android.synthetic.main.fragment_inspection_tasks.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class InspectionTasksFragment: Fragment(), InspectionTasksAdapter.OnInspectionClickListener {

    private val storage: Storage by inject()

    private val viewModel: InspectionTasksViewModel by viewModel()

    private var recyclerAdapter: InspectionTasksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_inspection_tasks, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSave()
        mainActivity().title = getString(R.string.inspection_tasks)
        mainActivity().navigateButtonVisible(false)
        initRecyclerView()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerAdapter = null
    }

    override fun onInspectionClicked(inspectionTask: InspectionTask) = navigateToTask(inspectionTask)

    private fun checkSave() {
        when(val save = storage.getSave()) {
            is InspectionTask -> navigateToTask(save, false)
            is InspectionResult -> navigateToInspect()
        }
    }

    private fun initRecyclerView() {
        recyclerAdapter = get<InspectionTasksAdapter>().apply {
            onInspectionClickListener = this@InspectionTasksFragment
        }
        recyclerView.adapter = recyclerAdapter

        viewModel.inspectionTasksLiveData.value?.let { updateTasksList(it) }
    }

    private fun setObservers() {
        viewModel.inspectionTasksLiveData.observe(viewLifecycleOwner) { updateTasksList(it) }
    }

    private fun updateTasksList(newTasksList: List<InspectionTask>) {
        recyclerAdapter?.inspectionTasksList = newTasksList.filterNotNull().filter {
            it.executor.id == storage.currentUser.id
        }
        progressBar.isVisible = false
    }

    private fun navigateToTask(inspectionTask: InspectionTask, backEnabled: Boolean = true) {
        val navOptions = if (!backEnabled) {
            navOptions {
                popUpTo(R.id.inspectionsListFragment) { inclusive = true }
            }
        } else null
        findNavController().navigate(
            R.id.action_inspectionsListFragment_to_taskFragment,
            bundleOf(INSPECTION_TASK_KEY to inspectionTask),
            navOptions
        )
    }

    private fun navigateToInspect() = findNavController().navigate(
        R.id.action_inspectionsListFragment_to_inspectFragment
    )
}