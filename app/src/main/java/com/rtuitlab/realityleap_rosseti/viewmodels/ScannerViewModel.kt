package com.rtuitlab.realityleap_rosseti.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rtuitlab.realityleap_rosseti.server.EmployeesRepository
import com.rtuitlab.realityleap_rosseti.utils.SingleLiveEvent
import com.rtuitlab.realityleap_rosseti.utils.currentUser

class ScannerViewModel(
		employeesRepo: EmployeesRepository
): ViewModel() {

	private val _authResultLiveData = SingleLiveEvent<Boolean>()
	val authResultLiveData: LiveData<Boolean> = _authResultLiveData

	val employeesLiveData = employeesRepo.employeesLiveData

	var userHash = ""
		set(value) {
			field = value
			employeesLiveData.value?.let { employees ->
				employees.find { it.hash == value }?.let {
					currentUser = it
					_authResultLiveData.value = true
					Log.i("Current user", currentUser.toString())
				}
			}
		}


}