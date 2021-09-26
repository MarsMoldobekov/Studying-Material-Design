package com.example.studyingmaterialdesign.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.studyingmaterialdesign.R

class MainPreferencesFragment : PreferenceFragmentCompat() {
    companion object {
        const val MAIN_PREFERENCES_FRAGMENT_TAG = "MAIN_PREFERENCES_FRAGMENT_TAG"

        fun newInstance(): PreferenceFragmentCompat = MainPreferencesFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey)
    }
}