package cr.ac.una.firebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var personasRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar Firebase
        FirebaseApp.initializeApp(this)

        // Obtener referencia a la base de datos "personas"
        val database = FirebaseDatabase.getInstance()
        personasRef = database.getReference("persona")

        // Agregar una persona a la base de datos
        val persona = Persona("Allam Chaves")
        val personaId = personasRef.push().key
        personasRef.child(personaId!!).setValue(persona)
    }
}