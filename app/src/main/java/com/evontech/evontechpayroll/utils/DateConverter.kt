package com.evontech.evontechpayroll.utils

import android.provider.SyncStateContract
import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    /*var df: DateFormat = SimpleDateFormat(SyncStateContract.Constants.DOB_FORMAT)

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return if (value != null) {
            try {
                return df.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }

    @TypeConverter
    fun dateToTimestamp(value: Date?): String? {
        return if (value == null) null else df.format(value)
    }*/
}