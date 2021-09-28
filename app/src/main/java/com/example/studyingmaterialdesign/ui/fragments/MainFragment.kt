package com.example.studyingmaterialdesign.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.studyingmaterialdesign.R
import com.example.studyingmaterialdesign.databinding.FragmentMainBinding

class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    companion object {
        fun newInstance(): Fragment = MainFragment()
    }

    private val onMenuItemClickListener = Toolbar.OnMenuItemClickListener { item: MenuItem? ->
        when (item?.itemId) {
            R.id.action_nasa -> {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://www.nasa.gov/")
                })
                true
            }
            R.id.action_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_main_container, MainPreferencesFragment.newInstance())
                    .addToBackStack(MainPreferencesFragment.MAIN_PREFERENCES_FRAGMENT_TAG).commit()
                true
            }
            else -> item?.let { super.onOptionsItemSelected(it) } == true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnMenuItemClickListener(onMenuItemClickListener)
        (childFragmentManager.findFragmentById(R.id.fragments_container) as NavHostFragment)
            .navController.let { NavigationUI.setupWithNavController(binding.navigationView, it) }
    }
}