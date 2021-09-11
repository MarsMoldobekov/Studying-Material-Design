package com.example.studyingmaterialdesign.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studyingmaterialdesign.databinding.FragmentPodBinding
import com.example.studyingmaterialdesign.viewmodel.PODData
import com.example.studyingmaterialdesign.viewmodel.ViewModel

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
        viewModel.getLiveDataPOD().observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    private fun renderData(podData: PODData) {
        when(podData) {
            is PODData.Error -> TODO()
            PODData.Loading -> TODO()
            is PODData.Success -> TODO()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}