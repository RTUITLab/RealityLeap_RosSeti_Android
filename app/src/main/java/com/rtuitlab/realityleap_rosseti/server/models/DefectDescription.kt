package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.io.Serializable

@IgnoreExtraProperties
data class DefectDescription(
	val text: String = "",
	@get:PropertyName("photo_urls") @set:PropertyName("photo_urls") var photoUrls: List<String> = listOf(),
	@Exclude var photoPaths: List<String> = listOf()
): Serializable