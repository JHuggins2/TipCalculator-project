package com.example.tipcalculator_huggins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = new Intent(this, SecondActivity.class);

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
                totalTip = bill * (tipPercent / 100);
                totalAmount = bill + totalTip;
                totalPerson = totalAmount / numPeople;

                i.putExtra(getString(R.string.totalAmountKey), totalAmount);
                i.putExtra(getString(R.string.totalTipKey), totalTip);
                i.putExtra(getString(R.string.totalPeopleKey), totalPerson);
                startActivity(i);
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
                    if(bill < 1 && bill != -1){
                        showErrorAlert(getString(R.string.invalidBill), editTextBill.getId());
                        editTextBill.setText("");
                        validBill = false;
                    } else{ validBill = true; }
                    setButtonCalculate();
                    break;
                case R.id.editTextPeople:
                    try{ numPeople = Integer.parseInt(editTextPeople.getText().toString()); }
                    catch (NumberFormatException e){ numPeople = -1; }
                    if(numPeople < 1 && numPeople != -1){
                        showErrorAlert(getString(R.string.invalidPeople), editTextPeople.getId());
                        editTextPeople.setText("");
                        validPeople = false;
                    } else{ validPeople = true; }
                    setButtonCalculate();
                    break;
                case R.id.editTextTip:
                    try{ tipPercent = Float.parseFloat(editTextTip.getText().toString()); }
                    catch (NumberFormatException e){ tipPercent = -1; }
                    if(tipPercent < 1 && tipPercent != -1){
                        showErrorAlert(getString(R.string.invalidTip), editTextTip.getId());
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
                .setTitle(getString(R.string.errorStr))
                .setMessage(errorMessage)
                .setNeutralButton(getString(R.string.closeStr),
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
                tipPercent = 20;
                validTip = true;
                setButtonCalculate();
                break;
            case R.id.radioButton15:
                if(checked){ editTextTip.setEnabled(false); }
                tipPercent = 15;
                validTip = true;
                setButtonCalculate();
                break;
            case R.id.radioButton10:
                if(checked){ editTextTip.setEnabled(false); }
                tipPercent = 10;
                validTip = true;
                setButtonCalculate();
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putFloat(getString(R.string.bundleBillKey), bill);
        outState.putInt(getString(R.string.bundlePeopleKey), numPeople);
        outState.putFloat(getString(R.string.bundleTipKey), tipPercent);
        outState.putInt(getString(R.string.bundleTipChoiceKey),tipChoices.getCheckedRadioButtonId());
        outState.putBoolean(getString(R.string.bundleValidPeopleKey),validPeople);
        outState.putBoolean(getString(R.string.bundleValidBillKey),validBill);
        outState.putBoolean(getString(R.string.bundleValidTipKey),validTip);
        outState.putBoolean(getString(R.string.bundleCustomTipKey),customTip);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        bill = savedInstanceState.getFloat(getString(R.string.bundleBillKey));
        numPeople = savedInstanceState.getInt(getString(R.string.bundlePeopleKey));
        tipPercent = savedInstanceState.getFloat(getString(R.string.bundleTipKey));
        int tipChoice = savedInstanceState.getInt(getString(R.string.bundleTipChoiceKey));
        tipChoices.check(tipChoice);

        validPeople = savedInstanceState.getBoolean((getString(R.string.bundleValidPeopleKey)));
        validBill = savedInstanceState.getBoolean((getString(R.string.bundleValidBillKey)));
        validTip = savedInstanceState.getBoolean((getString(R.string.bundleValidTipKey)));
        setButtonCalculate();

        customTip = savedInstanceState.getBoolean((getString(R.string.bundleCustomTipKey)));
        if(customTip){ editTextTip.setEnabled(true); }
    }
}