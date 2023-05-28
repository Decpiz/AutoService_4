package com.example.autoservice_4.ProfileActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.autoservice_4.Const;
import com.example.autoservice_4.Prevalent.Prevalent;
import com.example.autoservice_4.ProfileActivity;
import com.example.autoservice_4.R;

import io.paperdb.Paper;

public class InfoHumanActivity extends AppCompatActivity {
    private ImageView btnBack, btnEditFio, btnEditContacts;
    private Button btnSaveFio, btnSaveContacts;
    private EditText etName, etSecName, etPatronomic, etNumber, etEmail;
    private String userName, userSecName, userPatronomic, userNumber, userEmail;

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

        btnEditFio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FioElements(1);

                btnSaveFio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        String name = etName.getText().toString();
                        String secName = etSecName.getText().toString();
                        String patronomic = etPatronomic.getText().toString();

                        Paper.book().write(Const.USER_NAME_KEY,name);
                        Paper.book().write(Const.USER_SEC_NAME_KEY,secName);
                        Paper.book().write(Const.USER_PATRONOMIC_KEY,patronomic);

                        etName.setText(name);
                        etSecName.setText(secName);
                        etPatronomic.setText(patronomic);

                        FioElements(0);

                    }
                });
            }
        });

        btnEditContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactsElements(1);

                btnSaveContacts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        String number = etNumber.getText().toString();

                        Paper.book().write(Const.USER_NUMBER_KEY, number);

                        etNumber.setText(number);

                        ContactsElements(0);
                    }
                });
            }
        });

    }

    private void PutEditTexts()
    {
        etName.setText(userName);
        etSecName.setText(userSecName);
        etPatronomic.setText(userPatronomic);
        etNumber.setText(userNumber);
        etEmail.setText(userEmail);
    }

    private void FioElements(int x)
    {
        switch (x)
        {
            case(0):
                btnEditFio.setVisibility(View.VISIBLE);
                btnSaveFio.setVisibility(View.GONE);
                etName.setEnabled(false);
                etSecName.setEnabled(false);
                etPatronomic.setEnabled(false);
                break;

            case(1):
                btnEditFio.setVisibility(View.GONE);
                btnSaveFio.setVisibility(View.VISIBLE);
                etName.setEnabled(true);
                etSecName.setEnabled(true);
                etPatronomic.setEnabled(true);
                break;
        }
    }

    private void ContactsElements(int x)
    {
        switch (x)
        {
            case (0):
                btnEditContacts.setVisibility(View.VISIBLE);
                btnSaveContacts.setVisibility(View.GONE);
                etNumber.setEnabled(false);
                etEmail.setEnabled(false);
                break;

            case (1):
                btnEditContacts.setVisibility(View.GONE);
                btnSaveContacts.setVisibility(View.VISIBLE);
                etNumber.setEnabled(true);
                etEmail.setEnabled(false);
                break;
        }
    }

    private void Init()
    {
        btnBack = (ImageView) findViewById(R.id.infohuman_btnBack);
        btnEditFio = (ImageView) findViewById(R.id.infohuman_btnEditFio);
        btnEditContacts = (ImageView) findViewById(R.id.infohuman_btnEditContacts);

        btnSaveFio = (Button) findViewById(R.id.infohuman_btnSaveFio);
        btnSaveContacts = (Button) findViewById(R.id.infohuman_btnSaveContacts);

        etName = (EditText) findViewById(R.id.infohuman_etName);
        etSecName = (EditText) findViewById(R.id.infohuman_etSecName);
        etPatronomic = (EditText) findViewById(R.id.infohuman_etPatronomic);
        etNumber = (EditText) findViewById(R.id.infohuman_etNumber);
        etEmail = (EditText) findViewById(R.id.infohuman_etEmail);

        userName = Paper.book().read(Const.USER_NAME_KEY);
        userSecName = Paper.book().read(Const.USER_SEC_NAME_KEY);
        userPatronomic = Paper.book().read(Const.USER_PATRONOMIC_KEY);
        userNumber = Paper.book().read(Const.USER_NUMBER_KEY);
        userEmail = Paper.book().read(Prevalent.UserEmailKey);
    }
}