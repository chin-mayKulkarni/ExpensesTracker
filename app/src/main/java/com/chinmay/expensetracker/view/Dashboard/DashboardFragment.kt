package com.chinmay.expensetracker.view.Dashboard

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.util.SharedPreferencesHelper
import com.chinmay.expensetracker.view.MainActivity
import com.chinmay.expensetracker.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment() {

    private lateinit var viewModel : DashboardViewModel
    private val dashboardAdapter = DashboardAdapter(arrayListOf())
    private lateinit var prefHelper: SharedPreferencesHelper
    var sortOrderInc = true
    var sortOrderDate = true



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

        setHasOptionsMenu(true)
        //MainActivity.setActionBarTitle("Dashboard")
        (activity as MainActivity?)?.setActionBarTitle("Dashboard")

        viewModel.fetchFromDatabase()

        btn_add_transaction.setOnClickListener {
            val action = DashboardFragmentDirections.actionAddExpenseFragment()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout ->{
                showDialog("Notice", "Do you want to logout?")
            }
            R.id.action_sort_amount ->{
                sortOrderInc = !sortOrderInc
                viewModel.sortExpenseByAmount(sortOrderInc)
            }
            R.id.action_sort_date ->{
                sortOrderDate = !sortOrderDate
                viewModel.sortExpenseByDate(sortOrderDate)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDialog(title: String, message: String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(R.drawable.ic_log_out)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            viewModel.logout()
            val action = DashboardFragmentDirections.actionLogoutUser()
            view?.let { Navigation.findNavController(it).navigate(action) }
        }

        builder.setNegativeButton("No"){dialogInterface, which ->

        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

}