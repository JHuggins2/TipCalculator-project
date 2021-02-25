package com.example.tipcalculator_huggins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTip;
    private float tipPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextTip = findViewById(R.id.editTextTip);
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
                tipPercent = -1; // set to -1 to show that need to pull value from editTextTip
                break;
            default:
                editTextTip.setEnabled(false);
                break;
        }
    }
}