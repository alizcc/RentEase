package com.example_info.rentease.features

import android.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example_info.rentease.adapter.MiniImageAdapter
import com.example_info.rentease.database.dao.PropertyDao
import com.example_info.rentease.database.dao.UserDao
import com.example_info.rentease.database.mapper.toDomain
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePostFragment : Fragment() {

    private val postIdToUpdate: Long?
        get() = arguments?.getLong(KEY_ID)

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
    private var oldDetailItem: RentDetailItem? = null
    private var createdDetailItem: RentDetailItem? = null
    private lateinit var miniImageAdapter: MiniImageAdapter

    private lateinit var pickPhotoLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickMultiplePhotoLauncher: ActivityResultLauncher<Intent>

    private var posterImage: Uri? = null
    private val _miniImagesState = MutableStateFlow(emptyList<Uri>())
    val miniImagesState = _miniImagesState.asStateFlow()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPhotoPicker()
        setUpMultiplePhotoPicker()
        prepareUserInfo()
        setUpTypeSpinner()
        setUpCitySpinner()
        setUpRegionSpinner()
        setUpOtherImagesAdapter()
        loadToUpdate()
        setUpListeners()
        listenImageState()
    }

    private fun setUpOtherImagesAdapter() {
        miniImageAdapter = MiniImageAdapter { uriToDelete ->
            miniImagesState.value
            if (miniImagesState.value.contains(uriToDelete)) {
                _miniImagesState.update { list ->
                    list.filter { it != uriToDelete }
                }
            }
        }
        binding.rcyImages.adapter = miniImageAdapter
        binding.rcyImages.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun refreshPosterImage() {
        val showImage = posterImage != null
        binding.llPosterEmpty.isVisible = !showImage
        binding.ivPoster.isVisible = showImage
        if (showImage) {
            Glide
                .with(requireContext())
                .load(posterImage)
                .into(binding.ivPoster)
        }
    }

    private fun listenImageState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                miniImagesState.collectLatest {
                    miniImageAdapter.submitList(it)
                }
            }
        }
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

    private fun setUpMultiplePhotoPicker() {
        pickMultiplePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val uri: Uri = result.data?.data ?: return@registerForActivityResult
                    _miniImagesState.update {
                        it + uri
                    }
                }
            }
    }

    private fun loadToUpdate() {
        if (postIdToUpdate == null) return

        lifecycleScope.launch {
            propertyDao
                .getById(postIdToUpdate!!)
                ?.toDomain(preferences.currentUserId.toString())
                ?.let { item ->
                    oldDetailItem = item
                    binding.editTextQuarter.setText(item.quarter)
                    binding.editTextPrice.setText(item.price.toString())
                    binding.editTextFacilityList.setText(item.facilityList.joinToString(", "))
                    binding.editTextDescription.setText(item.description)
                    binding.editTextContactPhone.setText(item.contactPhone)

                    val cityIndex = cityList.indexOf(item.city).takeIf { it > -1 } ?: 0
                    binding.spinnerCity.setSelection(cityIndex)

                    val regionIndex = regionList.indexOf(item.region).takeIf { it > -1 } ?: 0
                    binding.spinnerRegion.setSelection(regionIndex)

                    binding.tvTitle.text = "Update Rent Post"
                    binding.btnPost.text = "UPDATE NOW"

                    val typeIndex = propertyTypePairList.indexOfFirst { it.first == item.type }
                        .takeIf { it > -1 } ?: 0
                    binding.spinnerType.setSelection(typeIndex)

                    try {
                        posterImage = Uri.parse(item.previewImage)
                        refreshPosterImage()
                        _miniImagesState.update {
                            item.images.map { Uri.parse(it) }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        }
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
        binding.btnAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickMultiplePhotoLauncher.launch(intent)
        }
        binding.cvPreviewImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickPhotoLauncher.launch(intent)
        }
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
            if (oldDetailItem == null) {
                showToast("Successfully created!")
            } else {
                showToast("Successfully updated!")
            }
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
            createdDetailItem = oldDetailItem?.copy(
                region = region,
                quarter = quarter,
                city = city,
                type = propertyTypePairList[typeIndex].first,
                price = price.toLong(),
                creatorName = userFullName,
                creatorId = preferences.currentUserId.toString(),
                description = description,
                facilityList = facilities.split(",").map { it.trim() },
                contactPhone = contactPhone,
            ) ?: RentDetailItem(
                id = 0,
                previewImage = posterImage?.toString().orEmpty(),
                region = region,
                quarter = quarter,
                city = city,
                type = propertyTypePairList[typeIndex].first,
                price = price.toLong(),
                creatorName = userFullName,
                creatorId = preferences.currentUserId.toString(),
                description = description,
                facilityList = facilities.split(",").map { it.trim() },
                images = miniImagesState.value.map { it.toString() },
                date = DateHelper.todayFormattedDate,
                contactPhone = contactPhone,
                totalRating = 0,
                ratingList = emptyList(),
                interestedList = emptyList(),
            )
            createPost(createdDetailItem!!)
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
        if (binding.spinnerType.selectedItemPosition < 0) {
            binding.spinnerType.setSelection(0)
        }
    }

    private fun setUpCitySpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            cityList
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerCity.adapter = adapter
        if (binding.spinnerCity.selectedItemPosition < 0) {
            binding.spinnerCity.setSelection(0)
        }
    }

    private fun setUpRegionSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            regionList
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerRegion.adapter = adapter
        if (binding.spinnerRegion.selectedItemPosition < 0) {
            binding.spinnerRegion.setSelection(0)
        }
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

    companion object {
        private const val KEY_IMAGE_TYPE_SINGLE = 102
        private const val KEY_IMAGE_TYPE_MULTIPLE = 103

        private const val KEY_IMAGE_PICKER_TYPE = "key-image-picker-type"
        private const val KEY_ID = "key-post-id"
        fun instanceForUpdate(postId: Long): CreatePostFragment {
            return CreatePostFragment().apply {
                arguments = Bundle().apply {
                    putLong(KEY_ID, postId)
                }
            }
        }
    }

}