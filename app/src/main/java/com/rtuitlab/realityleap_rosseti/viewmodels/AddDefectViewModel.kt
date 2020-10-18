package com.rtuitlab.realityleap_rosseti.viewmodels

import androidx.lifecycle.ViewModel
import com.rtuitlab.realityleap_rosseti.server.models.Defect
import com.rtuitlab.realityleap_rosseti.server.models.DefectDescription

class AddDefectViewModel(
	defect: Defect?
) : ViewModel() {

	private val defectId = defect?.id
	var location = defect?.location ?: ""
	var equipType = defect?.equipmentType ?: ""
	var description = defect?.description?.text ?: ""

	fun isDataValid() = location.isNotBlank() && equipType.isNotBlank() && description.isNotBlank()

	fun generateDefect() = Defect(
		defectId ?: "",
		location,
		equipType,
		DefectDescription(description)
	)
}