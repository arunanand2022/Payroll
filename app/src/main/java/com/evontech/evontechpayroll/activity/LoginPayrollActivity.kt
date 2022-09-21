package com.evontech.evontechpayroll.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.evontech.evontechpayroll.R
import com.evontech.evontechpayroll.utils.util
import com.evontech.evontechpayroll.utils.util.Companion.LOGINCHECKIN


class LoginPayrollActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_payroll)

        //val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        //setSupportActionBar(toolbar)
        val checkTodayLogin = util.getStringPreferences(this, LOGINCHECKIN, "")!!.split(" ")
        val checkTodayLogout = util.getStringPreferences(this, "logoutDateTime", "")
        if (checkTodayLogin[0].equals(util.getTodayDateOnly()) && checkTodayLogout.equals("")){
            val intent =  Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            var navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentView) as NavHostFragment
            navController= navHostFragment.findNavController()
            setupActionBarWithNavController(navController)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}