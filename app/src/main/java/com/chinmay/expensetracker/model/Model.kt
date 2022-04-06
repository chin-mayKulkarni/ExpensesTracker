package com.chinmay.expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val userName: String,
    val password: String
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

@Entity
data class Expense(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "amount")
    val expenseAmount: Long,

    @ColumnInfo(name = "paid_by")
    val paidBy: String,

    @ColumnInfo(name = "date")
    val expenseDate: String,

    @ColumnInfo(name = "description")
    val description: String?
) {
    @PrimaryKey(autoGenerate = true)
    var utid: Int = 0
}

