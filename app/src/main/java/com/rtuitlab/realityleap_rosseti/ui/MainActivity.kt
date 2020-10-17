package com.rtuitlab.realityleap_rosseti.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rtuitlab.realityleap_rosseti.R

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		paintStatusBar()
	}

	private fun paintStatusBar() {
		if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_NO){
			window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
		}
		window.statusBarColor = android.R.attr.windowBackground
	}

	fun navigateButtonVisible(isVisible: Boolean) = supportActionBar?.run {
		setDisplayHomeAsUpEnabled(isVisible)
		setDisplayShowHomeEnabled(isVisible)
	}

	override fun onSupportNavigateUp() = run {
		onBackPressed()
		true
	}
}