package com.rtuitlab.realityleap_rosseti.server.models

import androidx.core.net.toUri
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class LatLng(
	val lng: Double = 0.0,
	val lat: Double = 0.0
): Serializable {
	fun toUri() = "geo:$lat,$lng".toUri()
}