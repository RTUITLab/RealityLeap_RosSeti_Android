package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class InspectionTask(
	val id: String = "",
	val place: Place = Place(),
	@PropertyName("safety_event") val safetyEvent: String = "",
	val creator: Employee = Employee(),
	val executor: Employee = Employee()
)