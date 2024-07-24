package com.gmail.gabow95k.taski.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.gmail.gabow95k.taski.R
import com.gmail.gabow95k.taski.databinding.ViewCustomButtonBinding

class TaskiButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: ViewCustomButtonBinding =
        ViewCustomButtonBinding.inflate(LayoutInflater.from(context), this)

    var text: String
        get() = binding.text.text.toString()
        set(value) {
            binding.text.text = value
        }

    var iconResId: Int
        get() = binding.icon.id
        set(value) {
            binding.icon.setImageResource(value)
        }

    init {
        // Read custom attributes, if any
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TaskiButton,
            0, 0
        ).apply {
            try {
                text = getString(R.styleable.TaskiButton_text) ?: "Create task"
                iconResId = getResourceId(R.styleable.TaskiButton_icon, R.drawable.ic_add)
            } finally {
                recycle()
            }
        }
    }
}