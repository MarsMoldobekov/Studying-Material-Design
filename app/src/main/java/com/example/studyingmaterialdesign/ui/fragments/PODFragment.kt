package com.example.studyingmaterialdesign.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.studyingmaterialdesign.R
import com.example.studyingmaterialdesign.databinding.FragmentPodBinding
import com.example.studyingmaterialdesign.viewmodel.PODData
import com.example.studyingmaterialdesign.viewmodel.ViewModel
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate

//TODO(add viewpager2 for two images: regular and in hd)
class PODFragment : ViewBindingFragment<FragmentPodBinding>(FragmentPodBinding::inflate) {
    private val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textInputLayout.setEndIconOnClickListener {
            startActivityActionView("https://en.wikipedia.org/wiki/${binding.textInputEditText.text.toString()}")
        }

        with(viewModel) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                loadPOD(LocalDate.now().toString())
            }
            getLiveDataPOD().observe(viewLifecycleOwner) { renderData(it) }
        }
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
                    .setAction("RE-LOAD") {  }.show()
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
}