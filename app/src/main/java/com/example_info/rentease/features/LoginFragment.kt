package com.example_info.rentease.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example_info.rentease.database.dao.UserDao
import com.example_info.rentease.databinding.FragmentLoginBinding
import com.example_info.rentease.di.AliceInitializer
import com.example_info.rentease.navigation.AliceNavigator
import com.example_info.rentease.preferences.MainPreferences
import com.example_info.rentease.util.helper.showErrorAndFocus
import com.example_info.rentease.util.helper.showToast
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 09/06/2024
 */

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val navigator: AliceNavigator by lazy {
        AliceNavigator(parentFragmentManager)
    }
    private val userDao: UserDao by lazy {
        AliceInitializer.getDatabase(requireContext()).userDao()
    }
    private val preferences: MainPreferences by lazy {
        AliceInitializer.getMainPreferences(requireContext())
    }

    private fun doLogin() {
        val email = binding.incLogin.txtInputEmail.text.toString()
        val password = binding.incLogin.txtInputPassword.text.toString()
        lifecycleScope.launch {
            val user = userDao.findByEmail(email) ?: run {
                binding.incLogin.txtInputEmail.showErrorAndFocus("Email is not registered")
                return@launch
            }

            if (user.password != password) {
                binding.incLogin.txtInputPassword.showErrorAndFocus("Incorrect password")
                return@launch
            }
            // save current user id
            preferences.currentUserId = user.id

            showToast("Logged in successfully")
            navigator.navigate(HomeFragment(), true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.incLogin.tvRegister.setOnClickListener {
            navigator.navigate(RegisterFragment(), true)
        }
        binding.incLogin.btnSignIn.setOnClickListener {
            checkValidation()
        }
    }

    private fun clearTxtErrors() {
        with(binding.incLogin) {
            txtInputEmail.error = null
            txtInputPassword.error = null
        }
    }

    private fun checkValidation() {
        clearTxtErrors()

        with(binding.incLogin) {

            val email = txtInputEmail.text?.toString().orEmpty()
            if (email.isBlank()) {
                txtInputEmail.showErrorAndFocus("Email is required")
                return
            }
            val password = txtInputPassword.text?.toString().orEmpty()
            if (password.isBlank()) {
                txtInputPassword.showErrorAndFocus("Password is required")
                return
            }

            doLogin()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentLoginBinding.inflate(
            inflater, container, false
        ).also { binding = it }.root
    }

}