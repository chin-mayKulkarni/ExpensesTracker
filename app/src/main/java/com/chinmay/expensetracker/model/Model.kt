package com.chinmay.expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @ColumnInfo(name = "title")
    val Title : String,

    @ColumnInfo(name = "amount")
    val Amount : Int,

    @ColumnInfo(name = "paid_by")
    val PaidBy : String,

    @ColumnInfo(name = "date")
    val Date : String,

    @ColumnInfo(name = "description")
    val Description : String?
){
    @PrimaryKey(autoGenerate = true)
    var utid: Int = 0
}