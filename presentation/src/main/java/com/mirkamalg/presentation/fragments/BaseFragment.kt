package com.mirkamalg.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by Mirkamal on 16 November 2022
 */

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    abstract val onInflate: (LayoutInflater, ViewGroup?, Boolean) -> B
    abstract val onBindView: B.() -> Unit
    var binding: B? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = onInflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            onBindView(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}