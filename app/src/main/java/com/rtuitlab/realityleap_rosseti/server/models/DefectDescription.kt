package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class DefectDescription(
	val text: String = "",
	@PropertyName("photo_urls") val photoUrls: List<String> = listOf()
)