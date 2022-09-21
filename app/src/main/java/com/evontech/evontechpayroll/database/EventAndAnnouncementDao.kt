package com.evontech.evontechpayroll.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.evontech.evontechpayroll.model.EventAndAnnouncementModel

@Dao
interface EventAndAnnouncementDao {
    @Insert
    fun addEvent(eventAndAnnouncementModel: EventAndAnnouncementModel)

    @Query("Select * from event_announcement_table Where `action` = 1")
    fun getAllEvent():LiveData<List<EventAndAnnouncementModel>>

    @Query("Delete From event_announcement_table")
    fun delete()
}