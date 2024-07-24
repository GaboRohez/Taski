package com.gmail.gabow95k.taski.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.gabow95k.taski.data.Task
import com.gmail.gabow95k.taski.databinding.ItemDoneBinding

class DoneAdapter(var tasks: List<Task>, private val listener: DoneIn) :
    RecyclerView.Adapter<DoneAdapter.TaskViewHolder>() {

    interface DoneIn {
        fun onTaskDelete(task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemDoneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], listener, position)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(val binding: ItemDoneBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task, listener: DoneIn, position: Int) {
            binding.checkBox.text = task.name
            binding.checkBox.enable = false

            if (task.description.isNullOrEmpty()) {
                binding.tvDescription.visibility = View.GONE
            } else {
                binding.tvDescription.text = task.description
                binding.tvDescription.visibility = View.VISIBLE
            }

            binding.checkBox.isChecked = task.isCompleted
            updateTextStrikeThrough(task.isCompleted)

            binding.btnDelete.setOnClickListener {
                listener.onTaskDelete(task)
            }
        }

        private fun updateTextStrikeThrough(isChecked: Boolean) {
            binding.tvDescription.paintFlags = if (isChecked) {
                binding.tvDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

    }

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}