package com.example.beerservice.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beerservice.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        Singletons.init(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }
}