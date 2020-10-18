package com.rtuitlab.realityleap_rosseti.viewmodels

import androidx.lifecycle.ViewModel
import com.rtuitlab.realityleap_rosseti.persistence.Storage
import com.rtuitlab.realityleap_rosseti.server.InspectRepository
import com.rtuitlab.realityleap_rosseti.server.models.Defect
import com.rtuitlab.realityleap_rosseti.server.models.InspectionResult

class InspectViewModel(
		private val inspectRepo: InspectRepository,
		private val storage: Storage
) : ViewModel() {

	fun addInspectionResult() {
		inspectRepo.addInspectionResult(getSavedResult())
		storage.deleteCurrentResult()
	}

	fun saveDefects(newDefects: List<Defect>) = storage.storeResult(
		getSavedResult().apply { defects = newDefects }
	)

	fun getSavedDefects() = getSavedResult().defects

	private fun getSavedResult() = (storage.getSave() as InspectionResult)
}