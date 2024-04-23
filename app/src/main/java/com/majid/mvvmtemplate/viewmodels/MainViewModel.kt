package com.majid.mvvmtemplate.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.majid.mvvmtemplate.app.App
import com.majid.mvvmtemplate.models.User
import com.majid.mvvmtemplate.repositories.PreferencesRepository
import com.majid.mvvmtemplate.repositories.Repository
import com.majid.mvvmtemplate.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    var preferencesRepository: PreferencesRepository, var repository: Repository,
) : ViewModel() {

    /***
     The below _userData declared as mutable live and private so only view model could update it
    and it could be accessed using the userData that can only be observed not updated by other classes

    **/
    private val _userData: MutableLiveData<ArrayList<User>> = MutableLiveData()
    val userData: LiveData<ArrayList<User>> get() = _userData

    //This Coroutine Exception handle is to handle any exption that may raise in Coroutines
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

        /***
         *  the below function is used to save the status of user in DataStore /
         * **/
    fun saveUserLoggedInStatus(isLoggedIn: Boolean) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            preferencesRepository.saveUserLoggedInStatus(isLoggedIn)
        }

    }
    /***
     *The below function is used to checkwhether a user  s logged in or not
     * **/
    fun getUserLoggedInStatus(): LiveData<Boolean> = liveData {
        val isLoggedIn = preferencesRepository.getUserLoggedInStatus()
        emit(isLoggedIn)
    }

    fun loadData() {
        if (repository.hasData()) {
            _userData.postValue(ArrayList(repository.getUsers()))
        } else {
            // Load data from network or other source if necessary
            // For demonstration purposes, assuming you have a list of dummy users
            val dummyUsers: List<User> = Utils.generateUsers()
            repository.insertUsers(dummyUsers)

//            _userData.postValue(ArrayList(dummyUsers))
            _userData.postValue(ArrayList(repository.getUsers()) )
        }
    }

    /***
     * Closing the realm database as the Viewmodel is Cleared
     * **/

    override fun onCleared() {
        super.onCleared()
        repository.closeRealm()
    }

    /**
    Check Network connectivity
     */
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        ) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_BLUETOOTH
        ))
    }

}