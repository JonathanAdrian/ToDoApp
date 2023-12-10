package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel

class CreateTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(owner = this).get(DetailTodoViewModel::class.java)

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val editTitle = view.findViewById<EditText>(R.id.editTitle)
            val editNotes = view.findViewById<EditText>(R.id.editNotes)
            val radioGroupPriority = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            val radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)

            val todo = Todo(editTitle.text.toString(), editNotes.text.toString(), radio.tag.toString().toInt())
            viewModel.addTodo(todo)
            Toast.makeText(it.context, "Todo created", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }

}