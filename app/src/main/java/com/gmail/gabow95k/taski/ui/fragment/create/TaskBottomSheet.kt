package com.gmail.gabow95k.taski.ui.fragment.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.gmail.gabow95k.taski.R
import com.gmail.gabow95k.taski.data.Task
import com.gmail.gabow95k.taski.databinding.BottomSheetLayoutBinding
import com.gmail.gabow95k.taski.ui.fragment.dashboar.DashboardFragment
import com.gmail.gabow95k.taski.viewmodel.TaskiViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TaskiViewModel

    private var taskToEdit: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[TaskiViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskToEdit = arguments?.getParcelable("task")

        taskToEdit?.let {
            binding.tvTitle.setText(it.name)
            binding.tvNote.setText(it.description)
            binding.btnCreate.text = getString(R.string.update_task)
        }

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.taskInsertResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                dismiss()
                viewModel.resetTaskResults()
            }
        }

        viewModel.taskUpdateResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                taskToEdit?.let {
                    stopAnimationAndHideButtons(it)
                }
                dismiss()
                viewModel.resetTaskResults()
            }
        }
    }

    private fun setupListeners() {
        binding.btnCreate.setOnClickListener {
            if (taskToEdit == null) {
                createTask()
            } else {
                updateTask()
            }
        }
    }

    private fun createTask() {
        val taskName = binding.tvTitle.text.toString()
        val taskDescription = binding.tvNote.text.toString()

        if (taskName.isNotEmpty()) {
            viewModel.insert(Task(name = taskName, description = taskDescription))
        } else {
            Toast.makeText(context,
                getString(R.string.you_cannot_create_empty_records_prompt), Toast.LENGTH_SHORT).show()        }
    }

    private fun updateTask() {
        val taskName = binding.tvTitle.text.toString()
        val taskDescription = binding.tvNote.text.toString()

        if (taskName.isNotEmpty()) {
            taskToEdit?.let {
                val updatedTask = it.copy(
                    name = taskName,
                    description = taskDescription,
                    lastUpdateDate = Task.getCurrentDateTime()
                )
                viewModel.update(updatedTask)
            }
        } else {
            Toast.makeText(context,
                getString(R.string.you_cannot_create_empty_records_prompt), Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopAnimationAndHideButtons(updatedTask: Task) {
        val fragment = parentFragment as? DashboardFragment
        fragment?.stopAnimationForTask(updatedTask)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogThemeNoFloating
    }

    companion object {
        const val TAG = "TaskBottomSheet"

        fun newInstance(task: Task? = null): TaskBottomSheet {
            val args = Bundle()
            task?.let {
                args.putParcelable("task", it)
            }
            val fragment = TaskBottomSheet()
            fragment.arguments = args
            return fragment
        }
    }
}