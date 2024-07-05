package com.example.tarea_login_restricciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (validateEmail(email) && validatePassword(password)) {
                    // Proceder con el login
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // Iniciar PostLogin activity
                    Intent intent = new Intent(MainActivity.this, PostLogin.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            emailEditText.setError("Email es requerido");
            return false;
        }
        if (!email.contains("@")) {
            emailEditText.setError("Formato de email inválido");
            return false;
        }
        if (!email.endsWith(".cl") && !email.endsWith(".com")) {
            emailEditText.setError("Debe tener '@' y terminar en '.cl' o '.com'");
            return false;
        }
        if (email.indexOf('@') == 0) {
            emailEditText.setError("Email no debe empezar con @");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            passwordEditText.setError("Es necesaria la contraseña");
            return false;
        }

        char[] forbiddenCharacters = {'*', '+', '-', '/', '%', '(',')','$','#','!','¡','<','>','?','¿'};
        for (char c : password.toCharArray()) {
            for (char forbiddenChar : forbiddenCharacters) {
                if (c == forbiddenChar) {
                    passwordEditText.setError("La contraseña no puede contener los símbolos como estos '*', '+', '-', '/', '%'");
                    return false;
                }
            }
            if (!Character.isLetterOrDigit(c)) {
                passwordEditText.setError("La contraseña debe ser alfanumérica y no puede contener caracteres especiales");
                return false;
            }
        }
        return true;
    }
}
