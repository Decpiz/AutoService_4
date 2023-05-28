package com.example.autoservice_4.UserActivities;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UslugiActivity extends AppCompatActivity {

    private Toolbar tobProfile, tobSettings;

    private DatabaseReference mDataBase;
    private ListView lvUslugiList;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<Uslugi> listTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uslugi);

        Init();
        GetDataFromDB();
        DownPanel();


        lvUslugiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uslugi usluga = listTemp.get(i);
                Intent goDetalis = new Intent(UslugiActivity.this, ShowDetalisUslugaActivity.class);
                goDetalis.putExtra(Const.INTENT_EXTRA1, usluga.title);
                goDetalis.putExtra(Const.INTENT_EXTRA2, usluga.price);
                goDetalis.putExtra(Const.INTENT_EXTRA3, usluga.time);
                goDetalis.putExtra(Const.INTENT_EXTRA4, usluga.fullPrice);
                startActivity(goDetalis);
            }
        });
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
        tobProfile = (Toolbar) findViewById(R.id.uslugi_tobProfile);
        tobSettings = (Toolbar) findViewById(R.id.uslugi_tobSettings);

        lvUslugiList = (ListView) findViewById(R.id.uslugi_lvUslugiList);

        listTemp = new ArrayList<>();
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);


        lvUslugiList.setAdapter(adapter);

        mDataBase = FirebaseDatabase.getInstance().getReference(Const.USLUGI_KEY);
    }

    private void DownPanel()
    {
        tobProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goProfile = new Intent(UslugiActivity.this, ProfileActivity.class);
                startActivity(goProfile);
            }
        });


        tobSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSettings = new Intent(UslugiActivity.this, SettingsActivity.class);
                startActivity(goSettings);
            }
        });
    }
}