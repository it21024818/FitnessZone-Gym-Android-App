package com.example.gymapp;

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

public class CardRVAdapter extends RecyclerView.Adapter<CardRVAdapter.ViewHolder> {
    private ArrayList<CardRVModal> cardRVModalArrayList;
    private Context context;
    private CardClickInterface cardClickInterface;
    int lastPos = -1;

    public CardRVAdapter(ArrayList<CardRVModal> cardRVModalArrayList, Context context, CardClickInterface cardClickInterface) {
        this.cardRVModalArrayList = cardRVModalArrayList;
        this.context = context;
        this.cardClickInterface = cardClickInterface;
    }

    @NonNull
    @Override
    public CardRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_rv_item, parent, false);
        return new CardRVAdapter.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CardRVAdapter.ViewHolder holder, int position) {

        // setting data to our recycler view item on below line.
        CardRVModal cardRVModal = cardRVModalArrayList.get(position);
        holder.cardTV.setText(cardRVModal.getCardName());
        holder.cardNumTV.setText("Card Number " + cardRVModal.getCardNum());
        //Picasso.get().load(cardRVModal.getCardImg()).into(holder.cardIV);
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.cardIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardClickInterface.onCardClick(position);
            }
        });

    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return cardRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView cardIV;
        private TextView cardTV, cardNumTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            cardIV = itemView.findViewById(R.id.idIVCard);
            cardTV = itemView.findViewById(R.id.idTVCardName);
            cardNumTV = itemView.findViewById(R.id.idTVCardNum);
        }
    }

    // creating a interface for on click
    public interface CardClickInterface {
        void onCardClick(int position);
    }
}
