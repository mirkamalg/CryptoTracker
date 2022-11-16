package com.mirkamalg.presentation.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.mirkamalg.presentation.databinding.FragmentDetailsBinding
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
            observeValues()
            configureUI()
            setListeners()
            detailsViewModel.loadSettings(args.coinName)
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
            }
        }
    }

    private fun configureUI() {
        binding?.apply {
            textViewCoinName.text = args.coinName
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
}