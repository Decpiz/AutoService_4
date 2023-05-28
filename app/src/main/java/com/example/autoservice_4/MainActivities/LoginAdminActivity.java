package com.example.autoservice_4.MainActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.autoservice_4.AdminActivities.AddUslugaActivity;
import com.example.autoservice_4.AdminActivities.UslugiAdminActivity;
import com.example.autoservice_4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAdminActivity extends AppCompatActivity {
    private EditText etCode;
    private ImageView btnBack, btnGo;
    private DatabaseReference mDataBase;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        Init();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLogin = new Intent(LoginAdminActivity.this, LoginActivity.class);
                startActivity(goLogin);
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String codeInput = etCode.getText().toString();

                mDataBase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            String code = ds.getValue(String.class);
                            assert code != null;
                            if(code.equals(codeInput))
                            {
                                result = 1;
                            }
                            else
                            {
                                result = 0;
                            }
                        }
                        switch (result)
                        {
                            case(1):
                                Intent goUslugi = new Intent(LoginAdminActivity.this, UslugiAdminActivity.class);
                                startActivity(goUslugi);

                                Toast.makeText(LoginAdminActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                                break;
                            case(0):
                                Toast.makeText(LoginAdminActivity.this, "Ошибка!\nНеверно введен индивидуальный код!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void Init()
    {
        etCode = (EditText) findViewById(R.id.logadmin_etCodeInput);

        btnBack = (ImageView) findViewById(R.id.logadmin_ivBtnBack);
        btnGo = (ImageView) findViewById(R.id.logadmin_ivBtnGo);

        mDataBase = FirebaseDatabase.getInstance().getReference("Admins");
        result = 0;
    }
}
