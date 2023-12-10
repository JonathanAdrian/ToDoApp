package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TodoItemMenuBinding
import com.example.todoapp.model.Todo

class TodoListAdapter(val todos:ArrayList<Todo>,
                        val adapterOnClick: (Todo) -> Unit)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(), TodoCheckedChangeListener, TodoEditClick {
        class TodoViewHolder(var view:TodoItemMenuBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = TodoItemMenuBinding.inflate(inflater,parent,false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.view.todo = todos[position]
        holder.view.listener = this
        holder.view.editListener = this

        // Set up the CheckBox with a listener
        holder.view.checkTask.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateTodoStatus(todos[position].uuid, if (isChecked) 1 else 0)
        }

        // Set up the ImageView with a click listener
        holder.view.imgEditTask.setOnClickListener {
            val action = TodoListFragmentDirections.actionEditTodoFragment(todos[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
//        val checkTask = holder.v.findViewById<CheckBox>(R.id.checkTask)
//        checkTask.text = todos[position].title
//        checkTask.setOnCheckedChangeListener{ compoundButton, isChecked ->
//            if(isChecked == true) {
//                adapterOnClick(todos[position])
//            }
//        }
//
//        val imgEditTask = holder.v.findViewById<ImageView>(R.id.imgEditTask)
//        imgEditTask.setOnClickListener {
//            val action =
//                TodoListFragmentDirections.actionEditTodoFragment(todos[position].uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun updateTodoList(newTodos:List<Todo>){
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }

    override fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if (isChecked) {
            adapterOnClick(obj)
        }
    }

    override fun onTodoEditClick(v: View) {
        val action = TodoListFragmentDirections.actionEditTodoFragment(v.tag.toString().toInt())
        Navigation.findNavController(v).navigate(action)
    }
}