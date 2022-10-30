package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCardActivity extends AppCompatActivity {

    private Button addCardBtn;
    private TextInputEditText cardTypeEdt, cardNameEdt, cardNumEdt, expDateEdt, secCodeEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String cardId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        // initializing all our variables.
        cardTypeEdt = findViewById(R.id.idEdtCardType);
        cardNameEdt = findViewById(R.id.idEdtCardDate);
        cardNumEdt = findViewById(R.id.idEdtCardNum);
        expDateEdt = findViewById(R.id.idEdtCardDate);
        secCodeEdt = findViewById(R.id.idEdtSecCode);
        addCardBtn = findViewById(R.id.idBtnAddCard);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference( "Cards");

        addCardBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String cardType = cardTypeEdt.getText().toString();
                String cardName = cardNameEdt.getText().toString();
                String cardNum = cardNumEdt.getText().toString();
                String cardDate = expDateEdt.getText().toString();
                String secCode = secCodeEdt.getText().toString();
                cardId = cardName;
                CardRVModal cardRVModal = new CardRVModal(cardId, cardType, cardName, cardNum, cardDate, secCode);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(cardId).setValue(cardRVModal);
                        Toast.makeText(AddCardActivity.this, "Card Added..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent( AddCardActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCardActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}