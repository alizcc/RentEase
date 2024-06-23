package com.example_info.rentease.features

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example_info.rentease.database.dao.UserDao
import com.example_info.rentease.database.entity.UserEntity
import com.example_info.rentease.databinding.FragmentRegisterBinding
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

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val navigator: AliceNavigator by lazy {
        AliceNavigator(parentFragmentManager)
    }
    private val userDao: UserDao by lazy {
        AliceInitializer.getDatabase(requireContext()).userDao()
    }
    private val preferences: MainPreferences by lazy {
        AliceInitializer.getMainPreferences(requireContext())
    }

    private fun registerUser() {
        val userEntity = with(binding.incRegister) {
            UserEntity(
                username = txtUsername.text.toString(),
                password = txtPassword.text.toString(),
                fullName = txtName.text.toString(),
                phone = txtMobileNumber.text.toString(),
                email = txtEmail.text.toString(),
            )
        }
        lifecycleScope.launch {
            try {
                // check user is already exist or not
                val user = userDao.findByEmail(userEntity.email)
                user?.let {
                    binding.incRegister.txtEmail.showErrorAndFocus("Email is already registered")
                    return@launch
                }

                userDao.insertAll(userEntity)
                showToast("Successfully registered!")

                userDao.findByEmail(userEntity.email)?.let {
                    // save current user id
                    preferences.currentUserId = it.id
                    navigator.navigate(HomeFragment(), true)
                }
            } catch (e: Exception) {
                Log.e("RegisterFragment", e.stackTraceToString())
                showToast(e.message.orEmpty())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.incRegister.tvLogin.setOnClickListener {
            navigator.navigate(LoginFragment(), true)
        }
        binding.incRegister.btnRegister.setOnClickListener {
            checkValidation()
        }
    }

    private fun clearTxtErrors() {
        with(binding.incRegister) {
            txtName.error = null
            txtUsername.error = null
            txtEmail.error = null
            txtMobileNumber.error = null
            txtPassword.error = null
            txtConfirmPassword.error = null
        }
    }

    private fun checkValidation() {
        clearTxtErrors()

        with(binding.incRegister) {

            val fullName = txtName.text?.toString().orEmpty()
            if (fullName.isBlank()) {
                txtName.showErrorAndFocus("Full name is required")
                return
            }
            if (fullName.length < 3) {
                txtUsername.showErrorAndFocus("Full name should be at least 3 characters")
                return
            }

            val username = txtUsername.text?.toString().orEmpty()
            if (username.isBlank()) {
                txtUsername.showErrorAndFocus("Username is required")
                return
            }
            if (username.length < 6) {
                txtUsername.showErrorAndFocus("Username should be at least 6 characters")
                return
            }

            val email = txtEmail.text?.toString().orEmpty()
            if (email.isBlank()) {
                txtEmail.showErrorAndFocus("Email is required")
                return
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtEmail.showErrorAndFocus("Invalid email address")
                return
            }

            val phone = txtMobileNumber.text?.toString().orEmpty()
            if (phone.isBlank()) {
                txtMobileNumber.showErrorAndFocus("Mobile number is required")
                return
            }
            if (!Patterns.PHONE.matcher(phone).matches()) {
                txtMobileNumber.showErrorAndFocus("Invalid mobile number")
                return
            }

            val password = txtPassword.text?.toString().orEmpty()
            if (password.isBlank()) {
                txtPassword.showErrorAndFocus("Password is required")
                return
            }
            if (password.length < 6) {
                txtPassword.showErrorAndFocus("Password should be at least 6 characters")
                return
            }

            val password2 = txtConfirmPassword.text?.toString().orEmpty()
            if (password2.isBlank()) {
                txtConfirmPassword.showErrorAndFocus("Confirm Password is required")
                return
            }
            if (password != password2) {
                txtConfirmPassword.showErrorAndFocus("Passwords are not matched")
                return
            }

            registerUser()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRegisterBinding.inflate(
            inflater, container, false
        ).also { binding = it }.root
    }

}
