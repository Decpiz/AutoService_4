package com.example.autoservice_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowDetalisUslugaActivity extends AppCompatActivity {
    private TextView tvPrice, tvTitle, tvTime, tvFullPrice;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detalis_usluga);

        Init();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goUslugi = new Intent(ShowDetalisUslugaActivity.this,UslugiActivity.class);
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
    }

    private void Init()
    {
        tvTitle = (TextView) findViewById(R.id.detal_tvTitle);
        tvPrice = (TextView) findViewById(R.id.detal_tvPrice);
        tvTime = (TextView) findViewById(R.id.detal_tvTime);
        tvFullPrice = (TextView) findViewById(R.id.detal_tvFullPrice);
        btnBack = (ImageView) findViewById(R.id.detal_btnBack);
    }
}