package com.mirkamalg.presentation.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mirkamalg.presentation.databinding.FragmentHomeBinding
import com.mirkamalg.presentation.fragments.BaseFragment
import com.mirkamalg.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Mirkamal on 16 November 2022
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()

    override val onInflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    override val onBindView: FragmentHomeBinding.() -> Unit
        get() = {
            homeViewModel.getPrices("asdaad")
            observeValues()
            configureListeners()
        }

    private fun observeValues() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.loading.collect {
                    binding?.root?.isRefreshing = it
                }
            }
        }
    }

    private fun configureListeners() {
        binding?.root?.setOnRefreshListener {
            homeViewModel.getPrices("salam")
        }
    }
}