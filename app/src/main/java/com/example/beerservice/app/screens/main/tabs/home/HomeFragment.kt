package com.example.beerservice.app.screens.main.tabs.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.SearchView.OnCloseListener
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.budiyev.android.codescanner.*
import com.example.beerservice.R
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeerAdblockAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.OnBeerAdblockClickListener
import com.example.beerservice.app.screens.main.tabs.home.brewery.adapters.BreweryAdblockAdapter
import com.example.beerservice.app.screens.main.tabs.home.brewery.adapters.OnBreweryClickListener
import com.example.beerservice.app.screens.main.tabs.home.places.adapters.PlaceAdblockAdapter
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.app.utils.observeResult
import com.example.beerservice.app.utils.setupScanner
import com.example.beerservice.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding

    override val viewModel: HomeViewModel by viewModels()

    lateinit var breweryRecycler: RecyclerView
    lateinit var beerRecycler: RecyclerView
    lateinit var placeRecycler: RecyclerView
    lateinit var searchView: SearchView
    private lateinit var codeScanner: CodeScanner
    var scannerView: CodeScannerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        initializeViews()
        observeBreweryAdblock()
        observeBeerAdblock()
        observePlaceAdblock()
        observeOnNavigateToPlaceDetailsEvent()
        observeOnToggleFavoriteEvent()

        searchView.setOnQueryTextListener(onQueryTextListener)
        searchView.setOnCloseListener(onCloseSearchListener)
        scannerView = view.findViewById(R.id.scanner_view)
        codeScanner = CodeScanner(requireActivity(), scannerView!!)
        binding.imageViewScanner.setOnClickListener { initializeScanner() }

        binding.showAllBreweryTextView.setOnClickListener { navigateToBreweryListEvent() }
        binding.showAllBeerTextView.setOnClickListener { navigateToBeerListEvent() }
        binding.showAllStoresTextView.setOnClickListener { navigateToPlaceListEvent() }

    }

    private val onCloseSearchListener = OnCloseListener {
        hideKeyboard()
        true
    }

    private fun initializeScanner() {
        onRequestCameraPermissions()
        viewModel.isAvailableScanner(true)
        if (checkCameraPermission()) {
            startScanning()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA_REQUEST
            )
        }
    }

    private fun startScanning() {
        viewModel.isAvailableScanner.setupScanner(this, binding.scannerViewState) {
            codeScanner.camera = CodeScanner.CAMERA_BACK
            codeScanner.formats = CodeScanner.ALL_FORMATS
            codeScanner.autoFocusMode = AutoFocusMode.SAFE
            codeScanner.scanMode = ScanMode.SINGLE
            codeScanner.isAutoFocusEnabled = true
            codeScanner.isFlashEnabled = false
            codeScanner.startPreview()
            codeScanner.decodeCallback = DecodeCallback {
                activity?.runOnUiThread {
                    navigateToSearchEvent(it.text)
                }
            }
            scannerView?.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            navigateToSearchEvent(p0 ?: "")
            return true
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            return false
        }
    }

    private fun initializeViews() {
        searchView = binding.searchView

        with(binding) {
            recyclerBrewery.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
            recyclerBeer.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
            recyclerPlaces.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
        }

        breweryRecycler = binding.recyclerBrewery
        beerRecycler = binding.recyclerBeer
        placeRecycler = binding.recyclerPlaces
    }

    private fun observeBreweryAdblock() {
        binding.resultViewState.setTryAgainAction { println(R.string.action_try_again) }
        viewModel.brewery.observeResult(this, binding.root, binding.resultViewState) { breweries ->
            val adapter = BreweryAdblockAdapter(breweries, onBreweryClickListener)
            breweryRecycler.adapter = adapter
        }
    }

    private fun observeBeerAdblock() {
        binding.resultViewState.setTryAgainAction { println(R.string.try_again) }
        viewModel.beer.observeResult(this, binding.root, binding.resultViewState) { beers ->
            val adapter = BeerAdblockAdapter(beers, onBeerAdblockClickListener)
            beerRecycler.adapter = adapter
        }
    }

    private fun observePlaceAdblock() {
        binding.resultViewState.setTryAgainAction { println(R.string.try_again) }
        viewModel.place.observeResult(this, binding.root, binding.resultViewState) { stores ->
            val adapter = PlaceAdblockAdapter(stores, viewModel)
            placeRecycler.adapter = adapter
        }
    }

    private val onBreweryClickListener = object : OnBreweryClickListener {
        override fun onBreweryClick(brewery: Brewery, position: Int) {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToBreweryDetailsFragment(brewery.id!!)
            findNavController().navigate(direction)
        }
    }

    private val onBeerAdblockClickListener = object : OnBeerAdblockClickListener {
        override fun onBeerClick(beer: Beer, position: Int) {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToBeerDetailsFragment(beer.id!!)
            findNavController().navigate(direction)
        }
    }

    private fun observeOnNavigateToPlaceDetailsEvent() {
        viewModel.onNavigateToMapPlaceDetails.observeEvent(viewLifecycleOwner) {
            val direction =
                HomeFragmentDirections
                    .actionHomeFragmentToPlaceDetailsFragment(it)
            findNavController().navigate(direction)
        }
    }


    private fun observeOnToggleFavoriteEvent() {
        viewModel.onToggleFavoriteEvent.observeEvent(viewLifecycleOwner) {
        }
    }

    private fun navigateToBreweryListEvent() {
        findNavController().navigate(R.id.action_homeFragment_to_breweryListFragment)
    }

    private fun navigateToBeerListEvent() {
        findNavController().navigate(R.id.action_homeFragment_to_beersListFragment)
    }

    private fun navigateToPlaceListEvent() {
        findNavController().navigate(R.id.action_homeFragment_to_placeListFragment)
    }

    private fun navigateToSearchEvent(searchBy: String) {
        val direction = HomeFragmentDirections.actionHomeFragmentToSearchFragment(searchBy)
        findNavController().navigate(direction)
    }

    private fun onRequestCameraPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.CAMERA,
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }


    companion object {

        private const val PERMISSION_CAMERA_REQUEST = 1

        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard(it) }
        }

        private fun Context.hideKeyboard(view: View) {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

