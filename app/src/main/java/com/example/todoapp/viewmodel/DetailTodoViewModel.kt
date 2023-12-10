package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import buildDb
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val todoLD = MutableLiveData<Todo>()

    fun fetch(uuid: Int) {
        // Launch a coroutine in the background
        CoroutineScope(Dispatchers.Default).launch {
            // Perform background work in the IO dispatcher
            val todo = withContext(Dispatchers.IO) {
                val db = buildDb(getApplication())
                db.todoDao().selectTodo(uuid)
            }

            // Switch to the main thread to update LiveData
            withContext(Dispatchers.Main) {
                todoLD.value = todo?.copy(isDone=0)
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun addTodo(todo: Todo){
        launch {
            val db = buildDb(getApplication())
            val todoWithDefaultIsDone = todo.copy(isDone = 0)
            db.todoDao().insertAll(todo)
        }
    }

    fun update(todo: Todo){
        launch{
            val db = buildDb(getApplication())
            db.todoDao().updateTodo(todo)
        }
    }

    fun update(title:String, notes:String, priority:Int, uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().update(title, notes, priority, uuid)
        }
    }

    fun updateTodoStatus(id: Int, isDone: Int) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().updateTodoStatus(id, isDone)
        }
    }


}