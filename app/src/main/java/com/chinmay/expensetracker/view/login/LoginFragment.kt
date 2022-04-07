package com.chinmay.expensetracker.view.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.databinding.FragmentLoginBinding
import com.chinmay.expensetracker.model.User
import com.chinmay.expensetracker.view.MainActivity
import com.chinmay.expensetracker.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var viewModel: DashboardViewModel
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root

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
        (activity as MainActivity?)?.setActionBarTitle("Login")

        if (viewModel.isLoggedIn()) {
            Navigation.findNavController(view).navigate(action)
        }


        binding.btnLogin.setOnClickListener {
            val valid = viewModel.validateUser(username.text.toString(), password.text.toString())
            if (valid) {
                Navigation.findNavController(it).navigate(action)
            } else {
                username.text?.clear()
                password.text?.clear()
                username.requestFocus()
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(username, InputMethodManager.SHOW_IMPLICIT)
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

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            activity?.finish()
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


}