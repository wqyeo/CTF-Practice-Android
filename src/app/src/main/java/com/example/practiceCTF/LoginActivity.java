package com.example.practiceCTF;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

@SuppressWarnings({"unused", "FieldCanBeLocal", "FieldMayBeFinal"})
public class LoginActivity extends AppCompatActivity {
    private PasswordFetcher passwordFetcher = new PasswordFetcher(new Random().nextInt());
    private UsernameFetcher usernameFetcher = new UsernameFetcher();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComposeUiBridge.setLoginContent(
                this,
                this::authenticate,
                () -> startActivity(new Intent(LoginActivity.this, WinActivity.class)),
                this::finish
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean authenticate(String username, String password) {
        String decryptedUsername = usernameFetcher.getUsername();

        if (decryptedUsername == null) {
            Toast.makeText(LoginActivity.this, "Failed to decrypt actual username!\nEnsure your emulator is running on Android 8.0 for this CTF!!!", Toast.LENGTH_LONG).show();
            return false;
        }

        Log.d("USERNAME_COMPARISON", "Comparing " + username + " against " + decryptedUsername);

        String actualPassword = passwordFetcher.getPassword();
        return decryptedUsername.equals(username.trim()) && actualPassword.equals(password.trim());
    }
}
