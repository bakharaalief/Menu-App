package com.bakharaalief.menuapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bakharaalief.menuapp.data.remote.response.StepsItem
import com.bakharaalief.menuapp.databinding.InstructionItemBinding

class InstructionListAdapter : ListAdapter<StepsItem, InstructionListAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    class MyViewHolder(binding: InstructionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val instructionNumber = binding.instructionNumber
        private val instructionText = binding.instructionText

        fun bind(stepsItem: StepsItem) {
            instructionNumber.text = stepsItem.number.toString()
            instructionText.text = stepsItem.step
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InstructionItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<StepsItem> =
            object : DiffUtil.ItemCallback<StepsItem>() {
                override fun areItemsTheSame(oldItem: StepsItem, newItem: StepsItem): Boolean {
                    return oldItem.number == newItem.number
                }

                override fun areContentsTheSame(
                    oldItem: StepsItem,
                    newItem: StepsItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}