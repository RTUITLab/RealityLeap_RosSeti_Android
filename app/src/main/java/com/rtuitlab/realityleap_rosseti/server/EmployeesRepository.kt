package com.rtuitlab.realityleap_rosseti.server

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.rtuitlab.realityleap_rosseti.server.models.Employee
import com.rtuitlab.realityleap_rosseti.utils.SingleLiveEvent

class EmployeesRepository(
		firebase: FirebaseDatabase
) {
	private companion object {
		const val EMPLOYEES_REFERENCE_KEY = "employees"
	}

	private val _employeesLiveData = SingleLiveEvent<List<Employee>>()
	val employeesLiveData: LiveData<List<Employee>> = _employeesLiveData

	init {
		firebase.getReference(EMPLOYEES_REFERENCE_KEY).addValueEventListener(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {
				Log.i("EmployeesRepository", "#onDataChange")
				if (snapshot.exists()) {
					_employeesLiveData.postValue(snapshot.getValue<List<Employee>>())
				}
			}
			override fun onCancelled(error: DatabaseError) {}
		})
	}
}