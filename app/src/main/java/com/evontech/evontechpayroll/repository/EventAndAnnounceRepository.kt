package com.evontech.evontechpayroll.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.evontech.evontechpayroll.database.EventAndAnnouncementDao
import com.evontech.evontechpayroll.model.EventAndAnnouncementModel


class EventAndAnnounceRepository(private val eventDao:EventAndAnnouncementDao) {
    lateinit var _readAllEvent : MutableLiveData<List<EventAndAnnouncementModel>>

    val readAllEvent : LiveData<List<EventAndAnnouncementModel>> = eventDao.getAllEvent()

    suspend fun addEventDetails(eventModel: EventAndAnnouncementModel){
        return eventDao.addEvent(eventModel)
    }

    suspend fun delete(){
        eventDao.delete()
    }
    suspend fun deleteItem(eventModel: EventAndAnnouncementModel){
        /*if (readAllEvent.value != null){
            val item: ArrayList<EventAndAnnouncementModel> = ArrayList(readAllEvent.value)
            item.remove(eventModel)
            Log.d("_readAllEvent=>",_readAllEvent.toString())
            _readAllEvent.value=item
        }*/
    }


}