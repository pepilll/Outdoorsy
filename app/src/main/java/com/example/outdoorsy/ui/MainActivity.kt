package com.example.outdoorsy.ui

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outdoorsy.databinding.ActivityMainBinding
import com.example.outdoorsy.model.VehicleData
import com.example.outdoorsy.model.data.Data
import com.example.outdoorsy.model.includedData.IncludedDataObjects
import com.example.outdoorsy.util.DataState
import com.example.outdoorsy.util.PaginationListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var vehicleRecyclerViewAdapter: VehicleRecyclerViewAdapter
    private lateinit var binding: ActivityMainBinding
    private var vehicleList: ArrayList<Data> = ArrayList()
    private var includedData: ArrayList<IncludedDataObjects> = ArrayList()

    private var startIndex = 0
    private val pageSize = 20
    private var isLoadMoreItems = true
    private var filter: String = ""

    private val REQUEST_CODE_SPEACH_INPUT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        subscribeProgressBarObserver()
        subscribeDataStateObservers()
        initRecyclerView(filter)
        mainActivityViewModel.setStateEvent(
            binding.include.searchTextView.text.toString(),
            pageSize,
            startIndex
        )
        initRecyclerView(filter)
        binding.include.micButton.setOnClickListener {
            speak()
        }
        binding.include.searchButton.setOnClickListener {
            vehicleList.clear()
            includedData.clear()
            filter = binding.include.searchTextView.text.toString()
            searchRentals(filter)
            startIndex = 0
            binding.recyclerView.scrollToPosition(0)
        }
    }
    private fun speak() {
        REQUEST_CODE_SPEACH_INPUT
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.EXTRA_LANGUAGE_MODEL
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault())
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEACH_INPUT)
        } catch (е: Exception) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        REQUEST_CODE_SPEACH_INPUT
        when (requestCode) {
            REQUEST_CODE_SPEACH_INPUT -> if (resultCode == RESULT_OK && data != null) {
                val result =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                binding.include.searchTextView.setText(result[0], TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun subscribeDataStateObservers() {
        mainActivityViewModel.dataStateLiveData.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<VehicleData?> -> {
                    vehicleRecyclerViewAdapter.notifyDataSetChanged()
                    if (dataState.data!!.data.isNotEmpty()) {
                        if (dataState.data.meta.total < pageSize) {
                            mainActivityViewModel.isLastPage = true
                        }
                        for (vehicle in dataState.data.data) {
                            vehicleList.add(vehicle)
                        }
                        for (vehicle in dataState.data.included!!) {
                            includedData.add(vehicle)
                        }
                    }
                    isLoadMoreItems = true
                    mainActivityViewModel.setProgressLiveData(false)
                }
                is DataState.Error -> {
                    mainActivityViewModel.setProgressLiveData(false)
                }
                is DataState.Loading -> {
                    mainActivityViewModel.setProgressLiveData(true)
                }
            }
        }
    }

    private fun subscribeProgressBarObserver() {
        mainActivityViewModel.progressLiveData.observe(this) { state ->
            binding.progressBar.isVisible = state
        }
    }

    private fun initRecyclerView(filter: String) {
        vehicleRecyclerViewAdapter = VehicleRecyclerViewAdapter()
        binding.recyclerView.apply {
            vehicleRecyclerViewAdapter.submitData(vehicleList, includedData)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = vehicleRecyclerViewAdapter
            addOnScrollListener(object :
                PaginationListener(layoutManager as LinearLayoutManager, pageSize) {
                override fun loadMoreItems() {
                    if (!isLoading && isLoadMoreItems && !isLastPage) {
                        startIndex += pageSize
                        mainActivityViewModel.setStateEvent(
                            filter,
                            pageSize,
                            startIndex
                        )
                        isLoadMoreItems = false
                    }
                }
                override val isLastPage: Boolean
                    get() = mainActivityViewModel.isLastPage
                override val isLoading: Boolean
                    get() = mainActivityViewModel.progressLiveData.value ?: false
            })
        }
    }

    private fun searchRentals(filter: String) {
        mainActivityViewModel.setStateEvent(filter, pageSize, startIndex)
    }
}