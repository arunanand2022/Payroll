package com.evontech.evontechpayroll.ui.attendance

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.evontech.evontechpayroll.database.MyRoomDatabase
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel
import com.evontech.evontechpayroll.repository.MonthlyAttendanceRepository
import com.evontech.evontechpayroll.utils.util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttendanceViewModel(application: Application,val searchDate:String):AndroidViewModel(application) {
    lateinit var getTodayCheckOutData : LiveData<List<MonthlyAttendanceLogModel>>
    val monthlyAttendanceRepository : MonthlyAttendanceRepository
    init {
        val monAttDao = MyRoomDatabase.getDbInstance(getApplication()).monthlyAttenDao()
        monthlyAttendanceRepository = MonthlyAttendanceRepository(monAttDao)
        viewModelScope.launch(Dispatchers.IO) {
            getTodayCheckOutData = monthlyAttendanceRepository.getMonthlyAttendance(searchDate)
        }
    }

     fun getMonthData(searchDate:String){
        viewModelScope.launch(Dispatchers.IO) {
            getTodayCheckOutData =   monthlyAttendanceRepository.getMonthlyAttendance(searchDate)
        }
    }
}