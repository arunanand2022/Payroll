package com.evontech.evontechpayroll.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_attendance_log")
data class MonthlyAttendanceLogModel(
    @PrimaryKey(autoGenerate = true)
    val log_id : Long? = null,
    val user_id : Long? = null,
    val login_day : String ? = null,
    val check_in_time : String? = null,
    val check_out_time : String? = null,
    val month_and_year : String? = null,
    val projectName : String? = null,
    val role : String? = null,
    val check_out_comment : String? = null,
    val login_comment : String? = null,
    val hour : String? = null
)