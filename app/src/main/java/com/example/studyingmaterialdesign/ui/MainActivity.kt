package com.example.studyingmaterialdesign.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studyingmaterialdesign.R
import com.example.studyingmaterialdesign.ui.fragments.PODFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_container, PODFragment.newInstance()).commit()
        }
    }
}