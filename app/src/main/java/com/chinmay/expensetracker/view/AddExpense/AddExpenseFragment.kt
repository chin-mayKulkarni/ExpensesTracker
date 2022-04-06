package com.chinmay.expensetracker.view.AddExpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.model.Expense
import com.chinmay.expensetracker.util.Constants
import com.chinmay.expensetracker.util.transformIntoDatePicker
import com.chinmay.expensetracker.view.MainActivity
import com.chinmay.expensetracker.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_add_expense.*
import java.util.*

class AddExpenseFragment : Fragment() {

    lateinit var viewModel : DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        (activity as MainActivity?)?.setActionBarTitle("Add Expense")

        val usersAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_autocomplete_layout,
            Constants.usersList
        )

        et_date.transformIntoDatePicker(
            requireContext(),
            "dd/MM/yyyy",
            Date()
        )

        et_paid_by.setAdapter(usersAdapter)


        btn_save_expense.setOnClickListener {
            val title = et_title.text.toString()
            val amount = et_amount.text.toString().toLong()
            val date = et_date.text.toString()
            val description = et_desc.text.toString()
            val paid_by = et_paid_by.text.toString()

            //listOf(Expense(title, amount, date, description, paid_by))

            viewModel.storeDataLocally(listOf(Expense(title, amount, paid_by, date, description)))

            activity?.onBackPressed()


        }
    }


}


