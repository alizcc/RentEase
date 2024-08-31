package com.example_info.rentease.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example_info.rentease.adapter.RentPreviewAdapter
import com.example_info.rentease.adapter.RentPreviewAutoSliderAdapter
import com.example_info.rentease.database.dao.PropertyDao
import com.example_info.rentease.database.dao.UserDao
import com.example_info.rentease.database.mapper.toPreview
import com.example_info.rentease.databinding.FragmentHomeBinding
import com.example_info.rentease.di.AliceInitializer
import com.example_info.rentease.mock.getSampleRentPreviewItems
import com.example_info.rentease.mock.getSampleTabTypes
import com.example_info.rentease.model.RentPreviewItem
import com.example_info.rentease.navigation.AliceNavigator
import com.example_info.rentease.preferences.MainPreferences
import com.example_info.rentease.util.helper.tryLoad
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val preferences: MainPreferences by lazy {
        AliceInitializer.getMainPreferences(requireContext())
    }
    private val userDao: UserDao by lazy {
        AliceInitializer.getDatabase(requireContext()).userDao()
    }
    private val propertyDao: PropertyDao by lazy {
        AliceInitializer.getDatabase(requireContext()).propertyDao()
    }

    private val navigator: AliceNavigator by lazy {
        AliceNavigator(parentFragmentManager)
    }
    private lateinit var binding: FragmentHomeBinding
    private var sliderAdapter: RentPreviewAutoSliderAdapter? = null
    private lateinit var itemAdapter: RentPreviewAdapter
    private val propertyTypes = getSampleTabTypes()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        loadUserInfo()
        setUpItemsAdapter()
        loadSliderItems()
        loadTabItems()

        reloadItems()
    }

    private fun setUpListeners() {
        binding.edtSearch.setOnClickListener {
            navigator.navigate(SearchingFragment())
        }
        binding.btnAdd.setOnClickListener {
            navigator.navigate(CreatePostFragment())
        }
        binding.cvProfile.setOnClickListener {
            navigator.navigate(ProfileFragment())
        }
        binding.tvTitle.setOnClickListener {
            navigator.navigate(ProfileFragment())
        }
    }

    private fun loadUserInfo() {
        lifecycleScope.launch {
            val userId = preferences.currentUserId
            if (userId > 0) {
                userDao.findById(userId)?.let { user ->
                    binding.tvTitle.text = user.fullName
                    binding.ivProfile.tryLoad(user.image)
                }
            }
        }
    }

    private fun loadTabItems() {
        val types = propertyTypes
        types.forEach { type ->
            val tab = binding.tlTypes.newTab()
            tab.text = type.second
            tab.tag = type.first
            binding.tlTypes.addTab(tab)
        }
        binding.tlTypes.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val selectedType = it.tag.toString()
                    reloadItems(selectedType)
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabReselected(p0: TabLayout.Tab?) {}

        })
    }

    private fun setUpItemsAdapter() {
        itemAdapter = RentPreviewAdapter(::onClickItem)
        binding.rcyItems.adapter = itemAdapter
        binding.rcyItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClickItem(item: RentPreviewItem) {
        navigator.navigate(DetailFragment.instance(item.id))
    }

    private fun reloadItems(type: String? = null) {
        val selectedType = type ?: propertyTypes[binding.tlTypes.selectedTabPosition].first

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                val items = propertyDao.getAllByType(selectedType)
                itemAdapter.submitList(items.map { it.toPreview() })
            }
        }
    }

    private fun loadSliderItems() {
        val items = getSampleRentPreviewItems(5)
        sliderAdapter = RentPreviewAutoSliderAdapter(
            items = items,
            onClick = ::onClickItem
        )
        binding.pagerAutoSlides.adapter = sliderAdapter
        binding.pagerAutoSlides.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeBinding.inflate(
            inflater, container, false
        ).also { binding = it }.root
    }

}
