package com.chinmay.expensetracker.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.model.User
import com.chinmay.expensetracker.view.Dashboard.DashboardFragmentDirections
import com.chinmay.expensetracker.view.MainActivity
import com.chinmay.expensetracker.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel : DashboardViewModel
    lateinit var usersList: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDialog("Notice", "Do you want to exit application?")
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersList = listOf(
            User("user1", "pass1"),
            User("user2", "pass2"),
            User("user3", "pass3"),
            User("user4", "pass4")
        )

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val action = LoginFragmentDirections.actionDashboardFragment()
        viewModel.initializeUsers(usersList)
        viewModel.fetchUser()

       if ( viewModel.isLoggedIn()){
           Navigation.findNavController(view).navigate(action)
       }
        (activity as MainActivity?)?.setActionBarTitle("Login")

        btn_login.setOnClickListener {
            val valid = viewModel.validateUser(username.text.toString(), password.text.toString())
            if (valid) {
                Navigation.findNavController(it).navigate(action)
            } else{
                username.text?.clear()
                password.text?.clear()
            }
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.validUser.observe(viewLifecycleOwner, Observer { isValid ->
            isValid?.let { valid ->
                errorText.visibility = if (valid) View.GONE else View.VISIBLE

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

    private fun showDialog(title: String, message: String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("Yes"){dialogInterface, which ->
            activity?.finish()
        }
        builder.setNegativeButton("No"){dialogInterface, which ->
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }



}