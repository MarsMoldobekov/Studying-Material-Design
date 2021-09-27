package com.example.studyingmaterialdesign.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.studyingmaterialdesign.R
import com.example.studyingmaterialdesign.ui.fragments.PODFragment

class MainActivity : AppCompatActivity() {
    private val onPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, _ -> recreate() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentTheme()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_container, PODFragment.newInstance()).commit()
        }
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(onPreferenceChangeListener)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(onPreferenceChangeListener)
    }

    //TODO: extract string resource
    private fun setCurrentTheme() {
        when (PreferenceManager.getDefaultSharedPreferences(this).getString("themes", "Original")) {
            "Original" -> setTheme(R.style.Theme_DayNight)
            "Black and White" -> setTheme(R.style.Theme_BlackAndWhite)
            "Red" -> setTheme(R.style.Theme_Red)
        }
    }
}