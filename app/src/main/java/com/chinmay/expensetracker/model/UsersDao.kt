package com.chinmay.expensetracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDao {

    @Insert
    suspend fun insertAllUsers(vararg user: User): List<Long>

    @Query("SELECT * FROM user")
    suspend fun getAllUser(): List<User>


}