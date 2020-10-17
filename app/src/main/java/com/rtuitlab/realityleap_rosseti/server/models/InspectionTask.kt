package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.io.Serializable

@IgnoreExtraProperties
data class InspectionTask(
	val id: String = "",
	val place: Place = Place(),
	@get:PropertyName("safety_event") @set:PropertyName("safety_event") var safetyEvent: String = "",
	val creator: Employee = Employee(),
	val executor: Employee = Employee()
): Serializable