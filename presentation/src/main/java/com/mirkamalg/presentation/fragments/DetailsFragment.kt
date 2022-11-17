package com.mirkamalg.presentation.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.mirkamalg.presentation.adapters.recyclerview.RecyclerListAdapter
import com.mirkamalg.presentation.databinding.FragmentDetailsBinding
import com.mirkamalg.presentation.databinding.ItemRecordsBinding
import com.mirkamalg.presentation.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Mirkamal on 16 Noyabr 2022
 */

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override val onInflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate
    override val onBindView: FragmentDetailsBinding.() -> Unit
        get() = {
            configureUI()
            observeValues()
            setListeners()
            detailsViewModel.loadSettings(args.coinName)
            detailsViewModel.loadRecords(args.coinId)
        }

    private fun observeValues() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    detailsViewModel.minMax.collect {
                        binding?.textInputLayoutMin?.editText?.setText(it.first.toString())
                        binding?.textInputLayoutMax?.editText?.setText(it.second.toString())
                    }
                }
                launch {
                    detailsViewModel.records.collect {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun configureUI() {
        binding?.apply {
            textViewCoinName.text = args.coinName
            recyclerView.adapter = adapter
        }
    }

    private fun setListeners() {
        binding?.buttonSave?.setOnClickListener {
            detailsViewModel.saveSettings(
                args.coinName,
                binding?.textInputLayoutMin?.editText?.text.toString(),
                binding?.textInputLayoutMax?.editText?.text.toString()
            )
        }
    }

    private val adapter by lazy {
        RecyclerListAdapter<ItemRecordsBinding, Double>(
            onInflate = ItemRecordsBinding::inflate,
            onBind = { binding, data, _ ->
                binding.textViewValue.text = data.toString()
            }
        )
    }
}