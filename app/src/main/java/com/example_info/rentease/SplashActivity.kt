package com.example_info.rentease

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Show the splash screen for 2 seconds
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000) // 2000 milliseconds = 2 seconds
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//
//        val textRent: TextView = findViewById(R.id.text_rent)
//        val textEase: TextView = findViewById(R.id.text_ease)
//
//        val animationRent = AnimationUtils.loadAnimation(this, R.anim.drop_down)
//        val animationEase = AnimationUtils.loadAnimation(this, R.anim.rise_up)
//        val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
//
//        textRent.visibility = TextView.VISIBLE
//        textEase.visibility = TextView.VISIBLE
//
//        textRent.startAnimation(animationRent)
//        textEase.startAnimation(animationEase)
//
//        animationRent.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation) {}
//
//            override fun onAnimationEnd(animation: Animation) {
//                textRent.startAnimation(bounce)
//                textEase.startAnimation(bounce)
//            }
//
//            override fun onAnimationRepeat(animation: Animation) {}
//        })
//
//        bounce.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation) {}
//
//            override fun onAnimationEnd(animation: Animation) {
//                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//                finish()
//            }
//
//            override fun onAnimationRepeat(animation: Animation) {}
//        })
//    }
}
