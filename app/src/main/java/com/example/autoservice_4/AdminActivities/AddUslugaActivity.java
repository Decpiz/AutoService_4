package com.example.autoservice_4.AdminActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.autoservice_4.Const;
import com.example.autoservice_4.Model.Uslugi;
import com.example.autoservice_4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUslugaActivity extends AppCompatActivity {
    private Toolbar tobUslugi, tobZapis;
    private Button btnAdd;
    private EditText et1, et2;
    private EditText et3, et4;
    private DatabaseReference mDataBase;
    private AlertDialog.Builder confirmAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usluga);

        Init();
        DownPanel();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = et1.getText().toString();
                String price = et2.getText().toString();
                String time = et3.getText().toString();
                String fullPrice = et4.getText().toString();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(time) && !TextUtils.isEmpty(fullPrice))
                {
                    if (!title.equals("") && !price.equals("") && !time.equals("") && !fullPrice.equals(""))
                    {
                        confirmAdd.setMessage("Вы действительно хотите добавить услугу: " + title);
                        confirmAdd.setTitle("Подтверждение");
                        confirmAdd.setCancelable(true);

                        confirmAdd.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Uslugi usluga = new Uslugi(title, price + "р", time + "ч", fullPrice + "р");
                                mDataBase.push().setValue(usluga);

                                Toast.makeText(AddUslugaActivity.this, "Услуга успешно добавлена в список услуг!", Toast.LENGTH_SHORT).show();

                                et1.setText(null);
                                et2.setText(null);
                                et3.setText(null);
                                et4.setText(null);
                            }
                        });
                        confirmAdd.setNegativeButton("Отменить", null);
                        confirmAdd.create();
                        confirmAdd.show();
                    }
                }
                else
                {
                    Toast.makeText(AddUslugaActivity.this, "Введите данные!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Init()
    {
        btnAdd = (Button) findViewById(R.id.btnAddUsluga);

        et1 = (EditText) findViewById(R.id.titleInput);
        et2 = (EditText) findViewById(R.id.priceInput);
        et3 = (EditText) findViewById(R.id.timeInput);
        et4 = (EditText) findViewById(R.id.fullPriceInput);

        mDataBase = FirebaseDatabase.getInstance().getReference(Const.USLUGI_KEY);

        tobUslugi = (Toolbar) findViewById(R.id.tobUslugi);
        tobZapis = (Toolbar) findViewById(R.id.tobZapis);

        confirmAdd = new AlertDialog.Builder(this);
    }

    private void DownPanel()
    {
        tobZapis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goZapis = new Intent(AddUslugaActivity.this, ZapisActivity.class);
                startActivity(goZapis);
            }
        });


        tobUslugi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goAdd = new Intent(AddUslugaActivity.this, UslugiAdminActivity.class);
                startActivity(goAdd);
            }
        });
    }
}