package com.evontech.evontechpayroll.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel

@Dao
interface MonthlyAttendanceLogDao {

    @Insert
    fun insertCheckout(monthlyAttendanceLogModel: MonthlyAttendanceLogModel)

    @Query("Select * from monthly_attendance_log Where month_and_year =:month_year")
    fun getMonthlyData(month_year:String): LiveData<List<MonthlyAttendanceLogModel>>

    @Query("Update monthly_attendance_log Set projectName =:project,role=:role,check_out_comment=:comment,hour=:hour Where check_in_time=:checkin and login_day=:day and month_and_year=:date")
    fun updateCheckoutSave(checkin:String,day:String,date:String, project: String, role:String, comment:String,hour: String)

    @Query("Update monthly_attendance_log Set check_out_time =:checkout Where login_day=:day and month_and_year=:date")
    fun updateCheckOutFinal(checkout:String,day:String,date:String):Int?

    //@Query("Select * from monthly_attendance_log  ")
    //fun getMonthlyAttendance(): LiveData<List<MonthlyAttendanceLogModel>>

    @Query("Select * from monthly_attendance_log Where substr(month_and_year,4,8) =:month_year ")
    fun getMonthlyAttendance(month_year:String): LiveData<List<MonthlyAttendanceLogModel>>
}