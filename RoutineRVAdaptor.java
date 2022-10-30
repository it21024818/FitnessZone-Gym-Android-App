package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoutineRVAdaptor extends RecyclerView.Adapter<RoutineRVAdaptor.ViewHolder> {

    //declaring variables
    private int lastPos = -1;
    private ArrayList <ExerciseRVModel> exerciseRVModelArrayList;
    private Context context;
    private routineClickInterface routineclickInterface;

    //constructor with parameters
    public RoutineRVAdaptor(ArrayList<ExerciseRVModel> exerciseRVModelArrayList, Context context,routineClickInterface routineclickInterface) {
        this.exerciseRVModelArrayList = exerciseRVModelArrayList;
        this.context = context;
        this.routineclickInterface = routineclickInterface;
    }

    @NonNull
    @Override
    public RoutineRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercise_rv_item,parent,false);
        return new ViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RoutineRVAdaptor.ViewHolder holder,  int position) {

        //initializing variables
        ExerciseRVModel exerciseRVModel = exerciseRVModelArrayList.get(position);
        holder.exercisenameTV.setText(exerciseRVModel.getExName());
        holder.noofsetsTV.setText(exerciseRVModel.getExSets());
        Picasso.get().load(exerciseRVModel.getEximg()).into(holder.exerciseIV);

        setAnimation(holder.itemView,position);  //adding animation to recycler view
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            //onclick event
            @Override
            public void onClick(View view) {
                routineclickInterface.onRoutineClick(position);
            }
        });
    }
    private void setAnimation(View itemview, int position){

        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            itemview.setAnimation(animation);
            lastPos = position;
        }
    }
    //get number of items in the arraylist and recycler view
    @Override
    public int getItemCount() {
        return exerciseRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView exercisenameTV,noofsetsTV;
        private ImageView exerciseIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exercisenameTV = itemView.findViewById(R.id.TVexName);
            noofsetsTV = itemView.findViewById(R.id.TVsets);
            exerciseIV = itemView.findViewById(R.id.IVExercise);
        }
    }

    public interface routineClickInterface{
        void onRoutineClick(int position);
    }
}
