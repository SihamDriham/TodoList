package ma.ensa.projet.Model

data class Task(

    val id: Int,
    var title: String,
    var description: String,
    var isCompleted: Boolean = false

)
