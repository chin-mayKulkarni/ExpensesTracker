package com.chinmay.expensetracker.view.AddExpense

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chinmay.expensetracker.R
import kotlinx.android.synthetic.main.fragment_add_expense.*

class AddExpenseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = "Add Expense"
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            // back button pressed
            activity?.onBackPressed()
        })
    }

}