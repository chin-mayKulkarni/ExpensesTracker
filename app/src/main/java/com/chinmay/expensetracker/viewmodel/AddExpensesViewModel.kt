package com.chinmay.expensetracker.viewmodel

import android.app.Application
import androidx.core.content.ContentProviderCompat.requireContext
import com.chinmay.expensetracker.model.Expense
import com.chinmay.expensetracker.model.ExpenseDataBase
import kotlinx.coroutines.launch

class AddExpensesViewModel(application: Application) : BaseViewModel(application) {

    public fun storeDataLocally(list: List<Expense>) {
        launch {
            //ExpenseDataBase(getApplication()).expenseDao().deleteAllExpenses()
            val result = ExpenseDataBase(getApplication()).expenseDao().insertAll(*list.toTypedArray())
            var i = 0
            while(i< list.size){
                list[i].utid = result[i].toInt()
                ++i
            }

        }

    }
}