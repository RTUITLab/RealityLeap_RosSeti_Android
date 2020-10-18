package com.rtuitlab.realityleap_rosseti.persistence

import android.content.Context
import com.google.gson.Gson
import com.rtuitlab.realityleap_rosseti.server.models.Employee
import com.rtuitlab.realityleap_rosseti.server.models.InspectionResult
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask
import java.io.Serializable

class Storage(context: Context) {

    var currentUser = Employee()

    private companion object {
        const val PREFERENCES_KEY = "PREFERENCES_KEY"
        const val CURRENT_TASK_KEY = "CURRENT_TASK_KEY"
        const val CURRENT_RESULT_KEY = "CURRENT_RESULT_KEY"
    }

    private val prefs = context.getSharedPreferences(
        PREFERENCES_KEY,
        Context.MODE_PRIVATE
    )

    fun getSave(): Serializable? {
        return prefs.getString(CURRENT_TASK_KEY, null)?.let {
            Gson().fromJson(it, InspectionTask::class.java)
        } ?: prefs.getString(CURRENT_RESULT_KEY, null)?.let {
            Gson().fromJson(it, InspectionResult::class.java)
        }
    }

    fun storeTask(task: InspectionTask) {
        prefs.edit().putString(CURRENT_TASK_KEY, Gson().toJson(task)).apply()
    }

    fun storeResult(result: InspectionResult) {
        prefs.edit().putString(CURRENT_RESULT_KEY, Gson().toJson(result)).apply()
    }

    fun deleteCurrentTask() = prefs.edit().remove(CURRENT_TASK_KEY).apply()

    fun deleteCurrentResult() = prefs.edit().remove(CURRENT_RESULT_KEY).apply()
}