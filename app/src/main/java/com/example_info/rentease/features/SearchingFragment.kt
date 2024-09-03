package com.example_info.rentease.features

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example_info.rentease.adapter.RentPreviewAdapter
import com.example_info.rentease.database.dao.PropertyDao
import com.example_info.rentease.database.mapper.toPreview
import com.example_info.rentease.databinding.FragmentSearchingBinding
import com.example_info.rentease.di.AliceInitializer
import com.example_info.rentease.mock.getCityList
import com.example_info.rentease.mock.getRegionList
import com.example_info.rentease.model.RentPreviewItem
import com.example_info.rentease.navigation.AliceNavigator
import com.example_info.rentease.util.helper.asCommaSeparated
import com.example_info.rentease.util.helper.showToast
import kotlinx.coroutines.launch

class SearchingFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentSearchingBinding

    private lateinit var adapter: RentPreviewAdapter

    private val navigator: AliceNavigator by lazy {
        AliceNavigator(parentFragmentManager)
    }

    private val propertyDao: PropertyDao by lazy {
        AliceInitializer.getDatabase(requireContext()).propertyDao()
    }

    private val cityList = getCityList()
    private val regionList = getRegionList()

    private var filteredQuarter: String = ""
    private val priceList = (0..120).map { 50000L + (it * 50000L) }

    private var filteredMinPrice: Long = priceList[0]
    private var filteredMinPriceIndex: Int = -1
    private var filteredMaxPrice: Long = priceList[priceList.size - 1]
    private var filteredMaxPriceIndex: Int = -1

    private var previewItemList: List<RentPreviewItem> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinners()
        setUpCitySpinner()
        setUpRegionSpinner()
        setUpListeners()
        setUpAdapter()
        loadPreviewItems()
    }

    private fun loadPreviewItems() {
        lifecycleScope.launch {
            val items = propertyDao.getAll()
            previewItemList = items.map { it.toPreview() }
            refreshFilterList()
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

    private fun setUpSpinners() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            priceList.map { it.asCommaSeparated }
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerMaxPrice.adapter = adapter
        binding.spinnerMinPrice.adapter = adapter

        // Set default selections
        binding.spinnerMinPrice.setSelection(0)
        binding.spinnerMaxPrice.setSelection(priceList.size - 1)
    }

    private fun setUpAdapter() {
        adapter = RentPreviewAdapter {
            Toast.makeText(requireContext(), it.region, Toast.LENGTH_SHORT).show()
        }
        binding.rcyItems.adapter = adapter
        binding.rcyItems.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun refreshFilterList() {
        var filteredItems = previewItemList

        val filteredRegion = binding.spinnerRegion.selectedItem.toString()
        if (filteredRegion.isNotBlank()) {
            filteredItems = filteredItems.filter {
                it.region.contains(filteredRegion, true)
            }
        }

        val filteredCity = binding.spinnerCity.selectedItem.toString()
        if (filteredCity.isNotBlank()) {
            filteredItems = filteredItems.filter {
                it.city.contains(filteredCity, true)
            }
        }

        if (filteredQuarter.isNotBlank()) {
            filteredItems = filteredItems.filter {
                it.quarter.contains(filteredQuarter, true)
            }
        }

        filteredItems = filteredItems.filter {
            it.price in (filteredMinPrice..filteredMaxPrice)
        }

        val filteredTypes = createTypeList()
        if (filteredTypes.isNotEmpty()) {
            filteredItems = filteredItems.filter {
                filteredTypes.contains(it.type.lowercase())
            }
        }

        adapter.submitList(filteredItems)
        binding.tvResult.text = "Results (${filteredItems.size})"
    }

    private fun setUpListeners() {
        binding.btnBack.setOnClickListener {
            navigator.popBackStack()
        }
        binding.btnOpenFilter.setOnClickListener {
            binding.llFilter.isVisible = true
            binding.btnOpenFilter.isVisible = false
            binding.btnCloseFilter.isVisible = true
        }
        binding.btnCloseFilter.setOnClickListener {
            binding.llFilter.isVisible = false
            binding.btnOpenFilter.isVisible = true
            binding.btnCloseFilter.isVisible = false
        }
        binding.spinnerRegion.onItemSelectedListener = this
        binding.edtSearchQuarter.addTextChangedListener { edt ->
            edt?.toString()?.let {
                filteredQuarter = it
                refreshFilterList()
            }
        }
        binding.spinnerMinPrice.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedPrice = priceList[position]
                    if (selectedPrice > filteredMaxPrice) {
                        val index = filteredMinPriceIndex.takeIf { it > -1 } ?: 0
                        binding.spinnerMinPrice.setSelection(index)
                        showToast("Minimum price cannot be greater than maximum price")
                    } else {
                        filteredMinPriceIndex = position
                        filteredMinPrice = selectedPrice
                        binding.tvPriceRange.text =
                            "${filteredMinPrice.asCommaSeparated} - ${filteredMaxPrice.asCommaSeparated}"
                        refreshFilterList()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.spinnerMaxPrice.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedPrice = priceList[position]
                    if (selectedPrice < filteredMinPrice) {
                        val index = filteredMaxPriceIndex.takeIf { it > -1 } ?: (priceList.size - 1)
                        binding.spinnerMaxPrice.setSelection(index)
                        showToast("Maximum price cannot be less than minimum price")
                    } else {
                        filteredMaxPriceIndex = position
                        filteredMaxPrice = selectedPrice
                        binding.tvPriceRange.text =
                            "$filteredMinPrice Lakh - $filteredMaxPrice Lakh"
                        refreshFilterList()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        binding.cbCondo.setOnCheckedChangeListener { _, _ ->
            refreshFilterList()
        }
        binding.cbField.setOnCheckedChangeListener { _, _ ->
            refreshFilterList()
        }
        binding.cbHostel.setOnCheckedChangeListener { _, _ ->
            refreshFilterList()
        }
        binding.cbBungalow.setOnCheckedChangeListener { _, _ ->
            refreshFilterList()
        }
        binding.cbApartment.setOnCheckedChangeListener { _, _ ->
            refreshFilterList()
        }
        binding.cbMiniCondo.setOnCheckedChangeListener { _, _ ->
            refreshFilterList()
        }
    }

    private fun createTypeList(): List<String> {
        val filteredTypes = mutableListOf<String>()

        filteredTypes.apply {
            if (binding.cbCondo.isChecked) {
                add("condo")
            }
            if (binding.cbMiniCondo.isChecked) {
                add("minicondo")
            }
            if (binding.cbField.isChecked) {
                add("field")
            }
            if (binding.cbBungalow.isChecked) {
                add("bungalow")
            }
            if (binding.cbApartment.isChecked) {
                add("apartment")
            }
            if (binding.cbHostel.isChecked) {
                add("hostel")
            }
        }
        return filteredTypes
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSearchingBinding.inflate(
            inflater, container, false
        ).also { binding = it }.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        refreshFilterList()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        refreshFilterList()
    }

}
