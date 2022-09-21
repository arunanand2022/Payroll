package com.evontech.evontechpayroll.ui.leave

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LeaveViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Leave Section Coming Soon......"
    }
    val text: LiveData<String> = _text
}