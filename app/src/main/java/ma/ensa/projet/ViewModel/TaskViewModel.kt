package ma.ensa.projet.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ma.ensa.projet.Model.Task
import ma.ensa.projet.Repository.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TaskRepository(application)
    val allTasks: LiveData<List<Task>> = repository.getAllTasks()

    fun addTask(task: Task) {
        repository.addTask(task)
    }

    fun markTaskCompleted(task: Task) {
        repository.markTaskCompleted(task)
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }
}
