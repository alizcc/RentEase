package com.example_info.rentease.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example_info.rentease.adapter.RentPreviewAdapter
import com.example_info.rentease.databinding.FragmentSearchingBinding
import com.example_info.rentease.mock.getSampleRentPreviewItems
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.RangeSlider.OnSliderTouchListener
import kotlin.math.roundToLong

class SearchingFragment : Fragment() {

    private lateinit var binding: FragmentSearchingBinding

    private lateinit var adapter: RentPreviewAdapter

    private var filteredRegion: String = ""
    private var filteredCity: String = ""
    private var filteredQuarter: String = ""
    private var filteredMinPrice: Long = 0
    private var filteredMaxPrice: Long = Long.MAX_VALUE / 1000000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpAdapter()
        refreshFilterList()
    }

    private fun setUpAdapter() {
        adapter = RentPreviewAdapter {
            Toast.makeText(requireContext(), it.region, Toast.LENGTH_SHORT).show()
        }
        binding.rcyItems.adapter = adapter
        binding.rcyItems.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun refreshFilterList() {
        var filteredItems = getSampleRentPreviewItems(20)
        if (filteredRegion.isNotBlank()) {
            filteredItems = filteredItems.filter {
                it.region.contains(filteredRegion, true)
            }
        }

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
            it.price in ((filteredMinPrice * 100000)..(filteredMaxPrice * 100000))
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
        binding.edtSearchRegion.addTextChangedListener { edt ->
            edt?.toString()?.let {
                filteredRegion = it
                refreshFilterList()
            }
        }
        binding.edtSearchCity.addTextChangedListener { edt ->
            edt?.toString()?.let {
                filteredCity = it
                refreshFilterList()
            }
        }
        binding.edtSearchQuarter.addTextChangedListener { edt ->
            edt?.toString()?.let {
                filteredQuarter = it
                refreshFilterList()
            }
        }
        binding.sliderPrice.addOnSliderTouchListener(object : OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = slider.values
                if (values.size < 2) {
                    return
                }
                filteredMinPrice = values[0].roundToLong()
                filteredMaxPrice = values[1].roundToLong()
                binding.tvPriceRange.text = "$filteredMinPrice Lakh - $filteredMaxPrice Lakh"
                refreshFilterList()
            }
        })
        binding.cbCondo.setOnCheckedChangeListener { _, isChecked ->
            refreshFilterList()
        }
        binding.cbHouse.setOnCheckedChangeListener { _, isChecked ->
            refreshFilterList()
        }
        binding.cbStudio.setOnCheckedChangeListener { _, isChecked ->
            refreshFilterList()
        }
        binding.cbBungalow.setOnCheckedChangeListener { _, isChecked ->
            refreshFilterList()
        }
        binding.cbApartment.setOnCheckedChangeListener { _, isChecked ->
            refreshFilterList()
        }
        binding.cbTownHouse.setOnCheckedChangeListener { _, isChecked ->
            refreshFilterList()
        }
    }

    private fun createTypeList(): List<String> {
        val filteredTypes = mutableListOf<String>()

        filteredTypes.apply {
            if (binding.cbCondo.isChecked) {
                add("condo")
            }
            if (binding.cbHouse.isChecked) {
                add("house")
            }
            if (binding.cbStudio.isChecked) {
                add("studio")
            }
            if (binding.cbBungalow.isChecked) {
                add("bungalow")
            }
            if (binding.cbApartment.isChecked) {
                add("apartment")
            }
            if (binding.cbTownHouse.isChecked) {
                add("townhouse")
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

}
