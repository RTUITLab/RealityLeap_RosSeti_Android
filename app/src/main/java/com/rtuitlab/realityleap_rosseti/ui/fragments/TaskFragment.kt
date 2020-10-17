package com.rtuitlab.realityleap_rosseti.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.databinding.FragmentTaskBinding
import com.rtuitlab.realityleap_rosseti.extensions.mainActivity
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask
import com.rtuitlab.realityleap_rosseti.utils.ChangeLocationListener
import com.rtuitlab.realityleap_rosseti.viewmodels.TaskViewModel
import kotlinx.android.synthetic.main.fragment_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TaskFragment : Fragment() {

    companion object {
        const val INSPECTION_TASK_KEY = "inspectionTask"
        private const val MIN_TIME_DIFF_FOR_UPDATE = 2000L
        private const val MIN_DISTANCE_FOR_UPDATE = 100F
    }

    private val viewModel: TaskViewModel by viewModel{ parametersOf(extractInspectionTask()) }

    private val changeLocationListener = object : ChangeLocationListener() {
        override fun onLocationChanged(location: Location?) {
            viewModel.currentLocation = location
        }
    }

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
        mainActivity().title = getString(R.string.inspection_task)
        mainActivity().navigateButtonVisible(true)
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        requestLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        removeLocationUpdates()
    }

    private fun setListeners() {
        locationBtn.setOnClickListener { intentOpenMap() }
    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) { return }
        requireActivity().getSystemService(LocationManager::class.java)?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            MIN_TIME_DIFF_FOR_UPDATE,
            MIN_DISTANCE_FOR_UPDATE,
            changeLocationListener
        )
    }

    private fun removeLocationUpdates() {
        requireActivity().getSystemService(LocationManager::class.java)?.removeUpdates(changeLocationListener)
    }

    private fun extractInspectionTask() = requireArguments().getSerializable(INSPECTION_TASK_KEY) as InspectionTask

    private fun intentOpenMap() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = viewModel.task.place.location.toUri()
        }
        startActivity(intent)
    }
}