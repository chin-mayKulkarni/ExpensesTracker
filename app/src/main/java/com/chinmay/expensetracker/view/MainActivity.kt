package com.chinmay.expensetracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.chinmay.expensetracker.R
import com.chinmay.expensetracker.model.User
import com.chinmay.expensetracker.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var viewModel : DashboardViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        /*usersList = listOf(User("user1", "pass1"),
            User("user2", "pass2"),
            User("user3", "pass3"),
            User("user4", "pass4")
        )


        viewModel.initializeUsers(usersList)
        viewModel.fetchUser()*/

        initToolBar()


    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment?
        ?: return

        with(navHostFragment.navController){
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this, appBarConfiguration)
        }

    }

    fun setActionBarTitle(title: String){
        supportActionBar?.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment.navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}