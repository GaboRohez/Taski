package com.gmail.gabow95k.taski.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.gabow95k.taski.base.BaseFragment
import com.gmail.gabow95k.taski.data.Task
import com.gmail.gabow95k.taski.databinding.FragmentSearchBinding
import com.gmail.gabow95k.taski.ui.adapter.ToDoAdapter
import com.gmail.gabow95k.taski.ui.fragment.create.TaskBottomSheet
import com.gmail.gabow95k.taski.ui.listener.OnBackPressedListener
import com.gmail.gabow95k.taski.viewmodel.TaskiViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnBackPressedListener,
    ToDoAdapter.ToDoIn {

    private lateinit var viewModel: TaskiViewModel
    private lateinit var todoAdapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[TaskiViewModel::class.java]
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureUI()
        setupObservers()
        setupListeners()
    }

    private fun configureUI() {

        todoAdapter = ToDoAdapter(emptyList(), this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }

    private fun setupObservers() {
        viewModel.searchResults.observe(viewLifecycleOwner) { tasks ->
            todoAdapter.updateTasks(tasks)

            if (tasks.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyContent.visibility = View.VISIBLE
            } else {
                binding.emptyContent.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun setupListeners() {
        binding.searchView.setOnSearchTextChangedListener { query ->
            viewModel.searchTasks(query)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun onBackPressed(): Boolean {
        var isAnimating = false
        for (i in 0 until binding.recyclerView.childCount) {
            val holder =
                binding.recyclerView.findViewHolderForAdapterPosition(i) as? ToDoAdapter.TaskViewHolder
            holder?.let {
                if (it.binding.buttonContainer.animation != null) {
                    isAnimating = true
                    it.stopAnimationAndHideButtons()
                }
            }
        }
        return isAnimating
    }

    override fun onTaskDelete(task: Task) {
        viewModel.delete(task)
    }

    override fun onTaskEdit(task: Task) {
        TaskBottomSheet.newInstance(task)
            .show(requireActivity().supportFragmentManager, TaskBottomSheet.TAG)
    }

    override fun onTaskUpdate(task: Task) {
        viewModel.update(task)
    }

    fun stopAnimationForTask(task: Task?) {
        task?.let {
            val position = todoAdapter.tasks.indexOf(task)
            if (position != -1) {
                val holder =
                    binding.recyclerView.findViewHolderForAdapterPosition(position) as? ToDoAdapter.TaskViewHolder
                holder?.stopAnimationAndHideButtons()
            }
        }
    }
}