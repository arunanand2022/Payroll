package com.evontech.evontechpayroll.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.evontech.evontechpayroll.database.MyRoomDatabase
import com.evontech.evontechpayroll.model.EventAndAnnouncementModel
import com.evontech.evontechpayroll.repository.EventAndAnnounceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EventAndAnnounceViewModel(application: Application):AndroidViewModel(application) {

    val eventAndAnnounceRepository:EventAndAnnounceRepository
    val getAllData : LiveData<List<EventAndAnnouncementModel>>
    init {
        val eventDao = MyRoomDatabase.getDbInstance(getApplication()).eventDao()
        eventAndAnnounceRepository = EventAndAnnounceRepository(eventDao)
        getAllData = eventAndAnnounceRepository.readAllEvent
    }

    fun addEventDetails(eventAndAnnouncementModel: EventAndAnnouncementModel){
        viewModelScope.launch(Dispatchers.IO) {
            eventAndAnnounceRepository.addEventDetails(eventAndAnnouncementModel)
        }
    }

    fun deleteEvent(){
        viewModelScope.launch(Dispatchers.IO) {
            eventAndAnnounceRepository.delete()
        }
    }

    fun removeItem(eventAndAnnouncementModel: EventAndAnnouncementModel){
        viewModelScope.launch(Dispatchers.IO) {
            eventAndAnnounceRepository.deleteItem(eventAndAnnouncementModel)
        }
    }
}