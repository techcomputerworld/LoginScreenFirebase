package com.techcomputerworld.helpchatgpt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread.sleep(1000)
        screenSplash.setKeepOnScreenCondition { false }
        /* Codigo para pasar al otro activity
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
        finish();
        */
    }

}