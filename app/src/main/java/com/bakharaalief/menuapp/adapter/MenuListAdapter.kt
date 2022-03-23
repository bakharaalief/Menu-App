package com.bakharaalief.menuapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bakharaalief.menuapp.R
import com.bakharaalief.menuapp.data.remote.response.ResultsItem
import com.bakharaalief.menuapp.databinding.MenuItemBinding
import com.bumptech.glide.Glide

class MenuListAdapter : ListAdapter<ResultsItem, MenuListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class MyViewHolder(binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val menuItem = binding.menuItem
        private val menuPhoto = binding.menuItemPhoto
        private val menuName = binding.menuItemName

        fun bind(resultsItem: ResultsItem) {
            Glide
                .with(menuItem.context)
                .load(resultsItem.image)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(menuPhoto)

            menuName.text = resultsItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MenuItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val resultsItem = getItem(position)

        holder.bind(resultsItem)
        holder.menuItem.setOnClickListener {
            onItemClickCallback.onClick(resultsItem)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onClick(resultsItem: ResultsItem)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<ResultsItem> =
            object : DiffUtil.ItemCallback<ResultsItem>() {
                override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ResultsItem,
                    newItem: ResultsItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}