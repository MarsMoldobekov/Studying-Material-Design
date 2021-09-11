package com.example.studyingmaterialdesign.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.studyingmaterialdesign.BuildConfig
import com.example.studyingmaterialdesign.R
import com.example.studyingmaterialdesign.databinding.FragmentPodBinding
import com.example.studyingmaterialdesign.viewmodel.PODData
import com.example.studyingmaterialdesign.viewmodel.ViewModel
import com.google.android.material.snackbar.Snackbar

class PODFragment : Fragment() {
    companion object {
        fun newInstance(): Fragment = PODFragment()
    }

    private var _binding: FragmentPodBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
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
            chipGroup.setOnCheckedChangeListener { _, checkedId ->
                viewModel.load()
            }
            textInputLayout.setEndIconOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse(BuildConfig.WIKI_BASE_URL + binding.textInputEditText.text.toString())
                })
            }
        }

        with(viewModel) {
            load()
            getLiveDataPOD()?.observe(viewLifecycleOwner) {
                renderData(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun renderData(podData: PODData) {
        when (podData) {
            is PODData.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.imageView.setImageResource(R.drawable.ic_no_photo_vector)
                Snackbar.make(binding.root, podData.throwable.toString(), Snackbar.LENGTH_LONG)
                    .setAction("RE-LOAD") { viewModel.load() }.show()
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