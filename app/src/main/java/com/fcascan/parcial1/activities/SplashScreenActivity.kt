package com.fcascan.parcial1.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.findNavController
import com.fcascan.parcial1.R
import com.fcascan.parcial1.consts.SPLASH_TIME_OUT

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    fun exitSplashActivity() {
        finish()
    }
}