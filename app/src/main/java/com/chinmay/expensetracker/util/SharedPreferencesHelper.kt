package com.chinmay.expensetracker.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {
    companion object {
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferencesHelper? = null
        private var LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper =
            instance ?: synchronized(LOCK) {
                instance ?: buildHelper(context).also {
                    instance = it
                }
            }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun storeLoggedInUser(user: String?) {
        prefs?.edit(commit = true) { putString("user_name", user) }
    }

    fun getLoggedInUser() = prefs?.getString("user_name", null)

}