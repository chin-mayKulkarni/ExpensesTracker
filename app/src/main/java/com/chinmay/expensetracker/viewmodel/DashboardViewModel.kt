package com.chinmay.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chinmay.expensetracker.model.Expense
import com.chinmay.expensetracker.model.ExpenseDataBase
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    val expense = MutableLiveData<List<Expense>>()
    val loading = MutableLiveData<Boolean>()

    private fun fetchFromDatabase() {
        loading.value = true
        /*launch {
            val expenses = ExpenseDataBase(getApplication()).expenseDao().getAllExpenses()
            expensesRetrieved(expenses)
            //Toast.makeText(getApplication(), "Retrieved from DB", Toast.LENGTH_LONG).show()
        }*/
    }

    private fun expensesRetrieved(expenseList: List<Expense>){
        expense.value = expenseList
        /*songsLoadError.value = false
        loading.value = false*/
    }

}