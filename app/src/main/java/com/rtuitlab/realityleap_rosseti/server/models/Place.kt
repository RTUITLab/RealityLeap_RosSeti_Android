package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Place(
	val id: String = "",
	val name: String = "",
	val location: LatLng = LatLng()
)