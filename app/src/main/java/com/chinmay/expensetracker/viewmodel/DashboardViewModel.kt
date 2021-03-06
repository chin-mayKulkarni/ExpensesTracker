package com.chinmay.expensetracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chinmay.expensetracker.model.Expense
import com.chinmay.expensetracker.model.ExpenseDataBase
import com.chinmay.expensetracker.model.User
import com.chinmay.expensetracker.model.UsersDataBase
import com.chinmay.expensetracker.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : BaseViewModel(application) {
    val expense = MutableLiveData<List<Expense>>()
    val expenseDetails = MutableLiveData<Expense>()
    val loading = MutableLiveData<Boolean>()
    var usersList = MutableLiveData<List<String>>()
    private lateinit var userMap: Map<String, String>

    private var prefHelper = SharedPreferencesHelper(getApplication())
    val validUser = MutableLiveData<Boolean>()


    fun fetchFromDatabase() {
        loading.value = true
        launch {
            val expenses = ExpenseDataBase(getApplication()).expenseDao().getAllExpenses()
            expense.value = expenses
        }
    }

    fun sortExpenseByAmount(sortOrderInc: Boolean) {
        loading.value = true
        launch {
            val expenses =
                ExpenseDataBase(getApplication()).expenseDao().expenseOrderByAmount(sortOrderInc)
            expense.value = expenses
        }
    }

    fun sortExpenseByDate(sortOrderInc: Boolean) {
        loading.value = true
        launch {
            val expenses =
                ExpenseDataBase(getApplication()).expenseDao().expenseOrderByDate(sortOrderInc)
            expense.value = expenses
        }
    }

    fun fetchDetails(utid: Int) {
        launch {
            val expense = ExpenseDataBase(getApplication()).expenseDao().getExpenseDetails(utid)
            Log.d("DashBoardViewModel", expense.toString())
            expenseDetails.value = expense
        }
    }

    fun fetchUser() {
        launch {
            val userDetails = UsersDataBase(getApplication()).usersDao().getAllUser()
            userMap = convertListToMap(userDetails)
            createUsersList(userMap)
            if (!userDetails.equals(null))
                Log.d("DashBoardViewModel", userDetails.toString())
        }
    }

    private fun createUsersList(userMap: Map<String, String>) {
        usersList.value = userMap.keys.toList()

    }

    private fun convertListToMap(userDetails: List<User>): Map<String, String> {
        return userDetails.map { it.userName to it.password }.toMap()
    }

    fun initializeUsers(usersList: List<User>) {
        launch {
            val result =
                UsersDataBase(getApplication()).usersDao().insertAllUsers(*usersList.toTypedArray())
            var i = 0
            while (i < usersList.size) {
                usersList[i].uuid = result[i].toInt()
                ++i
            }
        }

    }

    fun storeDataLocally(list: List<Expense>) {
        launch {
            val result =
                ExpenseDataBase(getApplication()).expenseDao().insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].utid = result[i].toInt()
                ++i
            }
        }
    }

    fun validateUser(username: String, password: String): Boolean {
        fetchUser()

        validUser.value = userMap.containsKey(username) && password.equals(userMap.get(username))
        if (validUser.value == true) {
            prefHelper.storeLoggedInUser(username)
            return true
        }
        return false
    }

    fun logout() {
        prefHelper.storeLoggedInUser(null)
    }

    fun isLoggedIn(): Boolean {
        return prefHelper.getLoggedInUser() != null
    }

    fun fetchUserNameList() {
        launch {
            val userDetails = UsersDataBase(getApplication()).usersDao().getAllUser()
            userMap = convertListToMap(userDetails)
            createUsersList(userMap)
            if (!userDetails.equals(null))
                Log.d("DashBoardViewModel", userDetails.toString())
        }
    }

}