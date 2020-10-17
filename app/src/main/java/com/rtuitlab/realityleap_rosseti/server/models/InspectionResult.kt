package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class InspectionResult(
	val id: String = "",
	@PropertyName("inspection_task") val inspectionTask: InspectionTask = InspectionTask(),
	val defects: List<Defect> = listOf(),
	@PropertyName("start_time") val startTime: Long = 0,
	@PropertyName("finish_time") val finishTime: Long = 0,
	@PropertyName("approve_time") val approveTime: Long? = null
)