package com.evontech.evontechpayroll.ui.attendance

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AttendanceViewModelFactory(val app: Application,private val searchDate: String):ViewModelProvider.AndroidViewModelFactory(app) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttendanceViewModel::class.java)) {
            return AttendanceViewModel(app,searchDate) as T
        }
        return super.create(modelClass)
    }
}