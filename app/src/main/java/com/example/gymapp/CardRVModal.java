package com.example.gymapp;

import android.os.Parcel;
import android.os.Parcelable;

public class CardRVModal implements Parcelable {
    private String cardType;
    private String cardName;
    private String cardNum;
    private String cardDate;
    private String secCode;
    private String cardID;



    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public CardRVModal(){

    }

    protected CardRVModal(Parcel in) {
        cardType = in.readString();
        cardName = in.readString();
        cardNum = in.readString();
        cardDate = in.readString();
        secCode = in.readString();
        cardID = in.readString();
    }

    public static final Creator<CardRVModal> CREATOR = new Creator<CardRVModal>() {
        @Override
        public CardRVModal createFromParcel(Parcel in) {
            return new CardRVModal(in);
        }

        @Override
        public CardRVModal[] newArray(int i) {
            return new CardRVModal[i];
        }
    };



    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }


    public CardRVModal(String cardId, String cardType, String cardName, String cardNum, String cardDate, String secCode) {
        this.cardType= cardType;
        this.cardName= cardName;
        this.cardNum= cardNum;
        this.cardDate= cardDate;
        this.secCode= secCode;
        this.cardID= cardId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardType);
        dest.writeString(cardName);
        dest.writeString(cardNum);
        dest.writeString(cardDate);
        dest.writeString(secCode);
        dest.writeString(cardID);

    }
}
