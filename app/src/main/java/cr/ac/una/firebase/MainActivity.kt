package cr.ac.una.firebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var personasRef: DatabaseReference
    private lateinit var personaList: MutableList<Persona>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar Firebase
        FirebaseApp.initializeApp(this)
        personaList = mutableListOf()

        // Obtener referencia a la base de datos "personas"
        val database = FirebaseDatabase.getInstance()
        personasRef = database.getReference("persona")
        listarPersonas()


    }
    private fun listarPersonas() {
        personasRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                personaList.clear()
                for (dataSnapshot in snapshot.children) {
                    val persona = dataSnapshot.getValue(Persona::class.java)
                    persona?.let {
                        personaList.add(it)
                        System.out.println(it.nombre)
                    }
                }
                // Actualizar la interfaz de usuario con la lista de personas
                // Por ejemplo, puedes usar un RecyclerView para mostrar los datos
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error
            }
        })
    }

    private fun crearPersona(){
        // Agregar una persona a la base de datos
        val persona = Persona("Allam Chaves")
        val personaId = personasRef.push().key
        personasRef.child(personaId!!).setValue(persona)
    }


    private fun modificarPersona(personaId: String, nombre:String) {
        val persona = Persona(nombre)
        personasRef.child(personaId).setValue(persona)
    }
    private fun deletePersona(personaId: String) {
        val persona = personasRef.child(personaId)
        persona.removeValue()
    }
}