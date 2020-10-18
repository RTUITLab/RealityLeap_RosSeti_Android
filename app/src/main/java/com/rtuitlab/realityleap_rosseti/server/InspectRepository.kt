package com.rtuitlab.realityleap_rosseti.server

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.rtuitlab.realityleap_rosseti.server.models.InspectionResult
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask

class InspectRepository(
	private val firebase: FirebaseDatabase
) {
	private companion object {
		const val INSPECTION_RESULTS_KEY = "inspection_results"
		const val TASKS_REFERENCE_KEY = "inspection_tasks"
	}

	fun addInspectionResult(result: InspectionResult) {
		removeTask(result.inspectionTask)
		firebase.getReference(INSPECTION_RESULTS_KEY).push().apply {
			setValue(result.apply {
				id = key!!
				finishTime = System.currentTimeMillis()
			})
		}
	}

	private fun removeTask(task: InspectionTask) {
		firebase.getReference(TASKS_REFERENCE_KEY).addListenerForSingleValueEvent(object :
			ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {
				if (snapshot.exists()) {
					snapshot.children.forEach {
						if (it.getValue<InspectionTask>()?.id == task.id) {
							it.ref.removeValue()
						}
					}
					snapshot.getValue<List<InspectionTask>>()
				}
			}
			override fun onCancelled(error: DatabaseError) {}
		})
	}
}