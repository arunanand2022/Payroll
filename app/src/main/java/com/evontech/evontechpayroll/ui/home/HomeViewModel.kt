package com.evontech.evontechpayroll.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evontech.evontechpayroll.database.MyRoomDatabase
import com.evontech.evontechpayroll.model.DayLoginSummaryModel
import com.evontech.evontechpayroll.repository.HomeRepository
import com.evontech.evontechpayroll.utils.util
import com.evontech.evontechpayroll.utils.util.Companion.LOGINCHECKIN
import com.evontech.evontechpayroll.utils.util.Companion.calculateHours
import com.evontech.evontechpayroll.utils.util.Companion.getCurrentDate
import com.evontech.evontechpayroll.utils.util.Companion.getCurrentDateTime
import com.evontech.evontechpayroll.utils.util.Companion.getStringPreferences
import com.evontech.evontechpayroll.utils.util.Companion.saveStringPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel constructor(app: Application) : AndroidViewModel(app) {

    private val _text = MutableLiveData<String>().apply {value = "Hours logged in last 7 days" }
    val text: LiveData<String> = _text

    private val _totleHour = MutableLiveData<String>()
    val totleHour : LiveData<String> get() = _totleHour

    private val _checkInUpdateFirstTime  = MutableLiveData<Boolean>()
    val checkInUpdateFirstTime : LiveData<Boolean> get()= _checkInUpdateFirstTime

    val readDayLogin : LiveData<List<DayLoginSummaryModel>>
    private val homeRepository : HomeRepository

    init {
       val dayLoginDao = MyRoomDatabase.getDbInstance(getApplication()).dayLoginDao()
        homeRepository = HomeRepository(dayLoginDao)
        readDayLogin = homeRepository.readAllData
    }


    fun addDayLogin(dayLoginSummaryModel: DayLoginSummaryModel){
        viewModelScope.launch(Dispatchers.IO) { homeRepository.addDay(dayLoginSummaryModel)}
    }

    fun updateDailyLoginAttendance(day:String,checkin:String){
        viewModelScope.launch(Dispatchers.IO) {
            val data = homeRepository.checkLoginUpdated(day)
            if (data!!.checkIn.equals("0")){
                homeRepository.updateDailyLoginAttendance(day,checkin)
                saveStringPreferences(getApplication(),LOGINCHECKIN,checkin)//08/09/22 12:28:57pm
                _checkInUpdateFirstTime.postValue(true)
                val value =  homeRepository.getTotalHour()
                _totleHour.postValue(value.toString())
            }else if (!data.checkIn.equals("0")){
                val checkinTime = getStringPreferences(getApplication(),LOGINCHECKIN,"").toString()
                val hours = calculateHours(checkinTime.substring(0, checkinTime.length - 2), getCurrentDateTime().toString())
                homeRepository.updateDailyHours(day,hours)
                val value =  homeRepository.getTotalHour()
                _totleHour.postValue(value.toString())
            }


        }
    }

    fun checkInUpdateFirstTimeFlag(){
        _checkInUpdateFirstTime.postValue(false)
    }

    fun updateCheckoutTime(day:String, hours:String, checkout:String){
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.updateCheckoutTime(day,hours, checkout)
        }

    }



}