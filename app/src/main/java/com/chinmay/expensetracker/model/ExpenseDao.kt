package com.chinmay.expensetracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertAll(vararg songs: Expense): List<Long>

    @Query("SELECT * FROM expense")
    suspend fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM expense WHERE utid = :dogId")
    suspend fun getExpense(dogId: Int): Expense

    @Query("DELETE FROM expense")
    suspend fun deleteAllExpenses()
}