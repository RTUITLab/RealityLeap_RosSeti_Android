package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Defect(
	val id: String = "",
	val location: String = "",
	@get:PropertyName("equipment_type") @set:PropertyName("equipment_type") var equipmentType: String = "",
	val description: DefectDescription = DefectDescription(),
	@get:PropertyName("elimination_time") @set:PropertyName("elimination_time") var eliminationTime: Long? = null,
	@get:PropertyName("critical_score") @set:PropertyName("critical_score") var criticalScore: Int? = null
)