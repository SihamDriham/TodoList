package ma.ensa.projet.View

// TaskAdapter.kt
import android.app.AlertDialog
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ma.ensa.projet.Model.Task
import ma.ensa.projet.R


class TaskAdapter(
    private var tasks: List<Task>,
    private val onCompleteClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.task_title)
        val checkBox: CheckBox = view.findViewById(R.id.task_checkbox)
        val deleteButton: ImageButton = view.findViewById(R.id.task_delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.checkBox.isChecked = task.isCompleted

        // Afficher la description dans une boîte de dialogue lors du clic sur le titre
        holder.title.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Description")
                .setMessage(task.description)
                .setPositiveButton("Fermer") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }

        // Appliquer le barré si la tâche est complétée
        holder.title.paintFlags = if (task.isCompleted) {
            holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG // Barrer le titre
        } else {
            holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv() // Enlever le barré du titre
        }

        holder.checkBox.setOnClickListener { onCompleteClick(task) }

        // Boîte de dialogue de confirmation de suppression
        holder.deleteButton.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Confirmer la suppression")
                .setMessage("Voulez-vous vraiment supprimer cette tâche ?")
                .setPositiveButton("Oui") { dialog, _ ->
                    onDeleteClick(task)
                    dialog.dismiss()
                }
                .setNegativeButton("Non") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    override fun getItemCount(): Int = tasks.size
}

