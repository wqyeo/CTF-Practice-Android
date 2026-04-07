package com.example.practiceCTF;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComposeUiBridge.setHomeContent(
                this,
                () -> {
                    String url = getString(R.string.https_gitlab_com_wqyeo_ctf_practice_android);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                },
                () -> startActivity(new Intent(HomeActivity.this, LoginActivity.class))
        );
    }
}