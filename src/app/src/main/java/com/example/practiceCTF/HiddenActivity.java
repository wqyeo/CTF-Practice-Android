package com.example.practiceCTF;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that only be accessed via injects (frida).
 */
public class HiddenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComposeUiBridge.setHiddenContent(this);
    }
}

