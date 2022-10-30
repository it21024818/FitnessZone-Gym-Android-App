package com.example.gymapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CardRVAdapter.CardClickInterface{

    private FloatingActionButton addCardFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView cardRV;
    private ProgressBar loadingPB;
    private ArrayList<CardRVModal> cardRVModalArrayList;
    private CardRVAdapter cardRVAdapter;
    private RelativeLayout homeRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initializing all our variables.
        loadingPB = findViewById(R.id.idPBLoading);
        cardRV = findViewById(R.id.idRVCards);
        homeRL = findViewById(R.id.idRLBSheet);
        addCardFAB = findViewById(R.id.idFABAddCard);
        firebaseDatabase = FirebaseDatabase.getInstance();
        cardRVModalArrayList = new ArrayList<>();
        // on below line we are getting database reference.
        databaseReference = firebaseDatabase.getReference("Cards");

        addCardFAB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // opening a new activity for adding a course.
                Intent i = new Intent(MainActivity.this, AddCardActivity.class);
                startActivity(i);
            }
        });
        // on below line initializing our adapter class.
        cardRVAdapter = new CardRVAdapter(cardRVModalArrayList, this, this::onCardClick);
        // setting layout malinger to recycler view on below line.
        cardRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view on below line.
        cardRV.setAdapter(cardRVAdapter);
        // on below line calling a method to fetch courses from database.
        getCards();

    }

    private void getCards() {
        // on below line clearing our list.
        cardRVModalArrayList.clear();
        // on below line we are calling add child event listener method to read the data.
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // on below line we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
                // adding snapshot to our array list on below line.
                cardRVModalArrayList.add(snapshot.getValue(CardRVModal.class));
                // notifying our adapter that data has changed.
                cardRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added
                // we are notifying our adapter and making progress bar
                // visibility as gone.
                loadingPB.setVisibility(View.GONE);
                cardRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // notifying our adapter when child is removed.
                cardRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // notifying our adapter when child is moved.
                cardRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public void onCardClick(int position) {
        // calling a method to display a bottom sheet on below line.
        displayBottomSheet(cardRVModalArrayList.get(position));
    }

    private void displayBottomSheet(CardRVModal modal) {
        // on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        // on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);
        // setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        // on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        // on below line we are creating variables for
        // our text view and image view inside bottom sheet
        // and initialing them with their ids.
        TextView cardNameTV = layout.findViewById(R.id.idTVCardName);
        TextView cardNumTV = layout.findViewById(R.id.idTVCardNum);
//        TextView suitedForTV = layout.findViewById(R.id.idTVSuitedFor);
//        TextView priceTV = layout.findViewById(R.id.idTVCoursePrice);
        //ImageView cardIV = layout.findViewById(R.id.idIVCard);
        // on below line we are setting data to different views on below line.
        cardNameTV.setText(modal.getCardName());
        cardNumTV.setText(modal.getCardNum());
//        suitedForTV.setText("Suited for " + modal.getBestSuitedFor());
//        priceTV.setText("Rs." + modal.getCoursePrice());
        //Picasso.get().load(modal.getCourseImg()).into(courseIV);
       // Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditCard);

//        //adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are opening our EditCourseActivity on below line.
                Intent i = new Intent(MainActivity.this, EditCardActivity.class);
                // on below line we are passing our course modal
                i.putExtra("card", modal);
                startActivity(i);
            }
        });

    }
}