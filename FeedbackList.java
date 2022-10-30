package com.example.supportservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class FeedbackList extends AppCompatActivity implements FeedbackRVAdapter.FeedbackClickInterface{

    private RecyclerView feedbackRV;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<FeedbackRVModal> feedbackRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private FeedbackRVAdapter feedbackRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);
        feedbackRV = findViewById(R.id.idRVFeedbacks);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Feedbacks");
        feedbackRVModalArrayList = new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        feedbackRVAdapter = new FeedbackRVAdapter(feedbackRVModalArrayList,this,this);
        feedbackRV.setLayoutManager(new LinearLayoutManager(this));
        feedbackRV.setAdapter(feedbackRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FeedbackList.this,AddFeedback.class));
            }
        });

        getAllFeedbacks();
    }

    private void getAllFeedbacks(){

        feedbackRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                feedbackRVModalArrayList.add(snapshot.getValue(FeedbackRVModal.class));
                feedbackRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                feedbackRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                feedbackRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                feedbackRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onFeedbackClick(int position) {
        displayBottomSheet(feedbackRVModalArrayList.get(position));
    }

    private void displayBottomSheet(FeedbackRVModal feedbackRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView feedbackNameTV = layout.findViewById(R.id.idTVName);
        TextView feedbackEmailTV = layout.findViewById(R.id.idTVEmail);
        ImageView feedbackIV = layout.findViewById(R.id.idIVFeedback);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button viewFeedbackBtn = layout.findViewById(R.id.idBtnViewDetails);

        feedbackNameTV.setText(feedbackRVModal.getFeedbackName());
        feedbackEmailTV.setText(feedbackRVModal.getFeedbackEmail());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FeedbackList.this,EditFeedback.class);
                i.putExtra("feedback",feedbackRVModal);
                startActivity(i);
            }
        });

//        viewFeedbackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(feedbackRVModal.getFeedbackLink()));
//                startActivity(i);
//            }
//        });
    }
}
