package com.example.supportservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFeedback extends AppCompatActivity {

    private TextInputEditText userName, userEmail, regNum, conNum, message;
    private Button addFeedbackBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String feedbackID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);
        userName = findViewById(R.id.idEdtUserName);
        userEmail = findViewById(R.id.idEdtEmail);
        regNum = findViewById(R.id.idEdtRegNum);
        conNum = findViewById(R.id.idEdtContactNum);
        message = findViewById(R.id.idEdtMessage);
        addFeedbackBtn = findViewById(R.id.idBtnAddFeedback);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Feedbacks");

        addFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedbackName = userName.getText().toString();
                String feedbackEmail = userEmail.getText().toString();
                String feedbackRegNo = regNum.getText().toString();
                String feedbackConNo = conNum.getText().toString();
                String feedbackMessage = message.getText().toString();
                feedbackID = feedbackName;
                FeedbackRVModal feedbackRVModal = new FeedbackRVModal(feedbackName,feedbackMessage,feedbackEmail,feedbackRegNo,feedbackConNo,feedbackID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(feedbackID).setValue(feedbackRVModal);
                        Toast.makeText(AddFeedback.this,"Feedback Posted..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddFeedback.this, FeedbackList.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFeedback.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}