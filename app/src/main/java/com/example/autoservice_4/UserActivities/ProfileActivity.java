package com.example.autoservice_4.UserActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.autoservice_4.Const;
import com.example.autoservice_4.MainActivities.MainActivity;
import com.example.autoservice_4.Model.Appointment;
import com.example.autoservice_4.Model.Users;
import com.example.autoservice_4.Prevalent.Prevalent;
import com.example.autoservice_4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private List<Users> listUsers;
    private List<Appointment> listAppointment;
    private AlertDialog.Builder confirmOut;
    private DatabaseReference uDataBase, aDataBase;
    private FirebaseAuth mAuth;
    private String userEmail;

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
        uDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(listPut != null) listPut.clear();
                if(listUsers != null) listUsers.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Users user = ds.getValue(Users.class);
                    assert user != null;
                    listUsers.add(user);
                }

                aDataBase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if(listAppointment != null) listAppointment.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            Appointment appointment = ds.getValue(Appointment.class);
                            assert appointment != null;
                            listAppointment.add(appointment);
                        }


                        for(Users u : listUsers)
                        {
                            if (userEmail.equals(u.getEmail()))
                            {
                                for(Appointment a : listAppointment)
                                {
                                    if(a.getNumberUser().equals(u.getNumber()))
                                    {
                                        listPut.add(a.getUslugaName() + " ");
                                    }
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        uDataBase = FirebaseDatabase.getInstance().getReference("Users");
        aDataBase = FirebaseDatabase.getInstance().getReference(Const.APPOINTMENT_KEY);
        mAuth = FirebaseAuth.getInstance();
        userEmail = mAuth.getCurrentUser().getEmail();

        listUsers = new ArrayList<>();
        listAppointment = new ArrayList<>();
    }

    private void DownPanel()
    {
        tobUslugi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goUslugi = new Intent(ProfileActivity.this, UslugiActivity.class);
                startActivity(goUslugi);
            }
        });


        tobSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSettings = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(goSettings);
            }
        });
    }
}