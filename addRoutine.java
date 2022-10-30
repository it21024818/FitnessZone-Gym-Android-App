package com.example.fitnessapp;

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

public class addRoutine extends AppCompatActivity {
    //declaring variables
    private TextInputEditText exerciseName,exerciseArea,exerciseSets,exerciseDuration,exerciseImg,specialNotes;
    private Button addRoutinebtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebase;
    private DatabaseReference databaserefer;
    private String exerciseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);

        //initializing variables with their id
        exerciseName = findViewById(R.id.editExerciseName);
        exerciseArea = findViewById(R.id.editExerciseArea);
        exerciseSets = findViewById(R.id.editExerciseSet);
        exerciseDuration = findViewById(R.id.editExerciseTime);
        exerciseImg = findViewById(R.id.editExerciseImg);
        specialNotes = findViewById(R.id.editSpecialNotes);
        addRoutinebtn = findViewById(R.id.addExercisebtn);
        loadingPB = findViewById(R.id.routineLoading);
        firebase = FirebaseDatabase.getInstance(); //database initializing
        databaserefer = firebase.getReference("Exercises"); //getting database reference

        addRoutinebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //retrieving data to the layout
                loadingPB.setVisibility(View.VISIBLE);
                String exername = exerciseName.getText().toString();
                String exerarea = exerciseArea.getText().toString();
                String exerset = exerciseSets.getText().toString();
                String exerduration = exerciseDuration.getText().toString();
                String exernotes = specialNotes.getText().toString();
                String exerimg = exerciseImg.getText().toString();
                exerciseId = exername;

                ExerciseRVModel exerciseRVModel = new ExerciseRVModel(exername,exerarea,exerset,exerduration,exerimg,exernotes,exerciseId);

                databaserefer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaserefer.child(exerciseId).setValue(exerciseRVModel);
                        Toast.makeText(addRoutine.this,"Exercise Added",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(addRoutine.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(addRoutine.this,"Error is" + error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}