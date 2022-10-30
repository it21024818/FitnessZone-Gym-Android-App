package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class PackagesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        Button continueBtn = findViewById(R.id.idContinue);

        continueBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // opening a new activity for adding a course.
                Intent i = new Intent(PackagesActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Button calcBtn = findViewById(R.id.idCalcBtn);
        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculateTotal();

            }
        });

    }

    private void CalculateTotal(){
        TotalCalc obCalc = new TotalCalc();
        RadioButton obBasic = findViewById(R.id.idBasic);
        RadioButton obPlatinum = findViewById(R.id.idPlatinum);
        RadioButton obGold = findViewById(R.id.idGold);
        CheckBox obPT = findViewById(R.id.idPerTrain);
        TextView obTotal = findViewById(R.id.idTotal);
        double value = 0;

        if(obBasic.isChecked()){
            value = 2500;
        } else if(obPlatinum.isChecked()){
            value = 8000;
        } else if(obGold.isChecked()){
            value = 12000;
        } else{
            Toast.makeText(this, "Please select a package", Toast.LENGTH_LONG).show();
        }

        if(obPT.isChecked()){
            obTotal.setText(String.valueOf(obCalc.addPTfee(value)));
        } else{
            obTotal.setText(String.valueOf(value));
        }

    }

}