package com.rtuitlab.realityleap_rosseti.viewmodels

import androidx.lifecycle.ViewModel
import com.rtuitlab.realityleap_rosseti.server.InspectRepository
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask

class InspectViewModel(
		inspectRepo: InspectRepository,
		private val task: InspectionTask
) : ViewModel() {

}