package com.rtuitlab.realityleap_rosseti.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rtuitlab.realityleap_rosseti.persistence.Storage
import com.rtuitlab.realityleap_rosseti.server.EmployeesRepository
import com.rtuitlab.realityleap_rosseti.utils.SingleLiveEvent

class ScannerViewModel(
	employeesRepo: EmployeesRepository,
	private val storage: Storage
): ViewModel() {

	private val _authResultLiveData = SingleLiveEvent<Boolean>()
	val authResultLiveData: LiveData<Boolean> = _authResultLiveData

	val employeesLiveData = employeesRepo.employeesLiveData

	var userHash = ""
		set(value) {
			field = value
			employeesLiveData.value?.let { employees ->
				employees.find { it.hash == value }?.let {
					storage.currentUser = it
					_authResultLiveData.value = true
					Log.i("Current user", storage.currentUser.toString())
				}
			}
		}


}