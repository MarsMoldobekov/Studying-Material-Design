package com.example.studyingmaterialdesign.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.studyingmaterialdesign.BuildConfig
import com.example.studyingmaterialdesign.R
import com.example.studyingmaterialdesign.databinding.FragmentPodBinding
import com.example.studyingmaterialdesign.viewmodel.PODData
import com.example.studyingmaterialdesign.viewmodel.ViewModel
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.util.*

//TODO(add chipGroup for hdurl and url in settings)
class PODFragment : Fragment() {
    private var _binding: FragmentPodBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }

    private val onCheckedChangeListener = OnCheckedChangeListener()
    private var day = Days.TODAY

    private val onMenuItemClickListener = Toolbar.OnMenuItemClickListener { menuItem ->
        when (menuItem.itemId) {
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_main_container, MainPreferencesFragment.newInstance())
                    .addToBackStack(MainPreferencesFragment.MAIN_PREFERENCES_FRAGMENT_TAG)
                    .commit()
            }
            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT)
                .show()
        }
        return@OnMenuItemClickListener true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            chipGroup.check(R.id.chip_today)
            chipGroup.setOnCheckedChangeListener(onCheckedChangeListener)
            textInputLayout.setEndIconOnClickListener {
                startActivityActionView(BuildConfig.WIKI_BASE_URL + binding.textInputEditText.text.toString())
            }
            buttonNasa.setOnClickListener { startActivityActionView(BuildConfig.NASA_OFFICIAL_SITE) }
            bar.replaceMenu(R.menu.menu_bottom_app_bar)
            bar.setOnMenuItemClickListener(onMenuItemClickListener)
        }

        with(viewModel) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                load(LocalDate.now().toString())
            }
            getLiveDataPOD().observe(viewLifecycleOwner) { renderData(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startActivityActionView(uriString: String) {
        startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(uriString) })
    }

    private fun renderData(podData: PODData) {
        when (podData) {
            is PODData.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.imageView.setImageResource(R.drawable.ic_no_photo_vector)
                Snackbar.make(binding.root, podData.throwable.toString(), Snackbar.LENGTH_LONG)
                    .setAction("RE-LOAD") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            onCheckedChangeListener.loadByDay()
                        }
                    }.show()
            }
            PODData.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is PODData.Success -> {
                binding.loadingLayout.visibility = View.GONE
                podData.podResponse.url?.let { binding.imageView.load(it) }
                podData.podResponse.title?.let { binding.title.text = it }
                podData.podResponse.explanation?.let { binding.explanation.text = it }
            }
        }
    }

    companion object {
        fun newInstance(): Fragment = PODFragment()
    }

    private enum class Days { YESTERDAY, TODAY }

    private inner class OnCheckedChangeListener : ChipGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                day = when (checkedId) {
                    R.id.chip_yesterday -> Days.YESTERDAY
                    R.id.chip_today -> Days.TODAY
                    else -> day //CRUTCH
                }
                loadByDay()
            } else {
                //TODO(load date if Build.VERSION.SDK_INT <= Build.VERSION_CODES.O)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun loadByDay() {
            when (day) {
                Days.YESTERDAY -> viewModel.load(LocalDate.now().minusDays(1).toString())
                Days.TODAY -> viewModel.load(LocalDate.now().toString())
            }
        }
    }
}