package com.rtuitlab.realityleap_rosseti

import android.app.Application
import com.rtuitlab.realityleap_rosseti.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

	override fun onCreate() {
		super.onCreate()
		initKoin()
	}

	private fun initKoin() {
		startKoin {
			androidLogger()
			androidContext(this@App)
			modules(
				firebaseModule,
				repositoriesModule,
				viewModelsModule,
				adaptersModule,
				persistenceModule,
			)
		}
	}
}