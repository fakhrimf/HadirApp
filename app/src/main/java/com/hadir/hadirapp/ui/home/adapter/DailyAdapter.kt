package com.hadir.hadirapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hadir.hadirapp.BR
import com.hadir.hadirapp.databinding.DailyItemBinding
import com.hadir.hadirapp.model.DailyDataModel
import com.hadir.hadirapp.ui.home.listener.HomeActionListener

class DailyAdapter(
    private val item: ArrayList<DailyDataModel>,
    private val homeActionListener: HomeActionListener
) : RecyclerView.Adapter<DailyAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val inflater = LayoutInflater.from(p0.context)
        val binding = DailyItemBinding.inflate(inflater, p0, false)
        return Holder(binding.apply {
            binding.listener = homeActionListener
        })
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(item[position])
    }

    inner class Holder(private val binding: DailyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyDataModel) {
            binding.setVariable(BR.model, item)
            binding.executePendingBindings()
        }
    }
}