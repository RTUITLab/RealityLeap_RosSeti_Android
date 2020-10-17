package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LatLng(
	val lng: Double = 0.0,
	val lat: Double = 0.0
)