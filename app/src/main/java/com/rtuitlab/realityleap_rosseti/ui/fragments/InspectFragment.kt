package com.rtuitlab.realityleap_rosseti.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.extensions.mainActivity
import com.rtuitlab.realityleap_rosseti.recyclers.defects.DefectsAdapter
import com.rtuitlab.realityleap_rosseti.recyclers.inspection_tasks.InspectionTasksAdapter
import com.rtuitlab.realityleap_rosseti.server.models.Defect
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask
import com.rtuitlab.realityleap_rosseti.ui.dialogs.AddDefectDialog
import com.rtuitlab.realityleap_rosseti.ui.dialogs.AddDefectDialog.Companion.DEFECT_KEY
import com.rtuitlab.realityleap_rosseti.viewmodels.InspectViewModel
import kotlinx.android.synthetic.main.fragment_inspect.*
import kotlinx.android.synthetic.main.fragment_inspection_tasks.*
import kotlinx.android.synthetic.main.fragment_inspection_tasks.recyclerView
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InspectFragment : Fragment(), DefectsAdapter.OnDefectClickListener {

	private companion object {
		const val DEFECT_REQUEST_KEY = "DEFECT_REQUEST_KEY"
	}

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
		setListeners()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		recyclerAdapter = null
	}

	override fun onDefectClicked(defect: Defect) { showDefectEditDialog(defect) }

	private fun initRecyclerView() {
		recyclerAdapter = get<DefectsAdapter>().apply {
			onDefectClickListener = this@InspectFragment
		}
		recyclerView.adapter = recyclerAdapter
	}

	private fun extractInspectionTask() = requireArguments().getSerializable(TaskFragment.INSPECTION_TASK_KEY) as InspectionTask

	private fun setListeners() {
		addDefectBtn.setOnClickListener { showDefectEditDialog() }

		setFragmentResultListener(DEFECT_REQUEST_KEY) { _, bundle ->
			processDefect(bundle.getSerializable(DEFECT_KEY) as Defect)
		}
	}

	private fun showDefectEditDialog(defect: Defect? = null) {
		AddDefectDialog.newInstance(
			DEFECT_REQUEST_KEY,
			defect
		).show(parentFragmentManager, null)
	}

	private fun processDefect(defect: Defect) {
		recyclerAdapter?.let {  defectsAdapter ->
			if (defect.id.isEmpty()) {
				defectsAdapter.defectsList = defectsAdapter.defectsList.toMutableList().apply {
					add(defect.apply { id = defectsAdapter.defectsList.size.toString() })
				}.toList()
			} else {
				defectsAdapter.defectsList = defectsAdapter.defectsList.toMutableList().apply {
					set(defect.id.toInt(), defect)
				}.toList()
			}
		}
	}
}