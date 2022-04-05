package com.chinmay.expensetracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Expense::class), version = 1)
abstract class ExpenseDataBase: RoomDatabase() {
    abstract fun expenseDao() : ExpenseDao

    companion object{
        @Volatile private var instance: ExpenseDataBase? = null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK){
            instance?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ExpenseDataBase::class.java,
            "expensedatabase"
        ).build()
    }
}