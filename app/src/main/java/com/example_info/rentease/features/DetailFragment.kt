package com.example_info.rentease.features

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example_info.rentease.R
import com.example_info.rentease.adapter.RentFacilityAdapter
import com.example_info.rentease.adapter.RentImageSliderAdapter
import com.example_info.rentease.database.dao.PropertyDao
import com.example_info.rentease.database.mapper.toDomain
import com.example_info.rentease.database.mapper.toEntity
import com.example_info.rentease.databinding.FragmentDetailBinding
import com.example_info.rentease.di.AliceInitializer
import com.example_info.rentease.mock.getSampleTabTypes
import com.example_info.rentease.model.RentDetailItem
import com.example_info.rentease.navigation.AliceNavigator
import com.example_info.rentease.preferences.MainPreferences
import com.example_info.rentease.util.helper.asCommaSeparated
import com.example_info.rentease.util.helper.roundToDecimalPlace
import com.example_info.rentease.util.helper.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private val postId: Long
        get() = arguments?.getLong(KEY_ID) ?: throw IllegalArgumentException("Need to pass post ID")

    private val navigator: AliceNavigator by lazy {
        AliceNavigator(parentFragmentManager)
    }

    private val propertyDao: PropertyDao by lazy {
        AliceInitializer.getDatabase(requireContext()).propertyDao()
    }
    private lateinit var binding: FragmentDetailBinding
    private var imageSliderAdapter: RentImageSliderAdapter? = null
    private lateinit var facilityAdapter: RentFacilityAdapter

    private val _detailState = MutableStateFlow<RentDetailItem?>(null)
    private val detailState = _detailState.asStateFlow()

    private val preferences: MainPreferences by lazy {
        AliceInitializer.getMainPreferences(requireContext())
    }

    private val userId: String
        get() = preferences.currentUserId.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFacilityAdapter()
        observeState()
        setUpListeners()

        fetchDetails()
    }

    private fun fetchDetails() {
        lifecycleScope.launch {
            propertyDao.getById(postId)?.toDomain(userId)?.let { detail ->
                _detailState.update { detail }
            } ?: showToast("No such rental post")
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            showToast("Invalid phone number")
        }
    }

    private fun setUpListeners() {
        binding.btnBack.setOnClickListener {
            navigator.popBackStack()
        }
        binding.btnRate.setOnClickListener {
            showRatingDialog()
        }
        binding.btnInterest.setOnClickListener {
            toggleInterest()
        }
        binding.tvOwnerPhone.setOnClickListener {
            detailState.value?.contactPhone?.let {
                dialPhoneNumber(it)
            }
        }
    }

    private fun showRatingDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_rate_rent, null)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Submit") { _, _ ->
                val rating = ratingBar.rating
                // Handle the submitted rating here
                // For example, you might want to send the rating to your server
                // or save it locally
                doRating(rating.toInt())
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun doRating(rating: Int) {
        lifecycleScope.launch {
            detailState.value?.let {
                val detail = it.copy(
                    hasRated = true,
                    totalRating = it.totalRating + rating,
                    ratingList = it.ratingList + userId
                )
                propertyDao.update(detail.toEntity())
                fetchDetails()
            }
        }
    }

    private fun toggleInterest() {
        lifecycleScope.launch {
            detailState.value?.let { item ->
                val detail = item.copy(
                    hasInterested = !item.hasInterested,
                    interestedList = if (item.interestedList.contains(userId)) {
                        item.interestedList.filter { it != userId }
                    } else {
                        item.interestedList + userId
                    }
                )
                propertyDao.update(detail.toEntity())
                fetchDetails()
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                detailState.collectLatest { details ->
                    if (details == null) {
                        return@collectLatest
                    }
                    if (details.facilityList.isNotEmpty()) {
                        facilityAdapter.submitList(details.facilityList)
                    }
                    if (details.images.isNotEmpty()) {
                        setUpImageSlider(details.images)
                    } else {
                        setUpImageSlider(listOf(""))
                    }
                    binding.tvDescription.text = details.description
                    binding.tvName.text = getSampleTabTypes().find {
                        it.first.equals(details.type, true)
                    }?.second ?: details.type

                    binding.tvPrice.text = details.price.asCommaSeparated
                    binding.tvLocation.text =
                        "${details.quarter}၊ ${details.region}၊ ${details.city}"

                    val avgRating = if (details.totalRatingCount <= 0) 0 else
                        (details.totalRating.toDouble() / details.totalRatingCount.toDouble())
                            .roundToDecimalPlace(2)

                    binding.tvInterestRating.text = getString(
                        R.string.msg_rating_and_interest,
                        avgRating.toString(),
                        details.interestedCount.toString()
                    )

                    binding.llContact.isVisible = details.hasInterested
                    binding.tvOwner.text = details.creatorName
                    binding.tvOwnerPhone.text = details.contactPhone

                    // for interest button
                    val (interestIcon, interestText) = if (details.hasInterested) {
                        R.drawable.ic_fav_white_full to "No interested"
                    } else {
                        R.drawable.ic_fav_white_outline to "Interest"
                    }
                    binding.btnInterest.text = interestText
                    binding.btnInterest.setCompoundDrawablesWithIntrinsicBounds(
                        interestIcon,
                        0,
                        0,
                        0
                    )

                    // for rate button
                    val (rateIcon, rateText) = if (details.hasRated) {
                        R.drawable.ic_star_white_full to "Rated"
                    } else {
                        R.drawable.ic_star_white_outline to "Rate"
                    }
                    binding.btnRate.text = rateText
                    binding.btnRate.setCompoundDrawablesWithIntrinsicBounds(rateIcon, 0, 0, 0)
                    binding.btnRate.isEnabled = !details.hasRated
                    binding.btnRate.isVisible = details.hasInterested
                }
            }
        }
    }

    private fun setUpFacilityAdapter() {
        facilityAdapter = RentFacilityAdapter()
        binding.rcyFacilities.adapter = facilityAdapter
        binding.rcyFacilities.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpImageSlider(list: List<String>) {
        imageSliderAdapter = RentImageSliderAdapter(list)
        binding.pagerImageSlides.adapter = imageSliderAdapter
        binding.pagerImageSlides.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailBinding.inflate(
            inflater, container, false
        ).also { binding = it }.root
    }

    companion object {
        private const val KEY_ID = "key-post-id"
        fun instance(postId: Long): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(KEY_ID, postId)
                }
            }
        }
    }

}
