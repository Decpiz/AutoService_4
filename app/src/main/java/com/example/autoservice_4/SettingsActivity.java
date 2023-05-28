package com.example.autoservice_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {
    private Toolbar tobUslugi, tobProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Init();
        DownPanel();
    }

    private void Init()
    {
        tobProfile = (Toolbar) findViewById(R.id.tobProfile);
        tobUslugi = (Toolbar) findViewById(R.id.tobUslugi);
    }

    private void DownPanel()
    {
        tobUslugi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goUslugi = new Intent(SettingsActivity.this,UslugiActivity.class);
                startActivity(goUslugi);
            }
        });


        tobProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goProfile = new Intent(SettingsActivity.this,ProfileActivity.class);
                startActivity(goProfile);
            }
        });
    }
}