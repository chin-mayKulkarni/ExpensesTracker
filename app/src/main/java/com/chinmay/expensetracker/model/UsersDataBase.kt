package com.chinmay.expensetracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class UsersDataBase: RoomDatabase() {
    abstract fun usersDao(): UsersDao

    companion object{
        @Volatile private var instance: UsersDataBase? = null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK){
            instance?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UsersDataBase::class.java,
            "usersdatabase"
        ).build()
    }
}