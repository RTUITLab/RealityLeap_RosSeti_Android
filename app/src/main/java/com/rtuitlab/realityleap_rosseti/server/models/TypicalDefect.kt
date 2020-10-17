package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class TypicalDefect(
	val text: String = "",
	@get:PropertyName("critical_score") @set:PropertyName("critical_score") var criticalScore: Int = 0
)