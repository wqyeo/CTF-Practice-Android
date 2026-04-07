package com.example.practiceCTF;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComposeUiBridge.setHomeContent(
                this,
                () -> startActivity(new Intent(HomeActivity.this, LoginActivity.class))
        );
    }
}