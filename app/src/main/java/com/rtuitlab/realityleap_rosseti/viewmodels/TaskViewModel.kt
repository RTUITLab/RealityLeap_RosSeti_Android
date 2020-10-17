package com.rtuitlab.realityleap_rosseti.viewmodels

import android.location.Location
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask

class TaskViewModel(
    val task: InspectionTask
) : ViewModel() {

    private companion object {
        const val DISTANCE_FOR_ARRIVE = 10000
    }

    val isArrived = ObservableBoolean(true) // TODO place false

    var currentLocation: Location? = null
        set(value) {
            field = value
            value?.let { currentLocation ->
                val targetLocation = Location("").apply {
                    val latLng = task.place.location
                    latitude = latLng.lat
                    longitude = latLng.lng
                }
                Log.i("Current distance", currentLocation.distanceTo(targetLocation).toString())
                isArrived.set(currentLocation.distanceTo(targetLocation) < DISTANCE_FOR_ARRIVE)
            }
        }
}