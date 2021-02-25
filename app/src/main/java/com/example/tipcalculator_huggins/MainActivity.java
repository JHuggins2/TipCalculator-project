package com.example.tipcalculator_huggins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTip;
    private float tipPercent;
    private Button buttonCalculate;

    private boolean validPeople;
    private boolean validTotal;
    private boolean validTip;
    private boolean customTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextTip = findViewById(R.id.editTextTip);
        buttonCalculate = findViewById(R.id.buttonCalculate);
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton20:
                tipPercent = 0.20f;
                break;
            case R.id.radioButton15:
                tipPercent = 0.15f;
                break;
            case R.id.radioButton10:
                tipPercent = 0.1f;
                break;
            case R.id.radioButtonCustom:
                if(checked){ editTextTip.setEnabled(true); }
                customTip = true;
                break;
            default:
                editTextTip.setEnabled(false);
                break;
        }
    }
}