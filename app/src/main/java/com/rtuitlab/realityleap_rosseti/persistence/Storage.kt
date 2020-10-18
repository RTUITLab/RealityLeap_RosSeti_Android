package com.rtuitlab.realityleap_rosseti.persistence

import android.content.Context
import com.rtuitlab.realityleap_rosseti.server.models.Employee

class Storage(
    context: Context
) {

    var currentUser = Employee()

    private companion object {
        const val PREFERENCES_KEY = "PREFERENCES_KEY"
    }

    private val prefs = context.getSharedPreferences(
        PREFERENCES_KEY,
        Context.MODE_PRIVATE
    )

}