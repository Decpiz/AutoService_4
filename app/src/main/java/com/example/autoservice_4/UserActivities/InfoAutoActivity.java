package com.example.autoservice_4.UserActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.autoservice_4.Const;
import com.example.autoservice_4.R;

import io.paperdb.Paper;

public class InfoAutoActivity extends AppCompatActivity {
    private ImageView btnBack, btnEdit;
    private EditText etMarka, etModel, etYear;
    private Button btnSave;
    String marka, model, year;

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
            public void onClick(View view)
            {
                AutoElements(1);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        marka = etMarka.getText().toString();
                        model = etModel.getText().toString();
                        year = etYear.getText().toString();

                        etMarka.setText(marka);
                        etModel.setText(model);
                        etYear.setText(year);

                        Paper.book().write(Const.MARKA_AUTO, marka);
                        Paper.book().write(Const.MODEL_AUTO, model);
                        Paper.book().write(Const.YEAR_AUTO,year);

                        AutoElements(0);
                    }
                });
            }
        });
    }

    private void Init()
    {
        btnBack = (ImageView) findViewById(R.id.infoauto_btnBack);
        btnEdit = (ImageView) findViewById(R.id.infoauto_btnEditAuto);

        etMarka = (EditText) findViewById(R.id.infoauto_etMarka);
        etModel = (EditText) findViewById(R.id.infoauto_etModel);
        etYear = (EditText) findViewById(R.id.infoauto_etYear);

        btnSave = (Button) findViewById(R.id.infoauto_btnSave);
    }

    private void AutoElements(int i)
    {
        switch (i)
        {
            case(0):
                btnEdit.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.GONE);
                etMarka.setEnabled(false);
                etModel.setEnabled(false);
                etYear.setEnabled(false);
                break;

            case(1):
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
        marka = Paper.book().read(Const.MARKA_AUTO);
        model = Paper.book().read(Const.MODEL_AUTO);
        year = Paper.book().read(Const.YEAR_AUTO);

        etMarka.setText(marka);
        etModel.setText(model);
        etYear.setText(year);
    }
}

