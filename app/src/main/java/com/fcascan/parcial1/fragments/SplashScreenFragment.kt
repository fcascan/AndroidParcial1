package com.fcascan.parcial1.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fcascan.parcial1.R
import com.fcascan.parcial1.activities.LoginActivity
import com.fcascan.parcial1.activities.MainActivity
import com.fcascan.parcial1.activities.SplashScreenActivity
import com.fcascan.parcial1.consts.ITEM_IMG_SIZE_MULTIPLIER
import com.fcascan.parcial1.consts.SPLASH_SCREEN_SIZE_MULTIPLIER
import com.fcascan.parcial1.consts.SPLASH_SCREEN_URL
import com.fcascan.parcial1.consts.SPLASH_TIME_OUT

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    lateinit var v : View
    lateinit var imgSplash: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        imgSplash = v.findViewById(R.id.imgSplash)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshImage()

        val splashActivity = activity as SplashScreenActivity
        Handler(Looper.getMainLooper()).postDelayed ({
            startActivity(Intent(splashActivity, LoginActivity::class.java))
            splashActivity.exitSplashActivity()
        }, SPLASH_TIME_OUT)
    }

    private fun refreshImage() {
        Glide.with(this)
            .load(SPLASH_SCREEN_URL)
            .error(
                Glide.with(this)
                    .load(R.drawable.not_found)
                    .centerCrop()
                    .circleCrop()
                    .sizeMultiplier(SPLASH_SCREEN_SIZE_MULTIPLIER)
            )
            .centerCrop()
            .circleCrop()
            .sizeMultiplier(SPLASH_SCREEN_SIZE_MULTIPLIER)
            .into(imgSplash)
    }
}