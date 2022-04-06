package com.chinmay.expensetracker.view.Dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.model.Expense
import com.chinmay.expensetracker.util.SharedPreferencesHelper
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
        val prefHelper = SharedPreferencesHelper(holder.view.context)
        val loggedInUser = prefHelper.getLoggedInUser()
        holder.view.expenseTitle.text = expenseList[position].title
        if (loggedInUser.equals(expenseList[position].paidBy)) {
            holder.view.paidBy.text = "You"
        } else {
            holder.view.paidBy.text = expenseList[position].paidBy
        }
        holder.view.expenseAmount.text = expenseList[position].expenseAmount.toString()
        holder.view.expenseDate.text = expenseList[position].expenseDate


        holder.view.setOnClickListener {
            Log.d("DashboardAdapter", expenseList[position].utid.toString())
            val action =
                DashboardFragmentDirections.actionDetailsFragment(expenseList[position].utid)
            Navigation.findNavController(it)
                .navigate(action)
        }

    }


    override fun getItemCount(): Int {
        return expenseList.size
    }

    class ExpenseViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}