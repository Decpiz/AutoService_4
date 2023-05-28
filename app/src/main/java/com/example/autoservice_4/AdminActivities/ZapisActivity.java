package com.example.autoservice_4.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.autoservice_4.Model.Appointment;
import com.example.autoservice_4.Model.Uslugi;
import com.example.autoservice_4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ZapisActivity extends AppCompatActivity {
    private Toolbar tobAdd, tobUslugi;
    private ListView lvAppointment;
    private List<String> listData;
    private List<Appointment> listTemp;
    private ArrayAdapter<String> adapter;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapis);

        Init();
        DownPanel();
        GetDataFromDB();
    }

    private void GetDataFromDB() {
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (listData.size() > 0) listData.clear();
                if (listTemp.size() > 0) listTemp.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Appointment appointment = ds.getValue(Appointment.class);
                    assert appointment != null;
                    listData.add(appointment.getNameUser() + " " + appointment.getSecNameUser() + " " + appointment.getPatronomicUser() +
                            "\n" + appointment.getUslugaName() + "                                                 " + appointment.getNumberUser() + "\n");
                    listTemp.add(appointment);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Init() {
        tobAdd = (Toolbar) findViewById(R.id.tobAdd);
        tobUslugi = (Toolbar) findViewById(R.id.tobUslugi);

        listData = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);

        lvAppointment = (ListView) findViewById(R.id.lvHistoryAppointment);
        lvAppointment.setAdapter(adapter);

        mDataBase = FirebaseDatabase.getInstance().getReference("Appointment");
    }

    private void DownPanel() {
        tobUslugi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goZapis = new Intent(ZapisActivity.this, UslugiAdminActivity.class);
                startActivity(goZapis);
            }
        });


        tobAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goAdd = new Intent(ZapisActivity.this, AddUslugaActivity.class);
                startActivity(goAdd);
            }
        });
    }
}
