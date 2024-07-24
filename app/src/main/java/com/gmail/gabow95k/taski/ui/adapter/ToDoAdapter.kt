package com.gmail.gabow95k.taski.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.gmail.gabow95k.taski.R
import com.gmail.gabow95k.taski.data.Task
import com.gmail.gabow95k.taski.databinding.ItemTaskBinding
import com.gmail.gabow95k.taski.ui.custom_view.TaskiCheckBox

class ToDoAdapter(var tasks: List<Task>, private val listener: ToDoIn) :
    RecyclerView.Adapter<ToDoAdapter.TaskViewHolder>() {

    private var currentAnimatedPosition: Int? = null

    interface ToDoIn {
        fun onTaskDelete(task: Task)
        fun onTaskEdit(task: Task)
        fun onTaskUpdate(task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], listener, position)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task, listener: ToDoIn, position: Int) {
            binding.checkBox.text = task.name

            if (task.description.isNullOrEmpty()) {
                binding.tvDescription.visibility = View.GONE
            } else {
                binding.tvDescription.text = task.description
                binding.tvDescription.visibility = View.VISIBLE
            }

            binding.checkBox.isChecked = task.isCompleted
            updateTextStrikeThrough(task.isCompleted)

            itemView.setOnLongClickListener {
                if (currentAnimatedPosition != null && currentAnimatedPosition != position) {
                    notifyItemChanged(currentAnimatedPosition!!)
                }
                currentAnimatedPosition = position
                binding.buttonContainer.visibility = View.VISIBLE
                val shake: Animation = AnimationUtils.loadAnimation(itemView.context, R.anim.shake)
                binding.buttonContainer.startAnimation(shake)
                true
            }

            if (currentAnimatedPosition != position) {
                stopAnimationAndHideButtons()
            }

            binding.btnEdit.setOnClickListener {
                listener.onTaskEdit(task)
            }

            binding.btnDelete.setOnClickListener {
                listener.onTaskDelete(task)
            }

            binding.checkBox.onCheckedChangeListener =
                object : TaskiCheckBox.OnCheckedChangeListener {
                    override fun onCheckedChanged(checkBox: TaskiCheckBox, isChecked: Boolean) {
                        val updatedTask = task.copy(isCompleted = isChecked)
                        listener.onTaskUpdate(updatedTask)
                        updateTextStrikeThrough(isChecked)
                    }
                }
        }

        private fun updateTextStrikeThrough(isChecked: Boolean) {
            binding.tvDescription.paintFlags = if (isChecked) {
                binding.tvDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        fun stopAnimationAndHideButtons() {
            binding.buttonContainer.clearAnimation()
            binding.buttonContainer.visibility = View.GONE
        }
    }

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        currentAnimatedPosition = null
        notifyDataSetChanged()
    }
}