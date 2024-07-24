package com.gmail.gabow95k.taski.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import com.gmail.gabow95k.taski.databinding.TaskiViewSearchBinding

class TaskiSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: TaskiViewSearchBinding =
        TaskiViewSearchBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.searchEditText.addTextChangedListener { text ->
            binding.clearIcon.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        binding.clearIcon.setOnClickListener {
            binding.searchEditText.text.clear()
        }
    }

    var searchText: String
        get() = binding.searchEditText.text.toString()
        set(value) {
            binding.searchEditText.setText(value)
        }

    fun setSearchHint(hint: String) {
        binding.searchEditText.hint = hint
    }

    fun setOnSearchTextChangedListener(listener: (String) -> Unit) {
        binding.searchEditText.addTextChangedListener { text ->
            listener(text.toString())
        }
    }
}