package com.evontech.evontechpayroll.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evontech.evontechpayroll.database.MyRoomDatabase
import com.evontech.evontechpayroll.model.DayLoginSummaryModel
import com.evontech.evontechpayroll.model.UserDetailsModel
import com.evontech.evontechpayroll.repository.LoginRepository
import com.evontech.evontechpayroll.utils.util
import com.evontech.evontechpayroll.utils.util.Companion.LOGINCHECKIN
import com.evontech.evontechpayroll.utils.util.Companion.LOGINDAY
import com.evontech.evontechpayroll.utils.util.Companion.USERFIRSTNAME
import com.evontech.evontechpayroll.utils.util.Companion.USERID
import com.evontech.evontechpayroll.utils.util.Companion.USERLASTNAME
import com.evontech.evontechpayroll.utils.util.Companion.USERNAME
import com.evontech.evontechpayroll.utils.util.Companion.USERPHONE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(app:Application): AndroidViewModel(app) {
    //val checkUserInsertedOrNot : LiveData<List<UserDetailsModel>>
    private val loginRepository : LoginRepository
    init {
        val userLogin = MyRoomDatabase.getDbInstance(getApplication()).userDetailsDao()
        loginRepository = LoginRepository(userLogin)
        //checkUserInsertedOrNot = loginRepository.checkUserInsertedDetails
    }

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> get() = _navigateToHome

    private val _errorInvalidPassword = MutableLiveData<Boolean>()
    val errorInvalidPassword: LiveData<Boolean> get() = _errorInvalidPassword

    private val _errorUsername = MutableLiveData<Boolean>()
    val errorUsername: LiveData<Boolean> get() = _errorUsername

    private val _checkUserAdded = MutableLiveData<Boolean>()
    val checkUserAdded: LiveData<Boolean> get() = _checkUserAdded


    fun addUserDetails(userDetailsModel: UserDetailsModel){
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.addUserDetails(userDetailsModel)
        }
    }
    fun checkUserInsertedOrNot(userDetailsModel: UserDetailsModel){
        viewModelScope.launch(Dispatchers.IO) {
            val data = loginRepository.checkUserInsertedDetails(userDetailsModel)
            if(data == null){
                _checkUserAdded.postValue(true)
            }else{
                _checkUserAdded.postValue(false)
            }
        }
    }


    fun login(userDetailsModel: UserDetailsModel){
        viewModelScope.launch(Dispatchers.IO) {
            val userData =    loginRepository.loginUser(userDetailsModel)
            if (userData != null) {
                if(userData.userPassword == userDetailsModel.userPassword){
                    util.saveStringPreferences(getApplication(),USERID,userData.userId.toString())
                    util.saveStringPreferences(getApplication(),USERNAME,userData.userName)
                    util.saveStringPreferences(getApplication(), USERFIRSTNAME,userData.userFirstName)
                    util.saveStringPreferences(getApplication(), USERLASTNAME,userData.userLastName)
                    util.saveStringPreferences(getApplication(), USERPHONE,userData.userMobileNo)
                    _navigateToHome.postValue(true)
                }else{
                    _errorInvalidPassword.postValue(true)
                }
            } else {
                _errorUsername.postValue(true)
            }
        }
    }

    fun doneNavigatingUserDetails() {
        _navigateToHome.postValue(false)
    }

    fun donetoastInvalidPassword() {
        _errorInvalidPassword .value = false
    }

    fun donetoastErrorUsername() {
        _errorUsername .value = false
    }



}