package com.evontech.evontechpayroll.repository

import androidx.lifecycle.LiveData
import com.evontech.evontechpayroll.database.DayLoginDao
import com.evontech.evontechpayroll.model.DayLoginSummaryModel

class HomeRepository(private val dayLoginDao: DayLoginDao) {

    val readAllData : LiveData<List<DayLoginSummaryModel>> = dayLoginDao.getAllDay()

    suspend fun addDay(dayLoginSummaryModel: DayLoginSummaryModel){
        dayLoginDao.addDay(dayLoginSummaryModel)
    }
    suspend fun updateDailyLoginAttendance(day: String,check_in:String){
        dayLoginDao.updateDailyAttendance(day,check_in)
    }

    suspend fun checkLoginUpdated(day: String):DayLoginSummaryModel?{
       return dayLoginDao.checkLoginUpdated(day)
    }

    suspend fun updateDailyHours(day: String,hour:String){
         dayLoginDao.updateDailyHours(day,hour)
    }

    suspend fun getTotalHour():Double?{
        return dayLoginDao.getTotalHourOfTheWeak()
    }
    suspend fun updateCheckoutTime(day: String,hour:String,checkout:String){
        dayLoginDao.updateCheckoutTime(day,hour,checkout)
    }

}