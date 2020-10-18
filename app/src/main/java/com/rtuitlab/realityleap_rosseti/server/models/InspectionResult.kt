package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.io.Serializable

@IgnoreExtraProperties
data class InspectionResult(
	var id: String = "",
	@get:PropertyName("inspection_task") @set:PropertyName("inspection_task") var inspectionTask: InspectionTask = InspectionTask(),
	var defects: List<Defect> = listOf(),
	@get:PropertyName("start_time") @set:PropertyName("start_time") var startTime: Long = 0,
	@get:PropertyName("finish_time") @set:PropertyName("finish_time") var finishTime: Long = 0,
	@get:PropertyName("approve_time") @set:PropertyName("approve_time") var approveTime: Long? = null
): Serializable