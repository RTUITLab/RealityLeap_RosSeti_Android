package com.rtuitlab.realityleap_rosseti.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.extensions.mainActivity
import com.rtuitlab.realityleap_rosseti.recyclers.defects.DefectsAdapter
import com.rtuitlab.realityleap_rosseti.recyclers.inspection_tasks.InspectionTasksAdapter
import com.rtuitlab.realityleap_rosseti.server.models.Defect
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask
import com.rtuitlab.realityleap_rosseti.viewmodels.InspectViewModel
import kotlinx.android.synthetic.main.fragment_inspection_tasks.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InspectFragment : Fragment(), DefectsAdapter.OnDefectClickListener {

	private val viewModel: InspectViewModel by viewModel{ parametersOf(extractInspectionTask()) }

	private var recyclerAdapter: DefectsAdapter? = null

	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_inspect, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mainActivity().title = getString(R.string.search_defects)
		mainActivity().navigateButtonVisible(true)
		initRecyclerView()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		recyclerAdapter = null
	}

	override fun onDefectClicked(defect: Defect) {}

	private fun initRecyclerView() {
		recyclerAdapter = get<DefectsAdapter>().apply {
			onDefectClickListener = this@InspectFragment
		}
		recyclerView.adapter = recyclerAdapter
	}

	private fun extractInspectionTask() = requireArguments().getSerializable(TaskFragment.INSPECTION_TASK_KEY) as InspectionTask
}