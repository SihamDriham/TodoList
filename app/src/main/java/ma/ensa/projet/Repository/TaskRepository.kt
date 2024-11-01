package ma.ensa.projet.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import ma.ensa.projet.Model.Task

class TaskRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("TaskPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private var taskList = mutableListOf<Task>()
    private val tasksLiveData = MutableLiveData<List<Task>>()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        val json = sharedPreferences.getString("tasks", null)
        if (json != null) {
            val type = object : com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken<List<Task>>() {}.type
            taskList = gson.fromJson(json, type)
        }
        tasksLiveData.value = taskList
    }

    private fun saveTasks() {
        val editor = sharedPreferences.edit()
        editor.putString("tasks", gson.toJson(taskList))
        editor.apply()
        tasksLiveData.value = taskList
    }

    fun getAllTasks(): LiveData<List<Task>> = tasksLiveData

    fun addTask(task: Task) {
        taskList.add(task)
        saveTasks()
    }

    fun markTaskCompleted(task: Task) {
        task.isCompleted = !task.isCompleted
        saveTasks()
    }

    fun deleteTask(task: Task) {
        taskList.remove(task)
        saveTasks()
    }
}
