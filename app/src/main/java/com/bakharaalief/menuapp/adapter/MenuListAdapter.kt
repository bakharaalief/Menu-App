package com.bakharaalief.menuapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bakharaalief.menuapp.R
import com.bakharaalief.menuapp.data.local.enitity.MenuEntity
import com.bakharaalief.menuapp.databinding.MenuItemBinding
import com.bumptech.glide.Glide

class MenuListAdapter : ListAdapter<MenuEntity, MenuListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class MyViewHolder(binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val menuItem = binding.menuItem
        private val menuPhoto = binding.menuItemPhoto
        private val menuName = binding.menuItemName

        fun bind(menuEntity: MenuEntity) {
            Glide
                .with(menuItem.context)
                .load(menuEntity.image)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(menuPhoto)

            menuName.text = menuEntity.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MenuItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)

        holder.bind(data)
        holder.menuItem.setOnClickListener {
            onItemClickCallback.onClick(data)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onClick(menuEntity: MenuEntity)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MenuEntity> =
            object : DiffUtil.ItemCallback<MenuEntity>() {
                override fun areItemsTheSame(oldItem: MenuEntity, newItem: MenuEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: MenuEntity,
                    newItem: MenuEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}