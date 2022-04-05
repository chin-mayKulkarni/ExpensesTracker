package com.chinmay.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chinmay.expensetracker.util.SharedPreferencesHelper

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var credMap: Map<String, String> = hashMapOf(
        "user1" to "pass1",
        "user2" to "pass2",
        "user3" to "pass3",
        "user4" to "pass4",
        "q" to "q"
    )
    private var prefHelper = SharedPreferencesHelper(getApplication())


    val validUser = MutableLiveData<Boolean>()


    fun validateUser(username: String, password: String): Boolean {
        validUser.value = credMap.containsKey(username) && password.equals(credMap.get(username))
        if (validUser.value == true) {
            prefHelper.storeLoggedInUser(username)
            return true
        }
        return false
    }
}