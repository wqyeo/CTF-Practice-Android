package com.example.practiceCTF;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HiddenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComposeUiBridge.setHiddenContent(this);
    }
}

