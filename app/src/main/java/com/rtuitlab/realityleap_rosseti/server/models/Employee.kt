package com.rtuitlab.realityleap_rosseti.server.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.io.Serializable

@IgnoreExtraProperties
data class Employee(
    val id: String = "",
    val role: String = "",
    @get:PropertyName("first_name") @set:PropertyName("first_name") var firstName: String = "",
    @get:PropertyName("middle_name") @set:PropertyName("middle_name") var middleName: String = "",
    @get:PropertyName("last_name") @set:PropertyName("last_name") var lastName: String = "",
    @get:PropertyName("safety_group") @set:PropertyName("safety_group") var safetyGroup: String = "",
    val hash: String = ""
): Serializable {
    fun toInitials() = "$lastName ${firstName.first()}.${middleName.first()}."
}