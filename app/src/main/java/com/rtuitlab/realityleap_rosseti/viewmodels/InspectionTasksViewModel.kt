package com.rtuitlab.realityleap_rosseti.viewmodels

import androidx.lifecycle.ViewModel
import com.rtuitlab.realityleap_rosseti.server.TasksRepository

class InspectionTasksViewModel(
    tasksRepo: TasksRepository
): ViewModel() {
    val inspectionTasksLiveData = tasksRepo.inspectionTasksLiveData
}