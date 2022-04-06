package com.chinmay.expensetracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertAll(vararg expense: Expense): List<Long>

    @Query("SELECT * FROM expense")
    suspend fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM expense WHERE utid = :expId")
    suspend fun getExpenseDetails(expId: Int): Expense

    @Query("DELETE FROM expense")
    suspend fun deleteAllExpenses()

    @Query("SELECT * FROM expense " +
            "ORDER BY " +
            "CASE WHEN :isAsc = 0 THEN amount END ASC, " +
            "CASE WHEN :isAsc = 1 THEN amount END DESC ")
    suspend fun expenseOrderByAmount(isAsc: Boolean?): List<Expense>

    @Query("SELECT * FROM expense " +
            "ORDER BY " +
            "CASE WHEN :isAsc = 0 THEN date END ASC, " +
            "CASE WHEN :isAsc = 1 THEN date END DESC ")
    suspend fun expenseOrderByDate(isAsc: Boolean?): List<Expense>
}