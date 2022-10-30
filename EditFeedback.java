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

import java.util.HashMap;
import java.util.Map;

public class EditFeedback extends AppCompatActivity {

    private TextInputEditText userName, userEmail, regNum, conNum, message;
    private Button updateFeedbackBtn,deleteFeedbackBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String feedbackID;
    private FeedbackRVModal feedbackRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        firebaseDatabase = FirebaseDatabase.getInstance();
        userName = findViewById(R.id.idEdtUserName);
        userEmail = findViewById(R.id.idEdtEmail);
        regNum = findViewById(R.id.idEdtRegNum);
        conNum = findViewById(R.id.idEdtContactNum);
        message = findViewById(R.id.idEdtMessage);
        updateFeedbackBtn = findViewById(R.id.idBtnUpdateFeedback);
        deleteFeedbackBtn = findViewById(R.id.idBtnDeleteFeedback);
        feedbackRVModal = getIntent().getParcelableExtra("feedback");
        if (feedbackRVModal != null){
            userName.setText(feedbackRVModal.getFeedbackName());
            userEmail.setText(feedbackRVModal.getFeedbackEmail());
            regNum.setText(feedbackRVModal.getFeedbackRegNo());
            conNum.setText(feedbackRVModal.getFeedbackConNo());
            message.setText(feedbackRVModal.getFeedbackMessage());
            feedbackID = feedbackRVModal.getFeedbackID();
        }
        databaseReference = firebaseDatabase.getReference("Feedbacks").child(feedbackID);
        updateFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedbackName = userName.getText().toString();
                String feedbackEmail = userEmail.getText().toString();
                String feedbackRegNo = regNum.getText().toString();
                String feedbackConNo = conNum.getText().toString();
                String feedbackMessage = message.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("feedbackName",feedbackName);
                map.put("feedbackEmail",feedbackEmail);
                map.put("feedbackRegNo",feedbackRegNo);
                map.put("feedbackConNo",feedbackConNo);
                map.put("feedbackMessage",feedbackMessage);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditFeedback.this, "Feedback Updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditFeedback.this,FeedbackList.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditFeedback.this, "Fail to update feedback..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFeedback();
            }
        });
    }

    private void deleteFeedback(){
        databaseReference.removeValue();
        Toast.makeText(this, "Feedback Deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditFeedback.this, FeedbackList.class));
    }
}