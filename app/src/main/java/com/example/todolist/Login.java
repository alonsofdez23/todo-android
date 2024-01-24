package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button botonLogin;
    TextView botonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

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
                Toast.makeText(Login.this, "Usuario registrado", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}