package com.evontech.evontechpayroll.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_announcement_table")
data class EventAndAnnouncementModel(
    @PrimaryKey(autoGenerate = true)
    val eventId: Long?= null,
    val eventHeading : String?=null,
    val requested_date : String?=null,
    val url : String?=null,
    val details : String?=null,
    val image : String?=null,
    val eventValid : String?=null,
    val action : Boolean?=true

)