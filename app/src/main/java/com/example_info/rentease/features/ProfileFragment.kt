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
import com.bumptech.glide.Glide
import com.example_info.rentease.R
import com.example_info.rentease.adapter.RentPreviewAdapter
import com.example_info.rentease.database.dao.PropertyDao
import com.example_info.rentease.database.dao.UserDao
import com.example_info.rentease.database.entity.PropertyEntity
import com.example_info.rentease.database.mapper.toPreview
import com.example_info.rentease.databinding.FragmentProfileBinding
import com.example_info.rentease.di.AliceInitializer
import com.example_info.rentease.model.RentPreviewItem
import com.example_info.rentease.navigation.AliceNavigator
import com.example_info.rentease.preferences.MainPreferences
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

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
    private lateinit var binding: FragmentProfileBinding
    private lateinit var itemAdapter: RentPreviewAdapter
    private var selectedType: NavType = NavType.MyPosts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        loadUserInfo()
        setUpItemsAdapter()

        reloadItems()
    }

    private fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            preferences.logout()
            navigator.navigateAndClearAll(LoginFragment())
        }
        binding.btnEdit.setOnClickListener {
            navigator.navigate(RegisterFragment.instanceForUpdate(preferences.currentUserId))
        }
        binding.btnBack.setOnClickListener {
            navigator.popBackStack()
        }
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_my_posts -> {
                    selectedType = NavType.MyPosts
                    reloadItems()
                    true
                }

                R.id.nav_saved_posts -> {
                    selectedType = NavType.InterestedPosts
                    reloadItems()
                    true
                }

                else -> false
            }
        }
    }

    private fun loadUserInfo() {
        lifecycleScope.launch {
            Glide.with(requireContext())
                .load("https://i1.sndcdn.com/avatars-000339084123-nag0p1-t240x240.jpg")
                .into(binding.civProfile)

            val userId = preferences.currentUserId
            if (userId > 0) {
                userDao.findById(userId)?.let { user ->
                    binding.tvTitle.text = user.fullName
                }
            }
        }
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

    private fun filterInterestedPosts(entity: PropertyEntity): Boolean {
        return entity.interestedList.contains(preferences.currentUserId.toString())
    }

    private fun reloadItems() {
        val myPost = selectedType == NavType.MyPosts

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                val items = if (myPost) {
                    propertyDao.getAllByUserId(preferences.currentUserId.toString())
                } else {
                    propertyDao.getAll().filter(::filterInterestedPosts)
                }
                itemAdapter.submitList(items.map { it.toPreview() })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentProfileBinding.inflate(
            inflater, container, false
        ).also { binding = it }.root
    }

    enum class NavType {
        MyPosts, InterestedPosts;
    }

}
