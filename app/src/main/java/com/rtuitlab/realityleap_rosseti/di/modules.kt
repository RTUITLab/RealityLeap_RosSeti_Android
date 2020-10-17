package com.rtuitlab.realityleap_rosseti.di

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rtuitlab.realityleap_rosseti.recyclers.inspection_tasks.InspectionTasksAdapter
import com.rtuitlab.realityleap_rosseti.server.TasksRepository
import com.rtuitlab.realityleap_rosseti.viewmodels.InspectionTasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.database }
}

val repositoriesModule = module {
    single { TasksRepository(get()) }
}

val viewModelsModule = module {
    viewModel { InspectionTasksViewModel(get()) }
}

val adaptersModule = module {
    single { InspectionTasksAdapter() }
}