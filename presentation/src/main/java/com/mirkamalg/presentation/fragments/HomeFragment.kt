package com.mirkamalg.presentation.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mirkamalg.domain.models.PriceEntity
import com.mirkamalg.presentation.adapters.recyclerview.RecyclerListAdapter
import com.mirkamalg.presentation.databinding.FragmentHomeBinding
import com.mirkamalg.presentation.databinding.ItemCoinsBinding
import com.mirkamalg.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Mirkamal on 16 November 2022
 */

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()

    override val onInflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    override val onBindView: FragmentHomeBinding.() -> Unit
        get() = {
            homeViewModel.getPrices()
            observeValues()
            configureListeners()
        }

    private fun observeValues() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.loading.collect {
                        binding?.root?.isRefreshing = it
                    }
                }
                launch {
                    homeViewModel.coins.collect {
                        if (binding?.recyclerView?.adapter == null) {
                            binding?.recyclerView?.adapter = adapter
                        }
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun configureListeners() {
        binding?.root?.setOnRefreshListener {
            homeViewModel.getPrices()
        }
    }

    private val adapter by lazy {
        RecyclerListAdapter<ItemCoinsBinding, PriceEntity>(
            onInflate = ItemCoinsBinding::inflate,
            onBind = { binding, data, _ ->
                binding.apply {
                    textViewCoinName.text = data.cryptoName
                    textViewPrice.text = "\$${data.price}"
                    root.setOnClickListener {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                                data.cryptoName, data.id
                            )
                        )
                    }
                }
            }
        )
    }
}