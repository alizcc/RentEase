package com.example_info.rentease.features

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example_info.rentease.database.dao.PropertyDao
import com.example_info.rentease.database.dao.UserDao
import com.example_info.rentease.database.mapper.toEntity
import com.example_info.rentease.databinding.FragmentCreatePostBinding
import com.example_info.rentease.di.AliceInitializer
import com.example_info.rentease.mock.getCityList
import com.example_info.rentease.mock.getRegionList
import com.example_info.rentease.mock.getSampleTabTypes
import com.example_info.rentease.model.RentDetailItem
import com.example_info.rentease.navigation.AliceNavigator
import com.example_info.rentease.preferences.MainPreferences
import com.example_info.rentease.util.helper.DateHelper
import com.example_info.rentease.util.helper.showToast
import kotlinx.coroutines.launch

class CreatePostFragment : Fragment() {

    private val navigator: AliceNavigator by lazy {
        AliceNavigator(parentFragmentManager)
    }
    private lateinit var binding: FragmentCreatePostBinding

    private val preferences: MainPreferences by lazy {
        AliceInitializer.getMainPreferences(requireContext())
    }
    private val userDao: UserDao by lazy {
        AliceInitializer.getDatabase(requireContext()).userDao()
    }
    private val propertyDao: PropertyDao by lazy {
        AliceInitializer.getDatabase(requireContext()).propertyDao()
    }
    private var userFullName = ""
    private val cityList = getCityList()
    private val regionList = getRegionList()
    private val propertyTypePairList = getSampleTabTypes()
    private var createdDetailItem: RentDetailItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareUserInfo()
        setUpTypeSpinner()
        setUpCitySpinner()
        setUpRegionSpinner()
        setUpListeners()
    }

    private fun prepareUserInfo() {
        lifecycleScope.launch {
            val userId = preferences.currentUserId
            if (userId > 0) {
                userDao.findById(userId)?.let { user ->
                    userFullName = user.fullName
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.btnBack.setOnClickListener {
            navigator.popBackStack()
        }
        binding.btnPost.setOnClickListener {
            checkValidation()
        }
    }

    private fun createPost(createdDetailItem: RentDetailItem) {
        lifecycleScope.launch {
            propertyDao.insert(createdDetailItem.toEntity())
            showToast("Successfully created!")
            navigator.popBackStack()
        }
    }

    private fun checkValidation() {
        val city = binding.spinnerCity.selectedItem.toString()
        val region = binding.spinnerRegion.selectedItem.toString()
        val type = binding.spinnerType.selectedItem.toString()
        val typeIndex = binding.spinnerType.selectedItemPosition
        val quarter = binding.editTextQuarter.text.toString().trim()
        val price = binding.editTextPrice.text.toString().trim()
        val facilities = binding.editTextFacilityList.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val contactPhone = binding.editTextContactPhone.text.toString().trim()

        var isValid = true

        if (city.isEmpty()) {
            isValid = false
            showToast("Please select a city")
        }

        if (region.isEmpty()) {
            isValid = false
            showToast("Please select a township")
        }

        if (quarter.isEmpty()) {
            isValid = false
            binding.editTextQuarter.error = "Please enter a quarter"
        }

        if (type.isEmpty()) {
            isValid = false
            showToast("Please select a property type")
        }

        if (price.isEmpty()) {
            isValid = false
            binding.editTextPrice.error = "Please enter a price"
        } else if (!price.isDigitsOnly()) {
            isValid = false
            binding.editTextPrice.error = "Price must be a number"
        }

        if (facilities.isEmpty()) {
            isValid = false
            binding.editTextFacilityList.error = "Please enter facilities"
        }

        if (description.isEmpty()) {
            isValid = false
            binding.editTextDescription.error = "Please enter a description"
        }

        if (contactPhone.isEmpty()) {
            isValid = false
            binding.editTextContactPhone.error = "Please enter a contact phone number"
        } else if (!contactPhone.matches(Regex("^\\+?[0-9]{10,13}\$"))) {
            isValid = false
            binding.editTextContactPhone.error = "Please enter a valid phone number"
        }

        if (isValid) {
            createdDetailItem = RentDetailItem(
                id = 0,
                previewImage = "",
                region = region,
                quarter = quarter,
                city = city,
                type = propertyTypePairList[typeIndex].first,
                price = price.toLong(),
                creatorName = userFullName,
                creatorId = preferences.currentUserId.toString(),
                description = description,
                facilityList = facilities.split(",").map { it.trim() },
                images = emptyList(),
                date = DateHelper.todayFormattedDate,
                contactPhone = contactPhone,
                totalRating = 0,
                ratingList = emptyList(),
                interestedList = emptyList(),
            ).also {
                createPost(it)
            }
        }
    }

    private fun setUpTypeSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            propertyTypePairList.map { it.second }
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapter
        binding.spinnerType.setSelection(0)
    }

    private fun setUpCitySpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            cityList
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerCity.adapter = adapter
        binding.spinnerCity.setSelection(0)
    }

    private fun setUpRegionSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            regionList
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerRegion.adapter = adapter
        binding.spinnerRegion.setSelection(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCreatePostBinding.inflate(
            inflater, container, false
        ).also { binding = it }.root
    }

}