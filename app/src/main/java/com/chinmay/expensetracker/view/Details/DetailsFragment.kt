package com.chinmay.expensetracker.view.Details

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.util.SharedPreferencesHelper
import com.chinmay.expensetracker.view.MainActivity
import com.chinmay.expensetracker.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    val args : DetailsFragmentArgs by navArgs()
    lateinit var viewModel : DashboardViewModel
    lateinit var prefHelper : SharedPreferencesHelper
    var allUsers = "user1 user2 user3 user4"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefHelper = SharedPreferencesHelper(requireContext())

        setHasOptionsMenu(true)
        (activity as MainActivity?)?.setActionBarTitle("Details")

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        viewModel.fetchUserNameList()
        val utid = args.utid
        viewModel.fetchDetails(utid)
        observeViewModel()

    }


    private fun observeViewModel() {
        viewModel.expenseDetails.observe(viewLifecycleOwner, androidx.lifecycle.Observer { expense ->
            expense?.let {
                title.text = it.title
                amount.text = "₹" + it.expenseAmount.toString()
                expense_date.text = it.expenseDate
                expense_desc.text = it.description
                if (prefHelper.getLoggedInUser().equals(it.paidBy)){
                    expense_paid_by.text = "You"
                } else expense_paid_by.text = it.paidBy
                expense_split_with.text = getUsers(it.paidBy)
            }
        })

        viewModel.usersList.observe(viewLifecycleOwner, androidx.lifecycle.Observer { users->
            users?.let {
                Log.d("AddExpensesFragment", it.toString())
                allUsers = it.toString().replace("[", "")
                allUsers = allUsers.replace("]", "")
                allUsers = allUsers.replace(",", "")
            }

        })
    }

    private fun getUsers(paidBy: String): CharSequence? {
        return  allUsers.replace(paidBy, "")
    }

}