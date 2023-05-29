package com.example.autoservice_4.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.autoservice_4.Const;
import com.example.autoservice_4.Model.Uslugi;
import com.example.autoservice_4.R;
import com.example.autoservice_4.UserActivities.ProfileActivity;
import com.example.autoservice_4.UserActivities.SettingsActivity;
import com.example.autoservice_4.UserActivities.ShowDetalisUslugaActivity;
import com.example.autoservice_4.UserActivities.UslugiActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UslugiAdminActivity extends AppCompatActivity {
    private Toolbar tobAdd, tobZapis;

    private DatabaseReference mDataBase;

    private ListView lvUslugiList;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<Uslugi> listTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uslugi_admin);

        Init();
        DownPanel();
        GetDataFromDB();

    }

    private void GetDataFromDB()
    {
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listData.size() > 0)listData.clear();
                if(listTemp.size() > 0)listTemp.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Uslugi usluga = ds.getValue(Uslugi.class);
                    assert usluga != null;
                    listData.add(usluga.title);
                    listTemp.add(usluga);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Init()
    {
        tobAdd = (Toolbar) findViewById(R.id.tobAdd);
        tobZapis = (Toolbar) findViewById(R.id.tobZapis);

        lvUslugiList = (ListView) findViewById(R.id.uslugi_lvUslugiList);

        listTemp = new ArrayList<>();
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);


        lvUslugiList.setAdapter(adapter);

        mDataBase = FirebaseDatabase.getInstance().getReference(Const.USLUGI_KEY);
    }

    private void DownPanel()
    {
        tobZapis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goZapis = new Intent(UslugiAdminActivity.this, ZapisActivity.class);
                startActivity(goZapis);
            }
        });


        tobAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goAdd = new Intent(UslugiAdminActivity.this, AddUslugaActivity.class);
                startActivity(goAdd);
            }
        });
    }
}