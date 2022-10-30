package com.example.supportservice;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedbackRVModal implements Parcelable {
    private String feedbackName;
    private String feedbackEmail;
    private String feedbackRegNo;
    private String feedbackConNo;
    private String feedbackMessage;
    private String feedbackID;

    public FeedbackRVModal() {

    }

    public FeedbackRVModal(String feedbackName, String feedbackEmail, String feedbackRegNo, String feedbackConNo, String feedbackMessage, String feedbackID) {
        this.feedbackName = feedbackName;
        this.feedbackEmail = feedbackEmail;
        this.feedbackRegNo = feedbackRegNo;
        this.feedbackConNo = feedbackConNo;
        this.feedbackMessage = feedbackMessage;
        this.feedbackID = feedbackID;
    }

    protected FeedbackRVModal(Parcel in) {
        feedbackName = in.readString();
        feedbackEmail = in.readString();
        feedbackRegNo = in.readString();
        feedbackConNo = in.readString();
        feedbackMessage = in.readString();
        feedbackID = in.readString();
    }

    public static final Creator<FeedbackRVModal> CREATOR = new Creator<FeedbackRVModal>() {
        @Override
        public FeedbackRVModal createFromParcel(Parcel in) {
            return new FeedbackRVModal(in);
        }

        @Override
        public FeedbackRVModal[] newArray(int size) {
            return new FeedbackRVModal[size];
        }
    };

    public String getFeedbackName() {
        return feedbackName;
    }

    public void setFeedbackName(String feedbackName) {
        this.feedbackName = feedbackName;
    }

    public String getFeedbackEmail() {
        return feedbackEmail;
    }

    public void setFeedbackEmail(String feedbackEmail) {
        this.feedbackEmail = feedbackEmail;
    }

    public String getFeedbackRegNo() {
        return feedbackRegNo;
    }

    public void setFeedbackRegNo(String feedbackRegNo) {
        this.feedbackRegNo = feedbackRegNo;
    }

    public String getFeedbackConNo() {
        return feedbackConNo;
    }

    public void setFeedbackConNo(String feedbackConNo) {
        this.feedbackConNo = feedbackConNo;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(feedbackName);
        parcel.writeString(feedbackEmail);
        parcel.writeString(feedbackRegNo);
        parcel.writeString(feedbackConNo);
        parcel.writeString(feedbackMessage);
        parcel.writeString(feedbackID);
    }
}