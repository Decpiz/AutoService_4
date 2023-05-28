package com.example.autoservice_4.MainActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.autoservice_4.Prevalent.Prevalent;
import com.example.autoservice_4.R;
import com.example.autoservice_4.UserActivities.UslugiActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnBack, btnAdmin, btnZabilParol;
    private EditText etMailInput, etPasswordInput;
    private CheckBox cbZapomnit;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Init();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goAdmin = new Intent(LoginActivity.this, LoginAdminActivity.class);
                startActivity(goAdmin);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {
        String email = etMailInput.getText().toString();
        String password = etPasswordInput.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();
                        Intent goUslugi = new Intent(LoginActivity.this, UslugiActivity.class);
                        startActivity(goUslugi);

                        if(cbZapomnit.isChecked())
                        {
                           Paper.book().write(Prevalent.UserEmailKey, email);
                           Paper.book().write(Prevalent.UserPasswordKey, password);
                        }
                    }
                    else
                    {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Введен неверный адрес электронной почты или пароль!\nПовторите попытку!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void Init()
    {
        Paper.init(this);

        mAuth = FirebaseAuth.getInstance();

        cbZapomnit = (CheckBox) findViewById(R.id.log_cbZapomnit);

        btnLogin = (Button) findViewById(R.id.log_btnLogin);
        btnBack = (Button) findViewById(R.id.log_btnBack);
        btnZabilParol = (Button) findViewById(R.id.log_btnZabilPass);
        btnAdmin = (Button) findViewById(R.id.log_btnAdmin);

        etMailInput = (EditText) findViewById(R.id.log_numberInput);
        etPasswordInput = (EditText) findViewById(R.id.log_passwordInput);

        loadingBar = new ProgressDialog(this);
    }
}
