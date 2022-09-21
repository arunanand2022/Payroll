package com.evontech.evontechpayroll.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import androidx.room.Index
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "day_Login_table", indices = [Index(value = ["day"],unique = true)])
data class DayLoginSummaryModel (
    @PrimaryKey(autoGenerate = true)
    val dayId : Int? = null,
    var day :String,
    val checkIn : String? = null,
    val checkOut : String? = null,
    val hours : String? = "0.00"
  ): Parcelable