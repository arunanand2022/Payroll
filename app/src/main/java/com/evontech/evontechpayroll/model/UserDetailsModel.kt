package com.evontech.evontechpayroll.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_details_table", indices = [Index(value = ["userMobileNo"],unique = true)])
data class UserDetailsModel(
    @PrimaryKey(autoGenerate = true)
    val userId : Long? = null,
    val userName : String,
    val userPassword : String,
    val userFirstName : String?=null,
    val userMiddleName : String?=null,
    val userLastName : String?=null,
    val userEmailId : String?=null,
    val userMobileNo : String?=null,
    val userCreateDate : String?=null
):Parcelable