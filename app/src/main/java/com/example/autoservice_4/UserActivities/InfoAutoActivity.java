package com.example.autoservice_4.UserActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.autoservice_4.Const;
import com.example.autoservice_4.Model.Auto;
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

public class InfoAutoActivity extends AppCompatActivity {
    private ImageView btnBack, btnEdit;
    private EditText etMarka, etModel, etYear;
    private Button btnSave;
    private String marka, model, year, userEmail;
    DatabaseReference mDataBase;
    FirebaseAuth mAuth;
    private List<Auto> listAutos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_auto);

        Init();
        PutEditTexts();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goProfile = new Intent(InfoAutoActivity.this, ProfileActivity.class);
                startActivity(goProfile);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoElements(1);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Auto auto = new Auto();
                        auto.setMarka(etMarka.getText().toString());
                        auto.setModelAuto(etModel.getText().toString());
                        auto.setYear(etYear.getText().toString());
                        auto.setEmail(userEmail);

                        etMarka.setText(auto.getMarka());
                        etModel.setText(auto.getModelAuto());
                        etYear.setText(auto.getYear());

                        if (!TextUtils.isEmpty(auto.getMarka()) && !TextUtils.isEmpty(auto.getModelAuto()) && !TextUtils.isEmpty(auto.getYear()) && !TextUtils.isEmpty(auto.getEmail()))
                            mDataBase.push().setValue(auto);

                        AutoElements(0);
                    }
                });
            }
        });
    }

    private void Init() {
        btnBack = (ImageView) findViewById(R.id.infoauto_btnBack);
        btnEdit = (ImageView) findViewById(R.id.infoauto_btnEditAuto);

        etMarka = (EditText) findViewById(R.id.infoauto_etMarka);
        etModel = (EditText) findViewById(R.id.infoauto_etModel);
        etYear = (EditText) findViewById(R.id.infoauto_etYear);

        btnSave = (Button) findViewById(R.id.infoauto_btnSave);

        mDataBase = FirebaseDatabase.getInstance().getReference("UserAuto");
        mAuth = FirebaseAuth.getInstance();
        userEmail = mAuth.getCurrentUser().getEmail();

        listAutos = new ArrayList<>();
    }

    private void AutoElements(int i) {
        switch (i) {
            case (0):
                btnEdit.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.GONE);
                etMarka.setEnabled(false);
                etModel.setEnabled(false);
                etYear.setEnabled(false);
                break;

            case (1):
                btnEdit.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                etMarka.setEnabled(true);
                etModel.setEnabled(true);
                etYear.setEnabled(true);
                break;
        }
    }

    private void PutEditTexts()
    {
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(listAutos != null) listAutos.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Auto auto = ds.getValue(Auto.class);
                    assert auto != null;
                    listAutos.add(auto);
                }
                for (Auto a : listAutos)
                {
                    if (userEmail.equals(a.getEmail()))
                    {
                        etMarka.setText(a.getMarka());
                        etModel.setText(a.getModelAuto());
                        etYear.setText(a.getYear());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}







