package com.rtuitlab.realityleap_rosseti.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.extensions.mainActivity
import com.rtuitlab.realityleap_rosseti.recyclers.defects.DefectsAdapter
import com.rtuitlab.realityleap_rosseti.server.models.Defect
import com.rtuitlab.realityleap_rosseti.ui.dialogs.AddDefectDialog
import com.rtuitlab.realityleap_rosseti.ui.dialogs.AddDefectDialog.Companion.DEFECT_KEY
import com.rtuitlab.realityleap_rosseti.ui.dialogs.YesNoDialog
import com.rtuitlab.realityleap_rosseti.ui.dialogs.YesNoDialog.Companion.RESULT_YES_NO_KEY
import com.rtuitlab.realityleap_rosseti.viewmodels.InspectViewModel
import kotlinx.android.synthetic.main.fragment_inspect.*
import kotlinx.android.synthetic.main.fragment_inspection_tasks.recyclerView
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel


class InspectFragment : Fragment(), DefectsAdapter.OnDefectClickListener {

	private companion object {
		const val DEFECT_REQUEST_KEY = "DEFECT_REQUEST_KEY"
		const val FINISH_INSPECT_REQUEST_KEY = "FINISH_INSPECT_REQUEST_KEY"
	}

	private val viewModel: InspectViewModel by viewModel()

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
		setHasOptionsMenu(true)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		recyclerAdapter = null
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.menu_finish_inspect, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.finish_inspect) {
			showFinishInspectConfirmationDialog()
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onDefectClicked(defect: Defect) { showDefectEditDialog(defect) }

	private fun initRecyclerView() {
		recyclerAdapter = get<DefectsAdapter>().apply {
			onDefectClickListener = this@InspectFragment
			defectsList = viewModel.getSavedDefects()
		}
		recyclerView.adapter = recyclerAdapter
	}

	private fun setListeners() {
		addDefectBtn.setOnClickListener { showDefectEditDialog() }

		setFragmentResultListener(DEFECT_REQUEST_KEY) { _, bundle ->
			processDefect(bundle.getSerializable(DEFECT_KEY) as Defect)
		}
		setFragmentResultListener(FINISH_INSPECT_REQUEST_KEY) { _, bundle ->
			if (bundle.getBoolean(RESULT_YES_NO_KEY)) {
				viewModel.addInspectionResult()
				navigateToTasks()
			}
		}
	}

	private fun showDefectEditDialog(defect: Defect? = null) {
		AddDefectDialog.newInstance(
			DEFECT_REQUEST_KEY,
			defect
		).show(parentFragmentManager, null)
	}

	private fun processDefect(defect: Defect) {
		recyclerAdapter?.let { defectsAdapter ->
			val newList = if (defect.id.isEmpty()) {
				defectsAdapter.defectsList.toMutableList().apply {
					add(defect.apply { id = defectsAdapter.defectsList.size.toString() })
				}
			} else {
				defectsAdapter.defectsList.toMutableList().apply {
					set(defect.id.toInt(), defect)
				}
			}.toList()
			defectsAdapter.defectsList = newList
			viewModel.saveDefects(newList)
		}
	}

	private fun showFinishInspectConfirmationDialog() {
		YesNoDialog.newInstance(
			FINISH_INSPECT_REQUEST_KEY,
			getString(R.string.finish_inspect_confirm)
		).show(parentFragmentManager, null)
	}

	private fun navigateToTasks() = findNavController().navigate(
		R.id.action_inspectFragment_to_inspectionsListFragment
	)
}