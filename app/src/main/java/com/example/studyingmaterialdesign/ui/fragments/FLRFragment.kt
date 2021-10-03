package com.example.studyingmaterialdesign.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyingmaterialdesign.databinding.FragmentFlrBinding
import com.example.studyingmaterialdesign.ui.adapters.RecyclerViewAdapter
import com.example.studyingmaterialdesign.viewmodel.FLRData
import com.example.studyingmaterialdesign.viewmodel.ViewModel
import com.google.android.material.snackbar.Snackbar

class FLRFragment : Fragment() {
    private var _binding: FragmentFlrBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModel by lazy {
        ViewModelProvider(this@FLRFragment).get(ViewModel::class.java)
    }

    private val recyclerViewAdapter = RecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlrBinding.inflate(inflater, container, false)
        with(binding.fragmentFlrRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter
        }
        //TODO(input startDate and endDate)
        viewModel.loadFLR("2021-09-01", "2021-10-01")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveDataFLR().observe(viewLifecycleOwner) { renderData(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerViewAdapter.removeData()
    }

    private fun renderData(flrData: FLRData) {
        when (flrData) {
            is FLRData.Error -> {
                binding.fragmentFlrLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, flrData.throwable.toString(), Snackbar.LENGTH_SHORT)
                    .setAction("RE-LOAD") { viewModel.loadFLR("2021-09-01", "2021-10-01") }
                    .show()
            }
            FLRData.Loading -> {
                binding.fragmentFlrLoadingLayout.visibility = View.VISIBLE
            }
            is FLRData.Success -> {
                binding.fragmentFlrLoadingLayout.visibility = View.GONE
                with(recyclerViewAdapter) {
                    flrResponseData = flrData.listOfFLRResponse
                    notifyItemRangeInserted(0, flrData.listOfFLRResponse.size)
                }
            }
        }
    }
}