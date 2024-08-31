package com.example_info.rentease.features

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example_info.rentease.database.dao.UserDao
import com.example_info.rentease.database.entity.UserEntity
import com.example_info.rentease.databinding.FragmentRegisterBinding
import com.example_info.rentease.di.AliceInitializer
import com.example_info.rentease.navigation.AliceNavigator
import com.example_info.rentease.preferences.MainPreferences
import com.example_info.rentease.util.helper.showErrorAndFocus
import com.example_info.rentease.util.helper.showToast
import com.example_info.rentease.util.helper.tryParseToUri
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 09/06/2024
 */

class RegisterFragment : Fragment() {

    private val oldUserId: Long?
        get() = arguments?.getLong(KEY_ID)

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
    private var oldUserEntity: UserEntity? = null

    private lateinit var pickPhotoLauncher: ActivityResultLauncher<Intent>
    private var posterImage: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        loadOldUser()
        setUpPhotoPicker()
    }

    private fun pickProfilePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickPhotoLauncher.launch(intent)
    }

    private fun setUpPhotoPicker() {
        pickPhotoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val uri: Uri = result.data?.data ?: return@registerForActivityResult
                    posterImage = uri
                    refreshPosterImage()
                }
            }
    }

    private fun refreshPosterImage() {
        val showImage = posterImage != null
        binding.incRegister.ivNoProfile.isVisible = !showImage
        binding.incRegister.ivPoster.isVisible = showImage
        if (showImage) {
            Glide
                .with(requireContext())
                .load(posterImage)
                .into(binding.incRegister.ivPoster)
        }
    }

    private fun registerUser() {
        val userEntity = with(binding.incRegister) {
            UserEntity(
                username = txtUsername.text.toString(),
                password = txtPassword.text.toString(),
                fullName = txtName.text.toString(),
                phone = txtMobileNumber.text.toString(),
                email = txtEmail.text.toString(),
                image = posterImage?.toString()
            ).apply {
                oldUserEntity?.let {
                    this.id = it.id
                }
            }
        }
        lifecycleScope.launch {
            try {
                // check user is already exist or not
                val user = userDao.findByEmail(userEntity.email)
                user?.let {
                    if (oldUserEntity == null) {
                        binding.incRegister.txtEmail.showErrorAndFocus("Email is already registered")
                        return@launch
                    }
                }

                userDao.insert(userEntity)
                showToast(
                    if (oldUserEntity != null) {
                        "Successfully updated!"
                    } else {
                        "Successfully registered!"
                    }
                )

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

    private fun loadOldUser() {
        if (oldUserId == null) return
        lifecycleScope.launch {
            val userEntity = userDao.findById(oldUserId!!) ?: return@launch
            oldUserEntity = userEntity
            binding.incRegister.txtName.setText(userEntity.fullName)
            binding.incRegister.txtUsername.setText(userEntity.username)
            binding.incRegister.txtEmail.setText(userEntity.email)
            binding.incRegister.txtMobileNumber.setText(userEntity.phone)
            binding.incRegister.tilOldPassword.isVisible = true
            binding.incRegister.btnRegister.text = "UPDATE"
            binding.incRegister.tvTitle.text = "Update Profile"
            binding.incRegister.tvLogin.isVisible = false
            binding.btnBack.isVisible = true
            posterImage = tryParseToUri(userEntity.image)
            refreshPosterImage()
        }
    }

    private fun setUpListeners() {
        binding.incRegister.tvLogin.setOnClickListener {
            navigator.navigate(LoginFragment(), true)
        }
        binding.incRegister.btnRegister.setOnClickListener {
            checkValidation()
        }
        binding.btnBack.setOnClickListener {
            navigator.popBackStack()
        }
        binding.incRegister.cvPreviewImage.setOnClickListener {
            pickProfilePhoto()
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

            if (oldUserEntity != null) {
                val oldPassword = txtOldPassword.text?.toString().orEmpty()
                if (oldPassword != oldUserEntity!!.password) {
                    txtOldPassword.showErrorAndFocus("Password is incorrect")
                    return
                }
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

    companion object {
        private const val KEY_ID = "key-user-id"
        fun instanceForUpdate(userId: Long): RegisterFragment {
            return RegisterFragment().apply {
                arguments = Bundle().apply {
                    putLong(KEY_ID, userId)
                }
            }
        }
    }

}
