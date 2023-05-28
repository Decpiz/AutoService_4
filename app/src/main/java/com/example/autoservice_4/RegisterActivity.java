package com.example.autoservice_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private Button btnBack, btnLogin, btnRegister;
    private EditText etMailInput, etPasswordInput, etPodPasswordInput;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btnBack = (Button) findViewById(R.id.reg_btnBack);
        btnLogin = (Button) findViewById(R.id.reg_btnLogin);
        btnRegister = (Button) findViewById(R.id.reg_btnRegister);

        etMailInput = (EditText) findViewById(R.id.reg_numberInput);
        etPasswordInput = (EditText) findViewById(R.id.reg_passInput);
        etPodPasswordInput = (EditText) findViewById(R.id.reg_podPassInput);

        loadingBar = new ProgressDialog(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goLoginActivity);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String email = etMailInput.getText().toString();
        String password = etPasswordInput.getText().toString();
        String podPassword = etPodPasswordInput.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(podPassword))
        {
            Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show();
        }
        else if (!(password.equals(podPassword)))
        {
            Toast.makeText(this, "Пароли не совпадают\nПовторите ввод!", Toast.LENGTH_SHORT).show();
            etPodPasswordInput.setText("");
        }
        else
        {
            loadingBar.setTitle("Создание аккаунта");
            loadingBar.setMessage("Подождите!");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        loadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();
                        Intent goUslugi = new Intent(RegisterActivity.this, UslugiActivity.class);
                        startActivity(goUslugi);
                    }
                    else
                    {
                        loadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, "Ошибка!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
