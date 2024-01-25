package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String idUser;

    private ListView listViewTareas;
    private List<String> listaTareas = new ArrayList<>();
    private List<String> listaIDTareas = new ArrayList<>();
    private ArrayAdapter<String> mAdapterTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        idUser = mAuth.getCurrentUser().getUid();

        listViewTareas = findViewById(R.id.listTareas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mas) {
            // activar el cuadro de diálogo para añadir tarea

            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Nueva tarea")
                    .setMessage("¿Qué quieres hacer a continuación")
                    .setView(taskEditText)
                    .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Añadir tarea a la base de datos y al listView
                            String tarea = taskEditText.getText().toString();

                            Map<String, Object> miTarea = new HashMap<>();
                            miTarea.put("nombreTarea", tarea);
                            miTarea.put("idUsuario", idUser);

                            // Add a new document with a generated ID
                            db.collection("Tareas")
                                .add(miTarea)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(MainActivity.this, "Tarea añadida", Toast.LENGTH_SHORT). show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Fallo al crear la tarea", Toast.LENGTH_SHORT). show();
                                    }
                                });
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .create();
            dialog.show();
            return true;

        } else if (item.getItemId() == R.id.logout) {
            // cierre de sesión de Firebase
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}