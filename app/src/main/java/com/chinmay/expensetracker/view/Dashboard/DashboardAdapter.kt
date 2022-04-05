package com.chinmay.expensetracker.view.Dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.model.Expense
import kotlinx.android.synthetic.main.item_expense_layout.view.*

class DashboardAdapter(val expenseList: ArrayList<Expense>) :
    RecyclerView.Adapter<DashboardAdapter.ExpenseViewHolder>() {

    fun updateExpensesList(newExpensesList: List<Expense>) {
        expenseList.clear()
        expenseList.addAll(newExpensesList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_expense_layout, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.view.expenseTitle.text = expenseList[position].Title
        holder.view.paidBy.text = expenseList[position].PaidBy
        holder.view.expenseAmount.text = expenseList[position].Amount.toString()
        holder.view.expenseDate.text = expenseList[position].Date

        holder.view.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionAddExpenseFragment()
            Navigation.findNavController(it)
                .navigate(action)
        }

    }


    override fun getItemCount(): Int {
        return 0
    }

    class ExpenseViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}