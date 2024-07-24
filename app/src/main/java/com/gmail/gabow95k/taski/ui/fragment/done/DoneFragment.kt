package com.gmail.gabow95k.taski.ui.fragment.done

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.gabow95k.taski.base.BaseFragment
import com.gmail.gabow95k.taski.data.Task
import com.gmail.gabow95k.taski.databinding.FragmentDoneBinding
import com.gmail.gabow95k.taski.ui.adapter.DoneAdapter
import com.gmail.gabow95k.taski.ui.adapter.ToDoAdapter
import com.gmail.gabow95k.taski.ui.listener.OnBackPressedListener
import com.gmail.gabow95k.taski.viewmodel.TaskiViewModel

class DoneFragment : BaseFragment<FragmentDoneBinding>(), OnBackPressedListener,
    DoneAdapter.DoneIn {

    private lateinit var viewModel: TaskiViewModel
    private lateinit var doneAdapter: DoneAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[TaskiViewModel::class.java]
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDoneBinding {
        return FragmentDoneBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureUI()
        setupObservers()
        setupListeners()
    }

    private fun configureUI() {

        doneAdapter = DoneAdapter(emptyList(), this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = doneAdapter
        }
    }

    private fun setupObservers() {
        viewModel.allTasksDone.observe(viewLifecycleOwner) { tasks ->
            if (tasks.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyContent.visibility = View.VISIBLE
                binding.btnDeleteAll.visibility = View.INVISIBLE
            } else {
                binding.emptyContent.visibility = View.GONE
                binding.btnDeleteAll.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                doneAdapter.updateTasks(tasks)
            }
        }
    }

    private fun setupListeners() {
        binding.btnDeleteAll.setOnClickListener {
            viewModel.deleteAllCompletedTasks()
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

    companion object {
        @JvmStatic
        fun newInstance() =
            DoneFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}