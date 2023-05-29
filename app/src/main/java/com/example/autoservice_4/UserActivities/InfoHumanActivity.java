package com.example.autoservice_4.UserActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.autoservice_4.Const;
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

public class InfoHumanActivity extends AppCompatActivity {
    private ImageView btnBack, btnEdit;
    private Button btnSaveContacts;
    private EditText etName, etSecName, etPatronomic, etNumber, etEmail;
    private String userName, userSecName, userPatronomic, userNumber, userEmail;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private Users user;
    private List<Users> listUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_human);

        Init();
        PutEditTexts();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goProfile = new Intent(InfoHumanActivity.this, ProfileActivity.class);
                startActivity(goProfile);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Elements(1);

                btnSaveContacts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        userName = etName.getText().toString();
                        userSecName = etSecName.getText().toString();
                        userPatronomic = etPatronomic.getText().toString();
                        userNumber = etNumber.getText().toString();

                        etName.setText(userName);
                        etSecName.setText(userSecName);
                        etPatronomic.setText(userPatronomic);
                        etNumber.setText((userNumber));

                        user.setName(userName);
                        user.setSecName(userSecName);
                        user.setPatronomic(userPatronomic);
                        user.setNumber(userNumber);
                        user.setEmail(userEmail);

                        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userSecName) && !TextUtils.isEmpty(userPatronomic) && !TextUtils.isEmpty(userNumber))
                            mDataBase.push().setValue(user);

                        Elements(0);

                    }
                });
            }
        });
    }

    private void PutEditTexts()
    {
        etEmail.setText(userEmail);
        mDataBase.addValueEventListener(new ValueEventListener() {
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
                       etName.setText(u.getName());
                       etSecName.setText(u.getSecName());
                       etPatronomic.setText(u.getPatronomic());
                       etNumber.setText(u.getNumber());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Elements(int x)
    {
        switch (x)
        {
            case(0):
                btnEdit.setVisibility(View.VISIBLE);
                btnSaveContacts.setVisibility(View.GONE);
                etName.setEnabled(false);
                etSecName.setEnabled(false);
                etPatronomic.setEnabled(false);
                etNumber.setEnabled(false);
                etEmail.setEnabled(false);
                break;

            case(1):
                btnEdit.setVisibility(View.GONE);
                btnSaveContacts.setVisibility(View.VISIBLE);
                etName.setEnabled(true);
                etSecName.setEnabled(true);
                etPatronomic.setEnabled(true);
                etNumber.setEnabled(true);
                etEmail.setEnabled(false);
                break;
        }
    }

    private void Init()
    {
        btnBack = (ImageView) findViewById(R.id.infohuman_btnBack);
        btnEdit = (ImageView) findViewById(R.id.infhuman_btnEdit);

        btnSaveContacts = (Button) findViewById(R.id.infohuman_btnSaveContacts);

        etName = (EditText) findViewById(R.id.infohuman_etName);
        etSecName = (EditText) findViewById(R.id.infohuman_etSecName);
        etPatronomic = (EditText) findViewById(R.id.infohuman_etPatronomic);
        etNumber = (EditText) findViewById(R.id.infohuman_etNumber);
        etEmail = (EditText) findViewById(R.id.infohuman_etEmail);

        user = new Users();

        mAuth = FirebaseAuth.getInstance();
        userEmail = mAuth.getCurrentUser().getEmail();
        mDataBase = FirebaseDatabase.getInstance().getReference("Users");

        listUsers = new ArrayList<>();
    }
}