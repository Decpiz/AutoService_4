package com.example.autoservice_4.UserActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autoservice_4.Const;
import com.example.autoservice_4.Model.Appointment;
import com.example.autoservice_4.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class ShowDetalisUslugaActivity extends AppCompatActivity {
    private TextView tvPrice, tvTitle, tvTime, tvFullPrice;
    private ImageView btnBack;
    private Button btnAppointment;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detalis_usluga);

        Init();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goUslugi = new Intent(ShowDetalisUslugaActivity.this, UslugiActivity.class);
                startActivity(goUslugi);
            }
        });

        Intent i = getIntent();
        if(i != null)
        {
            tvTitle.setText(i.getStringExtra(Const.INTENT_EXTRA1));
            tvPrice.setText(i.getStringExtra(Const.INTENT_EXTRA2));
            tvTime.setText(i.getStringExtra(Const.INTENT_EXTRA3));
            tvFullPrice.setText(i.getStringExtra(Const.INTENT_EXTRA4));
        }

        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                assert i != null;
                String uslugaName = i.getStringExtra(Const.INTENT_EXTRA1);
                String name = Paper.book().read(Const.USER_NAME_KEY);
                String secName = Paper.book().read(Const.USER_SEC_NAME_KEY);
                String patronomic = Paper.book().read(Const.USER_PATRONOMIC_KEY);
                String number = Paper.book().read(Const.USER_NUMBER_KEY);


                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(secName)&&!TextUtils.isEmpty(patronomic)&&!TextUtils.isEmpty(number))
                {
                    if(!name.equals("") && !secName.equals("") && !patronomic.equals("") && !number.equals(""))
                    {
                        Toast.makeText(ShowDetalisUslugaActivity.this, "Вы успешно записаны на услугу: "+ uslugaName +
                                "\nОжидайте, вам перезвонят в течение часа", Toast.LENGTH_SHORT).show();

                        Appointment appointment = new Appointment(uslugaName, name, secName, patronomic, number);
                        mDataBase.push().setValue(appointment);
                    }
                }
                else
                {
                    Toast.makeText(ShowDetalisUslugaActivity.this, "Не удалось записаться на услугу!" +
                            "\nЗаполните личные данные в профиле", Toast.LENGTH_SHORT).show();
                    Intent goProfile = new Intent(ShowDetalisUslugaActivity.this, ProfileActivity.class);
                    startActivity(goProfile);
                }
            }
        });
    }

    private void Init()
    {
        tvTitle = (TextView) findViewById(R.id.detal_tvTitle);
        tvPrice = (TextView) findViewById(R.id.detal_tvPrice);
        tvTime = (TextView) findViewById(R.id.detal_tvTime);
        tvFullPrice = (TextView) findViewById(R.id.detal_tvFullPrice);

        btnBack = (ImageView) findViewById(R.id.detal_btnBack);
        btnAppointment = (Button) findViewById(R.id.detal_btnRegUsluga);

        mDataBase = FirebaseDatabase.getInstance().getReference(Const.APPOINTMENT_KEY);
    }
}