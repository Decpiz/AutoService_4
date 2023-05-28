package com.example.autoservice_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.autoservice_4.Model.Uslugi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUslugaActivity extends AppCompatActivity {
    private Button btnZalupa;
    private EditText et1, et2;
    private EditText et3, et4;
    private DatabaseReference mDataBase;
    private String USLUGI_KEY = "Uslugi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usluga);

        btnZalupa = (Button) findViewById(R.id.buttonZalupa);

        et1 = (EditText) findViewById(R.id.titleInput);
        et2 = (EditText) findViewById(R.id.priceInput);
        et3 = (EditText) findViewById(R.id.timeInput);
        et4 = (EditText) findViewById(R.id.fullPriceInput);

        mDataBase = FirebaseDatabase.getInstance().getReference(USLUGI_KEY);

        btnZalupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et1.getText().toString();
                String price = et2.getText().toString()+"р";
                String time = et3.getText().toString()+"ч";
                String fullPrice = et4.getText().toString()+"р";

                Uslugi usluga = new Uslugi(title, price,time,fullPrice);

                mDataBase.push().setValue(usluga);
            }
        });
    }
}