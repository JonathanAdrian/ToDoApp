package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentEditToDoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel
import com.google.android.material.snackbar.Snackbar

class EditTodoFragment : Fragment(), RadioClick, TodoEditClick {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var binding:FragmentEditToDoBinding
    private var todo: Todo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioListener = this
        binding.saveListener = this

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)

        val editTitle = view.findViewById<EditText>(R.id.editTitle)
        val editNotes = view.findViewById<EditText>(R.id.editNotes)
        val txtTitleCaption = view.findViewById<TextView>(R.id.txtTitleCaption)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)

//        btnAdd.setOnClickListener {
//            val radioGroupPriority = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
//            val radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
//
//            todo?.notes = editNotes.text.toString()
//            todo?.title = editTitle.text.toString()
//            todo?.priority = radio.tag.toString().toInt()
//            todo?.uuid = uuid
//            viewModel.update(todo!!)
//
//            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(it).popBackStack()
//        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            binding.todo = it
            todo=it
//            val editTitle = view?.findViewById<EditText>(R.id.editTitle)
//            val editNotes = view?.findViewById<EditText>(R.id.editNotes)
//            val radioHigh = view?.findViewById<RadioButton>(R.id.radioHigh)
//            val radioMedium = view?.findViewById<RadioButton>(R.id.radioMedium)
//            val radioLow = view?.findViewById<RadioButton>(R.id.radioLow)
//
//            editTitle?.setText(it.title)
//            editNotes?.setText(it.notes)
//
//            when (it.priority) {
//                1 -> radioLow?.isChecked = true
//                2 -> radioMedium?.isChecked = true
//                else -> radioHigh?.isChecked = true
//            }
        })
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditToDoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onRadioClick(v: View) {
        var priority = v.tag.toString().toInt()
        binding.todo!!.priority = priority
    }

    override fun onTodoEditClick(v: View) {
        viewModel.update(binding.todo!!)
        Snackbar.make(context!!,v,"Sukes simpam", Snackbar.LENGTH_SHORT).show()

    }
}