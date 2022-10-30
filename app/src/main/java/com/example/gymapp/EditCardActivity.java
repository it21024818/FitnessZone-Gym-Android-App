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

import java.util.HashMap;
import java.util.Map;

public class EditCardActivity extends AppCompatActivity {

    private TextInputEditText cardTypeEdt, cardNameEdt, cardNumEdt, expDateEdt, secCodeEdt;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String cardID;
    private CardRVModal cardRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_card);
        cardTypeEdt = findViewById(R.id.idEdtCardType);
        cardNameEdt = findViewById(R.id.idEdtCardName);
        cardNumEdt = findViewById(R.id.idEdtCardNum);
        expDateEdt = findViewById(R.id.idEdtCardDate);
        secCodeEdt = findViewById(R.id.idEdtSecCode);
        Button addCardBtn = findViewById(R.id.idBtnAddCard);
        loadingPB = findViewById(R.id.idPBLoading);
        Button deleteCardBtn = findViewById(R.id.idBtnDeleteCard);
        firebaseDatabase = FirebaseDatabase.getInstance();
        cardRVModal = getIntent().getParcelableExtra("card");
        if(cardRVModal!=null){
            cardTypeEdt.setText(cardRVModal.getCardType());
            cardNameEdt.setText(cardRVModal.getCardName());
            cardNumEdt.setText(cardRVModal.getCardNum());
            expDateEdt.setText(cardRVModal.getCardDate());
            secCodeEdt.setText(cardRVModal.getSecCode());
            cardID = cardRVModal.getCardID();
        }

        databaseReference = firebaseDatabase.getReference("Cards").child(cardID);
        addCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String cardType = cardTypeEdt.getText().toString();
                String cardName = cardNameEdt.getText().toString();
                String cardNum = cardNumEdt.getText().toString();
                String cardDate = expDateEdt.getText().toString();
                String secCode = secCodeEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("cardType", cardType);
                map.put("cardName", cardName);
                map.put("cardNum", cardNum);
                map.put("cardDate", cardDate);
                map.put("secCode", secCode);
                map.put("cardID", cardID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditCardActivity.this, "Card updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditCardActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditCardActivity.this, "Fail to update card..", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        deleteCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCard();
            }
        });
    }

    private void deleteCard(){
        databaseReference.removeValue();
        Toast.makeText(this, "Card Deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCardActivity.this, MainActivity.class));
    }
}