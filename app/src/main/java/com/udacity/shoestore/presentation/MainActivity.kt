package com.udacity.shoestore.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udacity.shoestore.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
    }
}
