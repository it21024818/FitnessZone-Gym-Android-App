package com.example.supportservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedbackRVAdapter extends RecyclerView.Adapter<FeedbackRVAdapter.ViewHolder> {
    private ArrayList<FeedbackRVModal> feedbackRVModalArrayList;
    private Context context;
    int lastPos = -1;
    private FeedbackClickInterface feedbackClickInterface;

    public FeedbackRVAdapter(ArrayList<FeedbackRVModal> feedbackRVModalArrayList, Context context, FeedbackClickInterface feedbackClickInterface) {
        this.feedbackRVModalArrayList = feedbackRVModalArrayList;
        this.context = context;
        this.feedbackClickInterface = feedbackClickInterface;
    }

    @NonNull
    @Override
    public FeedbackRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedback_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull FeedbackRVAdapter.ViewHolder holder, int position) {
        FeedbackRVModal feedbackRVModal = feedbackRVModalArrayList.get(position);
        holder.feedbackNameTV.setText(feedbackRVModal.getFeedbackName());
        holder.feedbackEmailTV.setText(feedbackRVModal.getFeedbackEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackClickInterface.onFeedbackClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedbackRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView feedbackNameTV, feedbackEmailTV;
        private ImageView feedbackIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            feedbackNameTV = itemView.findViewById(R.id.idTVName);
            feedbackEmailTV = itemView.findViewById(R.id.idTVEmail);
            feedbackIV = itemView.findViewById(R.id.idIVFeedback);
        }
    }

    public interface FeedbackClickInterface{
        void onFeedbackClick(int position);
    }
}
