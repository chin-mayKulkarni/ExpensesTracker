package com.chinmay.expensetracker.view.AddExpense

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.model.Expense
import com.chinmay.expensetracker.util.transformIntoDatePicker
import com.chinmay.expensetracker.view.MainActivity
import com.chinmay.expensetracker.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_add_expense.*
import java.util.*

class AddExpenseFragment : Fragment() {

    lateinit var viewModel: DashboardViewModel

    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun afterTextChanged(editable: Editable) {
            // check Fields For Empty Values everytime some value is entered
            checkFieldsForEmptyValues()
        }
    }

    private fun checkFieldsForEmptyValues() {
        btn_save_expense.isEnabled = !(et_title.text.toString() == "" ||
                et_amount.text.toString() == "" ||
                et_date.text.toString() == "" ||
                et_paid_by.text.toString() == "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        (activity as MainActivity?)?.setActionBarTitle("Add Expense")

        viewModel.fetchUserNameList()

        et_title.addTextChangedListener(mTextWatcher)
        et_amount.addTextChangedListener(mTextWatcher)
        et_date.addTextChangedListener(mTextWatcher)
        et_desc.addTextChangedListener(mTextWatcher)
        et_paid_by.addTextChangedListener(mTextWatcher)


        et_date.transformIntoDatePicker(
            requireContext(),
            "dd/MM/yyyy",
            Date()
        )


        btn_save_expense.setOnClickListener {
            viewModel.storeDataLocally(getEnteredExpense())
            activity?.onBackPressed()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.usersList.observe(viewLifecycleOwner, androidx.lifecycle.Observer { users ->
            users?.let {
                val usersAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_autocomplete_layout,
                    it
                )
                Log.d("AddExpensesFragment", it.toString())
                et_paid_by.setAdapter(usersAdapter)

            }

        })
    }

    private fun getEnteredExpense(): List<Expense> {
        val title = et_title.text.toString()
        val amount = et_amount.text.toString().toLong()
        val date = et_date.text.toString()
        val description = et_desc.text.toString()
        val paid_by = et_paid_by.text.toString()

        return listOf(Expense(title, amount, paid_by, date, description))
    }


}


