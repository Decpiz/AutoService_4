package com.example.autoservice_4.UserActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autoservice_4.Const;
import com.example.autoservice_4.Model.Appointment;
import com.example.autoservice_4.Model.Users;
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

public class ShowDetalisUslugaActivity extends AppCompatActivity {
    private TextView tvPrice, tvTitle, tvTime, tvFullPrice;
    private ImageView btnBack;
    private Button btnAppointment;
    private DatabaseReference usersDataBase, appointmentDataBase;
    private List<Users> listUsers;
    private FirebaseAuth mAuth;
    private String userEmail, name, secName, patronomic, number, uslugaName;
    private int result;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detalis_usluga);

        Init();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goUslugi = new Intent(ShowDetalisUslugaActivity.this, UslugiActivity.class);
                startActivity(goUslugi);
            }
        });

        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppointmentUser();
            }
        });

        if(i != null)
        {
            tvTitle.setText(i.getStringExtra(Const.INTENT_EXTRA1));
            tvPrice.setText(i.getStringExtra(Const.INTENT_EXTRA2));
            tvTime.setText(i.getStringExtra(Const.INTENT_EXTRA3));
            tvFullPrice.setText(i.getStringExtra(Const.INTENT_EXTRA4));
        }
    }

    private void AppointmentUser()
    {
        assert i != null;
        uslugaName = i.getStringExtra(Const.INTENT_EXTRA1);

        usersDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(listUsers != null) listUsers.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Users user = ds.getValue(Users.class);
                    assert user != null;
                    listUsers.add(user);
                }

                for(Users u : listUsers)
                {
                    if(userEmail.equals(u.getEmail()))
                    {
                        name = u.getName();
                        secName = u.getSecName();
                        patronomic = u.getPatronomic();
                        number = u.getNumber();

                        Toast.makeText(ShowDetalisUslugaActivity.this, "Вы успешно записаны на услугу: "+ uslugaName +
                                "\nОжидайте, вам перезвонят в течение часа", Toast.LENGTH_SHORT).show();

                        Appointment appointment = new Appointment(uslugaName, name, secName, patronomic, number);
                        appointmentDataBase.push().setValue(appointment);
                        result = 0;
                    }
                    else
                    {
                        result = 1;
                    }
                }
                if (result == 1)
                {
                    Toast.makeText(ShowDetalisUslugaActivity.this, "Не удалось записаться на услугу!" +
                            "\nЗаполните данные в профиле!", Toast.LENGTH_SHORT).show();
                    Intent goProfile = new Intent(ShowDetalisUslugaActivity.this, ProfileActivity.class);
                    startActivity(goProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Init()
    {
        tvTitle = (TextView) findViewById(R.id.detal_tvTitle);
        tvPrice = (TextView) findViewById(R.id.detal_tvPrice);
        tvTime = (TextView) findViewById(R.id.detal_tvTime);
        tvFullPrice = (TextView) findViewById(R.id.detal_tvFullPrice);

        btnBack = (ImageView) findViewById(R.id.detal_btnBack);
        btnAppointment = (Button) findViewById(R.id.detal_btnRegUsluga);

        appointmentDataBase = FirebaseDatabase.getInstance().getReference(Const.APPOINTMENT_KEY);
        usersDataBase = FirebaseDatabase.getInstance().getReference("Users");

        mAuth = FirebaseAuth.getInstance();
        listUsers = new ArrayList<>();
        userEmail = mAuth.getCurrentUser().getEmail();

        result = 0;

        i = getIntent();
    }
}