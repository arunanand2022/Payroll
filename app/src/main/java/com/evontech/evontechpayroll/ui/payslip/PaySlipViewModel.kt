package com.evontech.evontechpayroll.ui.payslip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PaySlipViewModel(application: Application):AndroidViewModel(application){

    private val _text = MutableLiveData<String>().apply {
        value = "PaySlip Section Coming Soon......"
    }
    val text: LiveData<String> = _text
}