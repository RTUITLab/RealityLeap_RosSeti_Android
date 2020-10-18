package com.rtuitlab.realityleap_rosseti.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.databinding.DialogAddDefectBinding
import com.rtuitlab.realityleap_rosseti.extensions.argument
import com.rtuitlab.realityleap_rosseti.server.models.Defect
import com.rtuitlab.realityleap_rosseti.viewmodels.AddDefectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AddDefectDialog: DialogFragment() {

	companion object {
		const val DEFECT_KEY = "DEFECT_KEY"

		fun newInstance(requestKey: String, defect: Defect? = null): AddDefectDialog {
			return AddDefectDialog().apply {
				arguments = bundleOf(DEFECT_KEY to defect)
				this.requestKey = requestKey
			}
		}
	}

	private var requestKey: String by argument()

	private val viewModel: AddDefectViewModel by viewModel{ parametersOf(extractDefect()) }

	private lateinit var positiveBtn: Button

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val binding = DialogAddDefectBinding.inflate(
				requireActivity().layoutInflater,
				null,
				false
		)
		binding.viewModel = viewModel
		binding.dialog = this
		return MaterialAlertDialogBuilder(requireContext())
				.setTitle(R.string.add_defect)
				.setView(binding.root)
				.setPositiveButton(R.string.save, null)
				.setNegativeButton(R.string.cancel, null)
				.create()
				.apply {
					setOnShowListener {
						positiveBtn = getButton(AlertDialog.BUTTON_POSITIVE)
						positiveBtn.isEnabled = false
						positiveBtn.setOnClickListener {
							setDefectResult()
							currentFocus?.let {
								val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
								imm?.hideSoftInputFromWindow(it.windowToken, 0)
							}
							dismiss()
						}
					}
				}
	}

	private fun setDefectResult() {
		setFragmentResult(
			requestKey,
			bundleOf(DEFECT_KEY to viewModel.generateDefect())
		)
	}

	private fun extractDefect() = requireArguments().getSerializable(DEFECT_KEY) as Defect?

	fun checkData() {
		positiveBtn.isEnabled = viewModel.isDataValid()
	}
}