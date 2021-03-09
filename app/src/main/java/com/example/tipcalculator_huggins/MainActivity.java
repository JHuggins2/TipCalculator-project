package com.example.tipcalculator_huggins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTip;
    private EditText editTextPeople;
    private EditText editTextBill;

    private Button buttonCalculate;
    private Button buttonReset;

    private boolean validPeople;
    private boolean validBill;
    private boolean validTip;
    private boolean customTip;

    private int numPeople;
    private float bill;
    private float tipPercent;

    private float totalTip;
    private float totalPerson;
    private float totalAmount;

    private RadioGroup tipChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTip = findViewById(R.id.editTextTip);
        editTextPeople = findViewById(R.id.editTextPeople);
        editTextBill = findViewById(R.id.editTextBill);

        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonReset);

        tipChoices = findViewById(R.id.radioGroup);


        editTextBill.setOnKeyListener(mKeyListener);
        editTextPeople.setOnKeyListener(mKeyListener);
        editTextTip.setOnKeyListener(mKeyListener);


        buttonReset.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                editTextBill.setText("");
                editTextPeople.setText("");
                editTextTip.setText("");
                validBill = false;
                validPeople = false;
                validTip = false;
                tipChoices.clearCheck();
                buttonCalculate.setEnabled(false);
                editTextTip.setEnabled(false);
            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalTip = bill * tipPercent;
                totalAmount = bill + totalTip;
                totalPerson = totalAmount / numPeople;

                System.out.println(totalAmount + "  " + totalTip + "  " + totalPerson);
            }
        });
    }

    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            switch (v.getId()) {
                case R.id.editTextBill:
                    try{ bill = Float.parseFloat(editTextBill.getText().toString()); }
                    catch (NumberFormatException e){ bill = -1; }
                    if(bill < 0.01f && bill != -1){
                        showErrorAlert("Invalid Bill", editTextBill.getId());
                        editTextBill.setText("");
                        validBill = false;
                    } else{ validBill = true; }
                    setButtonCalculate();
                    break;
                case R.id.editTextPeople:
                    try{ numPeople = Integer.parseInt(editTextPeople.getText().toString()); }
                    catch (NumberFormatException e){ numPeople = -1; }
                    if(numPeople < 1 && numPeople != -1){
                        showErrorAlert("Invalid Number of People", editTextPeople.getId());
                        editTextPeople.setText("");
                        validPeople = false;
                    } else{ validPeople = true; }
                    setButtonCalculate();
                    break;
                case R.id.editTextTip:
                    try{ tipPercent = Float.parseFloat(editTextTip.getText().toString()) / 100; }
                    catch (NumberFormatException e){ tipPercent = -1; }
                    System.out.println(tipPercent);
                    if(tipPercent < 0 && tipPercent != -1){
                        showErrorAlert("Invalid Tip Amount", editTextTip.getId());
                        editTextTip.setText("");
                        validTip = false;
                    } else{ validTip = true; }
                    setButtonCalculate();
                    break;
                default:
                    break;
            }
            return false;
        }

    };

    private void setButtonCalculate(){
        if(validBill && validPeople && validTip){
            buttonCalculate.setEnabled(true);
        } else { buttonCalculate.setEnabled(false); }
    }

    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton20:
                if(checked){ editTextTip.setEnabled(false); }
                tipPercent = 0.20f;
                break;
            case R.id.radioButton15:
                if(checked){ editTextTip.setEnabled(false); }
                tipPercent = 0.15f;
                break;
            case R.id.radioButton10:
                if(checked){ editTextTip.setEnabled(false); }
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