package com.example.beerservice.app.screens.main.feedback

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.main.auth.SignUpViewModel
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentCreateFeedbackBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File

class FeedbackCreateFragment : BaseFragment(R.layout.fragment_create_feedback) {

    private lateinit var addImage: ImageView
    private lateinit var addFeedbackButton: Button
    private lateinit var frameLayout: FrameLayout
    private lateinit var ratingBar: RatingBar
    private val args by navArgs<FeedbackCreateFragmentArgs>()
    lateinit var binding: FragmentCreateFeedbackBinding
    override val viewModel: FeedbackCreateViewModel by viewModels { ViewModelFactory() }
    var imageName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateFeedbackBinding.bind(view)
        addFeedbackButton = binding.addFeedbackButton
        addImage = binding.addImage
        ratingBar = binding.ratingBarRateIt

        observeDetails()
        observeGoBackEvent()
        observeState()
        observeShowSuccessFeedbackPublishedMessageEvent()

        //использую registerForActivityResult
        binding.addImage.setOnClickListener { onIntentMediaStoreImages() }
        binding.addFeedbackButton.setOnClickListener { onCreateFeedbackButtonClick() }


        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            ratingBar.rating = rating
        }
    }

    private fun onCreateFeedbackButtonClick() {
        viewModel.createFeedback(
            binding.editTexctFeedbackText.text.toString(),
            binding.ratingBarRateIt.numStars,
            MultipartBody.Part.create(onCreateRequestBody())
        )
    }


    private fun onIntentMediaStoreImages() {
        val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        changeImage.launch(pickImg)
    }

    private fun onCreateRequestBody(): RequestBody {
        val albumDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imagePath =
            albumDirectory?.absolutePath + "/$imageName"
        val imageFile = File(imagePath)
        return imageFile.asRequestBody("image/*".toMediaTypeOrNull())

    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent? = it.data
                val imgUri = data?.data

                val projection = arrayOf(MediaStore.Images.Media.DATA)

                imgUri?.let { uri ->
                    context?.contentResolver?.query(uri, projection, null, null, null)
                        ?.use { cursor ->
                            cursor.moveToFirst()
                            val columnIndex =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                            val imagePath = cursor.getString(columnIndex)
                            imageName = imagePath?.substringAfterLast("/")
                        }
                }

                val bitmap: Bitmap? = imgUri?.let { uri ->
                    val inputStream = context?.contentResolver?.openInputStream(uri)
                    BitmapFactory.decodeStream(inputStream)
                }
                if (bitmap != null) {
                    addImage.setImageBitmap(bitmap)
                }
            }
        }

    //если будет нужен стринг
    private fun bitmapToBase(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }


    private fun observeDetails() {
        setBeerId(args.beerId)
        viewModel.getBeerDetails()
        viewModel.beer.observe(viewLifecycleOwner) { result ->
            result.map { beer ->
                with(binding.beerView) {
                    loadPhoto(imageViewBeer, beer.image)
                    textViewBeerTitle.text = beer.name
                    stileBeer.text = beer.style
                    abv.text = beer.abv.toString()
                    ibu.text = beer.ibu.toString()
                    beerRating.text = beer.averageRating.toString()
                    totalAverage.text = beer.totalReviews.toString()
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
        binding.ratingBarRateIt.isEnabled = state.enableViews
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


    private fun observeGoBackEvent() = viewModel.goBackEvent.observeEvent(viewLifecycleOwner) {
        findNavController().popBackStack()
    }

    private fun observeShowSuccessFeedbackPublishedMessageEvent() =
        viewModel.showToastEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

}