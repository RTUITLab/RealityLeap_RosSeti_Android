package com.rtuitlab.realityleap_rosseti.server

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask
import com.rtuitlab.realityleap_rosseti.utils.SingleLiveEvent

class TasksRepository(
    firebase: FirebaseDatabase
) {
    private companion object {
        const val TASKS_REFERENCE_KEY = "inspection_tasks"
    }

    private val _inspectionTasksLiveData = SingleLiveEvent<List<InspectionTask>>()
    val inspectionTasksLiveData: LiveData<List<InspectionTask>> = _inspectionTasksLiveData

    init {
        firebase.getReference(TASKS_REFERENCE_KEY).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("TasksRepository", "#onDataChange")
                if (snapshot.exists()) {
                    _inspectionTasksLiveData.postValue(snapshot.getValue<List<InspectionTask>>())
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}