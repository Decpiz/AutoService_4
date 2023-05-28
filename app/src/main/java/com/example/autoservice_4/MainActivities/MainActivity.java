package com.example.autoservice_4.MainActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.autoservice_4.AdminActivities.AddUslugaActivity;
import com.example.autoservice_4.Prevalent.Prevalent;
import com.example.autoservice_4.R;
import com.example.autoservice_4.UserActivities.UslugiActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin, btnReg, btnGdeMi, btnSotr;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.main_btnLogin);
        btnReg = (Button) findViewById(R.id.main_btnReg);

        Paper.init(this);
        String emailKey = Paper.book().read(Prevalent.UserEmailKey);
        String passKey = Paper.book().read(Prevalent.UserPasswordKey);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(goLoginActivity);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goRegisterActivity = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(goRegisterActivity);
            }
        });

        if(!TextUtils.isEmpty(emailKey)&&!TextUtils.isEmpty(passKey))
        {
            if(!emailKey.equals("")&&!passKey.equals(""))
            {
                mAuth.signInWithEmailAndPassword(emailKey, passKey).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Intent goUslugi = new Intent(MainActivity.this, UslugiActivity.class);
                            startActivity(goUslugi);
                        }
                    }
                });
            }
        }
    }
}
