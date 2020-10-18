package com.rtuitlab.realityleap_rosseti.viewmodels

import androidx.lifecycle.ViewModel
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask

class TaskViewModel(
    val task: InspectionTask
) : ViewModel()