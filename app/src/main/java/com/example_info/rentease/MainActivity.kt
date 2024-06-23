package com.example_info.rentease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example_info.rentease.di.AliceInitializer
import com.example_info.rentease.features.HomeFragment
import com.example_info.rentease.features.LoginFragment
import com.example_info.rentease.navigation.AliceNavigator
import com.example_info.rentease.preferences.MainPreferences

class MainActivity : AppCompatActivity() {

    private val preferences: MainPreferences by lazy {
        AliceInitializer.getMainPreferences(this)
    }

    private val navigator: AliceNavigator by lazy {
        AliceNavigator(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkUserLogin()
    }

    private fun checkUserLogin() {
        if (preferences.isLoggedIn) {
            navigator.navigate(HomeFragment(), true)
        } else {
            navigator.navigate(LoginFragment(), true)
        }
    }

    override fun onDestroy() {
        AliceInitializer.destroy()
        super.onDestroy()
    }

}