package com.chinmay.expensetracker.view.Dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.util.SharedPreferencesHelper
import com.chinmay.expensetracker.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var viewModel : DashboardViewModel
    private val dashboardAdapter = DashboardAdapter(arrayListOf())
    private lateinit var prefHelper: SharedPreferencesHelper



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        prefHelper = SharedPreferencesHelper(requireContext())

        transaction_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dashboardAdapter
        }

        viewModel.fetchFromDatabase()

        btn_add_transaction.setOnClickListener {
            val action = DashboardFragmentDirections.actionAddExpenseFragment()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.expense.observe(viewLifecycleOwner, Observer { expenses ->
            expenses?.let {
                /*songsListLocal = songs
                search_bar.isEnabled = true*/
                transaction_list.visibility = View.VISIBLE
                dashboardAdapter.updateExpensesList(expenses)
            }
        })

    }

}