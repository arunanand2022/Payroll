package com.evontech.evontechpayroll.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.evontech.evontechpayroll.model.DayLoginSummaryModel
import com.evontech.evontechpayroll.model.EventAndAnnouncementModel
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel
import com.evontech.evontechpayroll.model.UserDetailsModel

@Database(entities = [DayLoginSummaryModel::class,UserDetailsModel::class,MonthlyAttendanceLogModel::class, EventAndAnnouncementModel::class], version = 2, exportSchema = false)
abstract class MyRoomDatabase : RoomDatabase(){

    abstract  fun dayLoginDao():DayLoginDao
    abstract  fun userDetailsDao():UserDetailsDao
    abstract  fun monthlyAttenDao():MonthlyAttendanceLogDao
    abstract fun eventDao(): EventAndAnnouncementDao

    companion object{
        private var INSTANCE : MyRoomDatabase? =null

        fun getDbInstance(context: Context):MyRoomDatabase{
            val  tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,MyRoomDatabase::class.java,"evontech_payroll_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}