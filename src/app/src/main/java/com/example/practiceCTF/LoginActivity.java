package com.example.practiceCTF;

import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;
    private TextView aboutTextView;

    private String ENCRYPTED_USERNAME = "oaryGaWV4Fe8YQzd9w6jKJxK5WA=";
    private String USERNAME_KEY = "Pancakes";
    private PasswordFetcher passwordFetcher = new PasswordFetcher(new Random().nextInt());

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        aboutTextView = findViewById(R.id.textViewAbout);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (authenticate(username, password)) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        aboutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, InfoActivity.class));
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean authenticate(String username, String password) {
        String decryptedUsername = AESEncryption.decrypt(ENCRYPTED_USERNAME, USERNAME_KEY);
        if (decryptedUsername == null) {
            Toast.makeText(LoginActivity.this, "Failed to decrypt username!\nEnsure your emulator is running on Android 8.0 for this CTF!!!", Toast.LENGTH_LONG).show();
            return false;
        }

        Log.d("USERNAME_COMPARISON", "Comparing " + username + " against " + decryptedUsername);

        String actualPassword = passwordFetcher.getPassword();
        return decryptedUsername.equals(username.trim()) && actualPassword.equals(password.trim());
    }
}
