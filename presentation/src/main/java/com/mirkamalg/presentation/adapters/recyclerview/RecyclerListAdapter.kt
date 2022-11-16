package com.mirkamalg.presentation.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

/**
 * Created by Mirkamal Gasimov on 05.10.2021.
 */

class RecyclerListAdapter<VB : ViewBinding, D : Any> @JvmOverloads constructor(
    private val onInflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val onBind: (VB, D, Int) -> Unit,
    private val recyclingEnabled: Boolean = true,
    diffCallback: DiffUtil.ItemCallback<D> = GenericDiffCallback()
) : ListAdapter<D, RecyclerViewHolder<VB, D>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<VB, D> {
        return RecyclerViewHolder.create(parent, onInflate, onBind).apply {
            setIsRecyclable(recyclingEnabled)
        }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder<VB, D>, position: Int) {
        holder.bind(getItem(position))
    }

}