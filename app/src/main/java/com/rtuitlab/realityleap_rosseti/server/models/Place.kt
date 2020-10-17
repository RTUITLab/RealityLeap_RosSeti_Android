package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Place(
	val id: String = "",
	val name: String = "",
	val location: LatLng = LatLng()
): Serializable