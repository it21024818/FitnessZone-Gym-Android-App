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

import java.util.HashMap;
import java.util.Map;

public class editRoutine extends AppCompatActivity {
    //declaring variables for main activity class
    private TextInputEditText exerciseName,exerciseArea,exerciseSets,exerciseDuration,exerciseImg,specialNotes;
    private Button updateRoutine,deleteRoutine;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebase;
    private DatabaseReference databaserefer;
    private String exerciseId;
    private ExerciseRVModel exerciseRVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_routine);

        //initializing all the variables

        firebase = FirebaseDatabase.getInstance();

        exerciseName = findViewById(R.id.editExerciseName);
        exerciseArea = findViewById(R.id.editExerciseArea);
        exerciseSets = findViewById(R.id.editExerciseSet);
        exerciseDuration = findViewById(R.id.editExerciseTime);
        exerciseImg = findViewById(R.id.editExerciseImg);
        specialNotes = findViewById(R.id.editSpecialNotes);
        updateRoutine = findViewById(R.id.updateExercisebtn);
        deleteRoutine = findViewById(R.id.deleteExercisebtn);
        loadingPB = findViewById(R.id.routineLoading);

        exerciseRVModel = getIntent().getParcelableExtra("exercise");

        if(exerciseRVModel!=null){
            exerciseName.setText(exerciseRVModel.getExName());
            exerciseArea.setText(exerciseRVModel.getExArea());
            exerciseSets.setText(exerciseRVModel.getExSets());
            exerciseDuration.setText(exerciseRVModel.getExduration());
            exerciseImg.setText(exerciseRVModel.getEximg());
            specialNotes.setText(exerciseRVModel.getSpecNotes());
            exerciseId = exerciseRVModel.getExId();
        }
        //initializing database reference
        databaserefer = firebase.getReference("Exercises").child(exerciseId);
        updateRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String exername = exerciseName.getText().toString();
                String exerarea = exerciseArea.getText().toString();
                String exerset = exerciseSets.getText().toString();
                String exerduration = exerciseDuration.getText().toString();
                String exernotes = specialNotes.getText().toString();
                String exerimg = exerciseImg.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("exName",exername);
                map.put("exArea",exerarea);
                map.put("exSets",exerset);
                map.put("exduration",exerduration);
                map.put("eximg",exernotes);
                map.put("specNotes",exerimg);
                map.put("exId",exerciseId);

                databaserefer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaserefer.updateChildren(map);
                        Toast.makeText(editRoutine.this,"Exercise Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(editRoutine.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(editRoutine.this,"Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        deleteRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteExercise();
            }
        });

    }
    private void deleteExercise(){
        databaserefer.removeValue();
        Toast.makeText(this,"Exercise Deleted...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(editRoutine.this,MainActivity.class));
    }
}