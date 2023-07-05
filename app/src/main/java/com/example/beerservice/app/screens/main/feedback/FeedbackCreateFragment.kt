package com.example.beerservice.app.screens.main.feedback

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentCreateFeedbackBinding
import java.io.ByteArrayOutputStream

class FeedbackCreateFragment : BaseFragment(R.layout.fragment_create_feedback) {

    private lateinit var addImage: ImageView
    private lateinit var addFeedbackButton: Button
    private lateinit var frameLayout: FrameLayout


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
}