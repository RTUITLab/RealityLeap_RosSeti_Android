package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class TypicalDefect(
	val text: String = "",
	@PropertyName("critical_score") val criticalScore: Int = 0
)