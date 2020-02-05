package com.hadir.hadirapp.teacher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hadir.hadirapp.BR
import com.hadir.hadirapp.databinding.TeacherItemBinding
import com.hadir.hadirapp.model.TeacherModel

class TeacherAdapter(
    private val item: ArrayList<TeacherModel>,
    private val teacherUserActionListener: TeacherUserActionListener
) : RecyclerView.Adapter<TeacherAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val inflater = LayoutInflater.from(p0.context)
        val binding = TeacherItemBinding.inflate(inflater, p0, false)
        return Holder(binding.apply {
            binding.listener = teacherUserActionListener
        })
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(item[position])
    }

    inner class Holder(private val binding: TeacherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TeacherModel) {
            binding.setVariable(BR.model, item)
            binding.executePendingBindings()
        }
    }
}