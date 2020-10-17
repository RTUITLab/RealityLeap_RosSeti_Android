package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Employee(
    val id: String = "",
    val role: String = "",
    @PropertyName("first_name") val firstName: String = "",
    @PropertyName("middle_name") val middleName: String = "",
    @PropertyName("last_name") val lastName: String = "",
    @PropertyName("safety_group") val safetyGroup: String = ""
)