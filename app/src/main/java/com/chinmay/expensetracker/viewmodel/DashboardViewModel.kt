package com.chinmay.expensetracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chinmay.expensetracker.model.Expense
import com.chinmay.expensetracker.model.ExpenseDataBase
import com.chinmay.expensetracker.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : BaseViewModel(application) {
    val expense = MutableLiveData<List<Expense>>()
    val expenseDetails = MutableLiveData<Expense>()
    val loading = MutableLiveData<Boolean>()
    private var credMap: Map<String, String> = hashMapOf(
        "user1" to "pass1",
        "user2" to "pass2",
        "user3" to "pass3",
        "user4" to "pass4",
        "q" to "q"
    )
    private var prefHelper = SharedPreferencesHelper(getApplication())
    val validUser = MutableLiveData<Boolean>()

    fun fetchFromDatabase() {
        loading.value = true
        launch {
            val expenses = ExpenseDataBase(getApplication()).expenseDao().getAllExpenses()
            expense.value = expenses
        }
    }

    fun fetchDetails(utid: Int){
        launch {
            val expense = ExpenseDataBase(getApplication()).expenseDao().getExpenseDetails(utid)
            Log.d("DashBoardViewModel", expense.toString())
            expenseDetails.value = expense
        }
    }

    fun storeDataLocally(list: List<Expense>) {
        launch {
            //ExpenseDataBase(getApplication()).expenseDao().deleteAllExpenses()
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
        validUser.value = credMap.containsKey(username) && password.equals(credMap.get(username))
        if (validUser.value == true) {
            prefHelper.storeLoggedInUser(username)
            return true
        }
        return false
    }

}