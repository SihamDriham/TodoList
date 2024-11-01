package ma.ensa.projet.View

// MainActivity.kt
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ma.ensa.projet.ViewModel.TaskViewModel
import ma.ensa.projet.Model.Task
import ma.ensa.projet.databinding.ActivityMainBinding

class TaskMain : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TaskAdapter(
            tasks = emptyList(),
            onCompleteClick = { task -> taskViewModel.markTaskCompleted(task) },
            onDeleteClick = { task -> taskViewModel.deleteTask(task) }
        )

        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.taskRecyclerView.adapter = adapter

        // Observe tasks and update adapter
        taskViewModel.allTasks.observe(this) { tasks ->
            adapter.updateTasks(tasks) // Utilisez la méthode publique
        }

        binding.addButton.setOnClickListener {
            val title = binding.taskTitleInput.text.toString()
            val description = binding.taskDescriptionInput.text.toString()
            if (title.isNotEmpty()) {
                val task = Task(id = System.currentTimeMillis().toInt(), title = title, description = description)
                taskViewModel.addTask(task)
                binding.taskTitleInput.text.clear()
                binding.taskDescriptionInput.text.clear()
            }
        }
    }
}
