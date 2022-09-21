package com.evontech.evontechpayroll.repository

import androidx.lifecycle.LiveData
import com.evontech.evontechpayroll.database.DayLoginDao
import com.evontech.evontechpayroll.database.UserDetailsDao
import com.evontech.evontechpayroll.model.DayLoginSummaryModel
import com.evontech.evontechpayroll.model.UserDetailsModel

class LoginRepository(private val userDetailsDao: UserDetailsDao) {

    //val readAllData : LiveData<List<DayLoginSummaryModel>> = dayLoginDao.getAllDay()

//    suspend fun updateDailyLoginAttendance(day: String,check_in:String){
//        dayLoginDao.updateDailyAttendance(day,check_in)
//    }

    suspend fun addUserDetails(userDetailsModel: UserDetailsModel){
       return userDetailsDao.addUserDetails(userDetailsModel)
       }
    suspend fun checkUserInsertedDetails(userDetailsModel: UserDetailsModel):UserDetailsModel?{
       return userDetailsDao.checkUserInserted(userDetailsModel.userName,userDetailsModel.userMobileNo)

    }


    suspend fun loginUser(userDetailsModel: UserDetailsModel):UserDetailsModel?{
       return userDetailsDao.loginUser(userDetailsModel.userName,userDetailsModel.userPassword)
    }

//    suspend fun getParticularUser(userDetailsModel: UserDetailsModel):UserDetailsModel?{
//       return userDetailsDao.getParticularUser(userDetailsModel.userName,userDetailsModel.userPassword)
//    }
}