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

        // Set hints from string resources
        emailEditText.setHint(R.string.email_hint);
        passwordEditText.setHint(R.string.password_hint);
        loginButton.setText(R.string.login_button_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (validateEmail(email) && validatePassword(password)) {
                    // Proceder con el login
                    Toast.makeText(MainActivity.this, R.string.login_success_message, Toast.LENGTH_SHORT).show();
                    // Iniciar PostLogin activity
                    Intent intent = new Intent(MainActivity.this, PostLogin.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            emailEditText.setError(getString(R.string.email_required));
            return false;
        }
        if (!email.contains("@")) {
            emailEditText.setError(getString(R.string.invalid_email_format));
            return false;
        }
        if (!email.endsWith(".cl") && !email.endsWith(".com")) {
            emailEditText.setError(getString(R.string.email_must_contain_at));
            return false;
        }
        if (email.indexOf('@') == 0) {
            emailEditText.setError(getString(R.string.email_cannot_start_with_at));
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            passwordEditText.setError(getString(R.string.password_required));
            return false;
        }

        char[] forbiddenCharacters = {'*', '+', '-', '/', '%', '(', ')', '$', '#', '!', '¡', '<', '>', '?', '¿'};
        for (char c : password.toCharArray()) {
            for (char forbiddenChar : forbiddenCharacters) {
                if (c == forbiddenChar) {
                    passwordEditText.setError(getString(R.string.password_no_special_symbols));
                    return false;
                }
            }
            if (!Character.isLetterOrDigit(c)) {
                passwordEditText.setError(getString(R.string.password_alphanumeric));
                return false;
            }
        }
        return true;
    }
}
