package com.rtuitlab.realityleap_rosseti.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.extensions.mainActivity
import com.rtuitlab.realityleap_rosseti.recyclers.inspection_tasks.InspectionTasksAdapter
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask
import com.rtuitlab.realityleap_rosseti.viewmodels.InspectionTasksViewModel
import kotlinx.android.synthetic.main.fragment_inspection_tasks.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class InspectionTasksFragment: Fragment(), InspectionTasksAdapter.OnInspectionClickListener {

    private val viewModel: InspectionTasksViewModel by viewModel()

    private var recyclerAdapter: InspectionTasksAdapter? = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_inspection_tasks, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity().title = getString(R.string.inspection_tasks)
        initRecyclerView()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerAdapter = null
    }

    override fun onInspectionClicked(inspectionTask: InspectionTask) {

    }

    private fun initRecyclerView() {
        recyclerView.adapter = recyclerAdapter?.apply {
            onInspectionClickListener = this@InspectionTasksFragment
        }
    }

    private fun setObservers() {
        viewModel.inspectionTasksLiveData.observe(viewLifecycleOwner) {
            recyclerAdapter?.inspectionTasksList = it
            progressBar.isVisible = false
        }
    }
}