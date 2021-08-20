package com.example.tequilapp.barlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tequilapp.R
import com.example.tequilapp.database.Bar
import com.example.tequilapp.databinding.ListItemBarBinding

class BarListAdapter(val clickListener: BarItemListener) : RecyclerView.Adapter<ViewHolder>() {
    var data = listOf<Bar>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() =data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder private constructor(val binding: ListItemBarBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(
        item: Bar,
        position: Int,
        clickListener: BarItemListener
    ) {
        binding.bar = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
        if (position % 2 == 0)
            binding.root.setBackgroundColor(binding.root.resources.getColor(R.color.yellow_dark))
        else
            binding.root.setBackgroundColor(binding.root.resources.getColor(R.color.yellow_light))
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val binding = ListItemBarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }
    }
}

class BarItemListener(val clickListener: (barId: Long) -> Unit){
    fun onClick(bar: Bar) = clickListener(bar.barId)
}
