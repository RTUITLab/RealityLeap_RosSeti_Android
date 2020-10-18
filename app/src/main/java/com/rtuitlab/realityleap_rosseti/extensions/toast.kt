package com.rtuitlab.realityleap_rosseti.extensions

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String?, duration: Int): Toast = Toast.makeText(this, message, duration)
fun Context.shortToast(message: String?) = toast(message, Toast.LENGTH_SHORT)
fun Context.longToast(message: String?) = toast(message, Toast.LENGTH_SHORT)