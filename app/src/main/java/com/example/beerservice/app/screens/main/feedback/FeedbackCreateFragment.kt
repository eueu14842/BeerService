package com.example.beerservice.app.screens.main.feedback

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.auth.SignUpViewModel
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentCreateFeedbackBinding
import java.io.ByteArrayOutputStream

class FeedbackCreateFragment : BaseFragment(R.layout.fragment_create_feedback) {

    private lateinit var addImage: ImageView
    private lateinit var addFeedbackButton: Button
    private lateinit var frameLayout: FrameLayout

    private val args by navArgs<FeedbackCreateFragmentArgs>()
    lateinit var binding: FragmentCreateFeedbackBinding
    override val viewModel: FeedbackCreateViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateFeedbackBinding.bind(view)
        addFeedbackButton = binding.addFeedbackButton
        addImage = binding.addImage


        binding.addImage.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }
        observeDetails()


    }


    private fun observeDetails() {
        setBeerId(args.beerId)
        viewModel.getBeerDetails()
        viewModel.beer.observe(viewLifecycleOwner) { result ->
            result.map { beer ->
                with(binding.beerView) {
                    loadPhoto(imageViewBeer, beer.image)
                    textViewBeerTitle.text = beer.name
                    textViewBeerDesc.text = beer.description
                    stileBeer.text = beer.style

                    textViewBeerFeedBack.isVisible = false
                }
            }
        }
    }

    private fun loadPhoto(imageView: ImageView, url: String?) {
        val context = imageView.context
        Glide.with(context).load(url).into(imageView)
    }

    private fun setBeerId(beerId: Int) {
        viewModel.setBeerId(beerId)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) { state ->
        binding.ratingBar.isEnabled = state.enableViews
        binding.editTexctFeedbackText.isEnabled = state.enableViews
        fillError(binding.editTexctFeedbackText, state.textErrorMessageRes)

        binding.progressBar.visibility = if (state.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun fillError(input: TextView, @StringRes stringRes: Int) {
        if (stringRes == SignUpViewModel.NO_ERROR_MESSAGE) {
            input.error = null
        } else {
            input.error = getString(stringRes)
        }
    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent? = it.data
                val imgUri = data?.data

                val bitmap: Bitmap? = imgUri?.let { uri ->
                    val inputStream = context?.contentResolver?.openInputStream(uri)
                    BitmapFactory.decodeStream(inputStream)
                }
                if (bitmap != null) addImage.setImageBitmap(bitmap)
            }
        }

    fun bitmapToBase(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    private fun observeGoBackEvent() = viewModel.goBackEvent.observeEvent(viewLifecycleOwner) {
        findNavController().popBackStack()
    }

    private fun observeShowSuccessFeedbackPublishedMessageEvent() =
        viewModel.showToastEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

}