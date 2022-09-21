package com.evontech.evontechpayroll.repository

import androidx.lifecycle.LiveData
import com.evontech.evontechpayroll.database.MonthlyAttendanceLogDao
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel

class MonthlyAttendanceRepository(val monthlyAttendanceLogDao: MonthlyAttendanceLogDao) {

    suspend fun readDataForMonth(day: String,date:String):LiveData<List<MonthlyAttendanceLogModel>>{
     return   monthlyAttendanceLogDao.getMonthlyData(date)
    }

    suspend fun addCheckInData(monthlyAttendanceLogModel: MonthlyAttendanceLogModel){
        monthlyAttendanceLogDao.insertCheckout(monthlyAttendanceLogModel)
    }
    suspend fun updateCheckoutSave(monthlyAttendanceLogModel: MonthlyAttendanceLogModel){
        monthlyAttendanceLogDao.updateCheckoutSave(monthlyAttendanceLogModel.check_in_time.toString(),monthlyAttendanceLogModel.login_day.toString(),
            monthlyAttendanceLogModel.month_and_year.toString(),monthlyAttendanceLogModel.projectName.toString(),monthlyAttendanceLogModel.role.toString(),monthlyAttendanceLogModel.check_out_comment.toString(),monthlyAttendanceLogModel.hour.toString())
    }

    suspend fun updateCheckOutFinal(monthlyAttendanceLogModel: MonthlyAttendanceLogModel):Int?{
      return  monthlyAttendanceLogDao.updateCheckOutFinal(monthlyAttendanceLogModel.check_out_time.toString(),monthlyAttendanceLogModel.login_day.toString(), monthlyAttendanceLogModel.month_and_year.toString())
    }

    suspend fun getMonthlyAttendance(date:String):LiveData<List<MonthlyAttendanceLogModel>>{
       return monthlyAttendanceLogDao.getMonthlyAttendance(date)
    }

}