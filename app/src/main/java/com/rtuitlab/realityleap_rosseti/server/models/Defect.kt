package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Defect(
	val id: String = "",
	val location: String = "",
	@PropertyName("equipment_type") val equipmentType: String = "",
	val description: DefectDescription = DefectDescription(),
	@PropertyName("elimination_time") val eliminationTime: Long? = null,
	@PropertyName("critical_score") val criticalScore: Int? = null
)