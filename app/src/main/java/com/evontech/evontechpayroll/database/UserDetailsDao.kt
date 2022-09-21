package com.evontech.evontechpayroll.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.evontech.evontechpayroll.model.DayLoginSummaryModel
import com.evontech.evontechpayroll.model.UserDetailsModel

@Dao
interface UserDetailsDao {
    @Insert
    fun addUserDetails(userDetailsModel: UserDetailsModel)

    @Query("Select * From user_details_table where userName =:uName or userMobileNo =:mobile")
    fun checkUserInserted(uName:String,mobile:String?):UserDetailsModel?

    @Query("Select * from user_details_table where userName =:username or userPassword =:password")
    fun loginUser(username:String,password:String):UserDetailsModel?

//    @Query("Select * From user_details_table where userName =:username or userPassword =:password ")
//    fun getParticularUser(username:String,password:String):UserDetailsModel?
}