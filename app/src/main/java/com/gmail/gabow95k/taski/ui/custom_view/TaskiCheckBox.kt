package com.gmail.gabow95k.taski.ui.custom_view

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.gmail.gabow95k.taski.R
import com.gmail.gabow95k.taski.databinding.TaskiCheckboxBinding

class TaskiCheckBox @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: TaskiCheckboxBinding =
        TaskiCheckboxBinding.inflate(LayoutInflater.from(context), this, true)

    var isChecked: Boolean = false
        set(value) {
            field = value
            updateCheckboxImage()
        }

    var text: String?
        get() = binding.tvMessage.text.toString()
        set(value) {
            binding.tvMessage.text = value
        }

    var enable: Boolean = true
        set(value) {
            field = value
        }

    var onCheckedChangeListener: OnCheckedChangeListener? = null

    interface OnCheckedChangeListener {
        fun onCheckedChanged(checkBox: TaskiCheckBox, isChecked: Boolean)
    }

    init {
        updateCheckboxImage()

        binding.ivCheckBox.setOnClickListener {
            if (enable) {
                isChecked = !isChecked
                onCheckedChangeListener?.onCheckedChanged(this, isChecked)
            }
        }

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TaskiCheckBox,
            0, 0
        ).apply {
            try {
                text = getString(R.styleable.TaskiCheckBox_taskiCheckBoxText) ?: ""
                isChecked = getBoolean(R.styleable.TaskiCheckBox_taskiCheckBoxIsChecked, false)
            } finally {
                recycle()
            }
        }
    }

    private fun updateCheckboxImage() {
        binding.ivCheckBox.setImageDrawable(
            if (isChecked) {
                ContextCompat.getDrawable(context, R.drawable.checked_image)
            } else {
                ContextCompat.getDrawable(context, R.drawable.unchecked_image)
            }
        )

        updateTextStrikeThrough()
        updateTextColor()
    }

    private fun updateTextStrikeThrough() {
        binding.tvMessage.paintFlags = if (isChecked) {
            binding.tvMessage.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.tvMessage.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    private fun updateTextColor() {
        val textColor = if (isChecked) {
            ContextCompat.getColor(context, R.color.text_description_color)
        } else {
            ContextCompat.getColor(context, R.color.purple)
        }
        binding.tvMessage.setTextColor(textColor)
    }
}