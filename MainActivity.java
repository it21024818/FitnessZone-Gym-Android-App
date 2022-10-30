package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RoutineRVAdaptor.routineClickInterface{

    //declaring variables for main activity class

    private RecyclerView exerciseRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebase;
    private DatabaseReference databaserefer;
    private ArrayList<ExerciseRVModel> exerciseRVModelsArrayList;
    private RelativeLayout homeRL;
    private RoutineRVAdaptor routineRVAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exerciseRV = findViewById(R.id.RVExercises);
        loadingPB = findViewById(R.id.PBloading);
        addFAB = findViewById(R.id.floatingBtn);

        firebase = FirebaseDatabase.getInstance();
        databaserefer = firebase.getReference("Exercises"); //getting the database reference

        exerciseRVModelsArrayList = new ArrayList<>();
        homeRL = findViewById(R.id.RLBsheet);

        routineRVAdaptor = new RoutineRVAdaptor(exerciseRVModelsArrayList,this,this::onRoutineClick); //initializing adaptor class

        exerciseRV.setLayoutManager(new LinearLayoutManager(this));  //setting Relative layout view
        exerciseRV.setAdapter(routineRVAdaptor);

        //adding an onClick event for the floating action button
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, addRoutine.class);   //direct to addroutine class on click
                startActivity(i);
            }
        });
        getAllExercises();  //function calling
    }

    //retrieve all the exercise in the main activity
    private void getAllExercises(){
        exerciseRVModelsArrayList.clear();
        databaserefer.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                //exerciseRVModelsArrayList.add(snapshot.getValue(ExerciseRVModel.class));
                routineRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                routineRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                routineRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                routineRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onRoutineClick(int position) {
        displayBotttomSheet(exerciseRVModelsArrayList.get(position));  //function calling
    }
    //function declaration
    private void displayBotttomSheet(ExerciseRVModel model){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.botttom_sheet_dialog,homeRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        //Initializing the variable with their respective ids

        TextView exerciseNameTV = layout.findViewById(R.id.TVexName);
        TextView exerciseareaTV = layout.findViewById(R.id.TVarea);
        TextView exerciseSetsTV = layout.findViewById(R.id.TVsets);
        TextView exerciseSpecialNotesTV = layout.findViewById(R.id.TVspecialNotes);
        ImageView exerciseIV = layout.findViewById(R.id.IVExercise);

        Button editExBtn = layout.findViewById(R.id.editExerciseBtn);

        exerciseNameTV.setText(model.getExName());
        exerciseareaTV.setText(model.getExArea());
        exerciseSetsTV.setText(model.getExSets());
        exerciseSpecialNotesTV.setText(model.getSpecNotes());
        Picasso.get().load(model.getEximg()).into(exerciseIV);

        editExBtn.setOnClickListener(new View.OnClickListener() {

           //Onclick go to edit routine activity
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,editRoutine.class);
                i.putExtra("exercise",model);
                startActivity(i);
            }
        });

    }
}