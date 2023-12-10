package com.example.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.example.todoapp.model.Todo

interface RadioClick {
    fun onRadioClick(v:View)
}

interface TodoCheckedChangeListener {
    fun onCheckedChange(cb:CompoundButton, isChecked:Boolean, obj: Todo)
}

interface TodoEditClick {
    fun onTodoEditClick(v:View)
}