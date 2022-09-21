package com.evontech.evontechpayroll.ui.checkout

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.evontech.evontechpayroll.database.MyRoomDatabase
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel
import com.evontech.evontechpayroll.repository.MonthlyAttendanceRepository
import com.evontech.evontechpayroll.utils.util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProceedCheckoutViewModel(app:Application) : AndroidViewModel(app) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is CheckOut Fragment"
    }
    val text: LiveData<String> = _text

    lateinit var getTodayCheckOutData : LiveData<List<MonthlyAttendanceLogModel>>
    val monthlyAttendanceRepository : MonthlyAttendanceRepository
    init {
        val monAttDao = MyRoomDatabase.getDbInstance(getApplication()).monthlyAttenDao()
        monthlyAttendanceRepository = MonthlyAttendanceRepository(monAttDao)
        viewModelScope.launch(Dispatchers.IO) {
            getTodayCheckOutData = monthlyAttendanceRepository.readDataForMonth("",util.getMonthAndYear().toString())
        }
    }

    private val _updateFinalCheckoutFlag = MutableLiveData<Boolean>()
    var updateFinalCheckoutFlag :LiveData<Boolean> =_updateFinalCheckoutFlag

    fun addCheckIn(monthlyAttendanceLogModel: MonthlyAttendanceLogModel){
        viewModelScope.launch(Dispatchers.IO) {
            monthlyAttendanceRepository.addCheckInData(monthlyAttendanceLogModel)
        }
    }
    fun updateCheckoutSave(monthlyAttendanceLogModel: MonthlyAttendanceLogModel){
        viewModelScope.launch(Dispatchers.IO) {
            monthlyAttendanceRepository.updateCheckoutSave(monthlyAttendanceLogModel)
        }
    }
    fun updateCheckOutFinalTime(monthlyAttendanceLogModel: MonthlyAttendanceLogModel){
        viewModelScope.launch(Dispatchers.IO) {
           val updateFlag = monthlyAttendanceRepository.updateCheckOutFinal(monthlyAttendanceLogModel)
            //Log.d("updateFlag=>>",updateFlag.toString())
            if (updateFlag==1){
                _updateFinalCheckoutFlag.postValue(true)
            }else{
                _updateFinalCheckoutFlag.postValue(false)
            }
        }
    }


}