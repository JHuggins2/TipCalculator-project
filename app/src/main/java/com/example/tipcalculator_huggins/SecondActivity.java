package com.example.tipcalculator_huggins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewTotal;
    private TextView textViewTip;
    private TextView textViewPerson;

    private float totalAmount;
    private float totalTip;
    private float totalPerson;

    private Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent i = getIntent();

        textViewTotal = findViewById(R.id.textViewTotal);
        textViewTip = findViewById(R.id.textViewTipTotal);
        textViewPerson= findViewById(R.id.textViewPersonTotal);

        totalAmount = i.getExtras().getFloat(getString(R.string.totalAmountKey));
        totalTip = i.getExtras().getFloat(getString(R.string.totalTipKey));
        totalPerson = i.getExtras().getFloat(getString(R.string.totalPeopleKey));

        textViewTotal.setText(String.format("%.02f",totalAmount));
        textViewTip.setText(String.format("%.02f",totalTip));
        textViewPerson.setText(String.format("%.02f",totalPerson));

        buttonReturn = findViewById(R.id.buttonReturn);

        buttonReturn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}