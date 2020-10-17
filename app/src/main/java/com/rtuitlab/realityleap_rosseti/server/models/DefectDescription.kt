package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class DefectDescription(
	val text: String = "",
	@get:PropertyName("photo_urls") @set:PropertyName("photo_urls") var photoUrls: List<String> = listOf()
)