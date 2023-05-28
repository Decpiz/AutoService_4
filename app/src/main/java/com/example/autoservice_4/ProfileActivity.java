package com.example.autoservice_4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.autoservice_4.Prevalent.Prevalent;
import com.example.autoservice_4.ProfileActivities.InfoAutoActivity;
import com.example.autoservice_4.ProfileActivities.InfoHumanActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity {
    private ImageView btnOut;
    private Toolbar tobUslugi, tobSettings;
    private Button btnInfoHuman, btnInfoAuto, btnEditPassword;
    private ListView lvHistoryList;
    private ArrayAdapter<String> adapter;
    private List<String> listPut;

    private AlertDialog.Builder confirmOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Init();
        PutHistoryList();
        DownPanel();

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutAccount();
            }
        });

        btnInfoHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInfoHuman = new Intent(ProfileActivity.this, InfoHumanActivity.class);
                startActivity(goInfoHuman);
            }
        });

        btnInfoAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInfoAuto = new Intent(ProfileActivity.this, InfoAutoActivity.class);
                startActivity(goInfoAuto);
            }
        });
    }

    private void OutAccount()
    {
        confirmOut.setMessage("Вы действительно хотите выйти из аккаунта?");
        confirmOut.setTitle("Подтверждение");
        confirmOut.setCancelable(true);
        confirmOut.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Paper.book().write(Prevalent.UserEmailKey, "");
                Paper.book().write(Prevalent.UserPasswordKey, "");

                Toast.makeText(ProfileActivity.this, "Вы вышли из аккаунта!", Toast.LENGTH_SHORT).show();
                Intent goMain = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(goMain);
            }
        });
        confirmOut.setNegativeButton("Нет", null);
        confirmOut.create().show();
    }

    private void PutHistoryList()
    {
        for(int i = 1; i<20; i++)
        {
            String item = "" + i;
            listPut.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    private void Init()
    {
        confirmOut = new AlertDialog.Builder(this);

        btnOut = (ImageView) findViewById(R.id.profile_ivOutAccount);

        tobSettings = (Toolbar) findViewById(R.id.profile_tobSettings);
        tobUslugi = (Toolbar) findViewById(R.id.profile_tobUslugi);

        btnInfoHuman = (Button) findViewById(R.id.profile_btnFullInfoHuman);
        btnInfoAuto = (Button) findViewById(R.id.profile_btnFullInfoAuto);
        btnEditPassword = (Button) findViewById(R.id.profile_btnEditPassword);

        lvHistoryList = (ListView) findViewById(R.id.profile_lvHistoryList);

        listPut = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listPut);
        lvHistoryList.setAdapter(adapter);
    }

    private void DownPanel()
    {
        tobUslugi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goUslugi = new Intent(ProfileActivity.this,UslugiActivity.class);
                startActivity(goUslugi);
            }
        });


        tobSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSettings = new Intent(ProfileActivity.this,SettingsActivity.class);
                startActivity(goSettings);
            }
        });
    }
}