package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button botonLogin;
    TextView botonRegistro;
    private FirebaseAuth mAuth;
    
    EditText emailText, passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        
        emailText = findViewById(R.id.cajaCorreo);
        passText = findViewById(R.id.cajaPass);

        botonLogin = findViewById(R.id.botonLogin);
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // login en Firebase
                try {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });

        botonRegistro = findViewById(R.id.botonRegistro);
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear usuario en Firebase
                
                String email = emailText.getText().toString();
                String password = passText.getText().toString();
                
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in successs, update UI with the signed-in user's information
                                Toast.makeText(Login.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user
                                Toast.makeText(Login.this, "Login incorrecto", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
    }
}