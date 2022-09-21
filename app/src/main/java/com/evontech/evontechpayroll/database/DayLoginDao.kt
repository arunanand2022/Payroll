package com.evontech.evontechpayroll.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.evontech.evontechpayroll.model.DayLoginSummaryModel

@Dao
interface DayLoginDao {

    @Insert
    fun addDay(dayLoginSummaryModel: DayLoginSummaryModel)

    @Query("Select * from day_Login_table")
    fun getAllDay(): LiveData<List<DayLoginSummaryModel>>

    @Query("Update day_Login_table Set checkIn =:check_in, hours='0.00'  Where day =:Day and checkIn ='0' and checkOut='0' and day not in('Sat','Sun')")
    fun updateDailyAttendance(Day: String?,check_in:String?)

    @Query("Update day_Login_table Set hours =:hours Where day =:Day and checkIn !='0' and checkOut='0' and day not in('Sat','Sun')")
    fun updateDailyHours(Day: String?,hours:String?)

    @Query("Select * from day_Login_table where day=:Day")
    fun checkLoginUpdated(Day: String?):DayLoginSummaryModel?

    @Query("Select sum(cast(hours as Double))   as total From day_Login_table")
    fun getTotalHourOfTheWeak():Double?

    @Query("Update day_Login_table Set hours =:hours, checkOut=:checkout Where day =:Day and checkIn !='0' and checkOut='0' and day not in('Sat','Sun')")
    fun updateCheckoutTime(Day: String?,hours:String?,checkout :String?)
}
