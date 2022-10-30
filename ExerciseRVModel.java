package com.example.fitnessapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseRVModel implements Parcelable {
    //variable declaration
    private String exId;
    private String exName;
    private String exArea;
    private String exSets;
    private String exduration;
    private String eximg;
    private String specNotes;

    //parameterized constructor
    public ExerciseRVModel(String exName, String exArea, String exSets, String exduration, String eximg, String specNotes,String exId) {
        this.exName = exName;
        this.exArea = exArea;
        this.exSets = exSets;
        this.exduration = exduration;
        this.eximg = eximg;
        this.specNotes = specNotes;
        this.exId = exId;
    }

    protected ExerciseRVModel(Parcel in) {
        exId = in.readString();
        exName = in.readString();
        exArea = in.readString();
        exSets = in.readString();
        exduration = in.readString();
        eximg = in.readString();
        specNotes = in.readString();
    }

    public static final Creator<ExerciseRVModel> CREATOR = new Creator<ExerciseRVModel>() {
        @Override
        public ExerciseRVModel createFromParcel(Parcel in) {
            return new ExerciseRVModel(in);
        }

        @Override
        public ExerciseRVModel[] newArray(int size) {
            return new ExerciseRVModel[size];
        }
    };

    //setters and getters for variables

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getExArea() {
        return exArea;
    }

    public String getExId() {
        return exId;
    }

    public void setExId(String exId) {
        this.exId = exId;
    }

    public void setExArea(String exArea)
    {
        this.exArea = exArea;
    }

    public String getExSets() {
        return exSets;
    }

    public void setExSets(String exSets) {
        this.exSets = exSets;
    }

    public String getExduration() {
        return exduration;
    }

    public void setExduration(String exduration) {
        this.exduration = exduration;
    }

    public String getEximg() {
        return eximg;
    }

    public void setEximg(String eximg) {
        this.eximg = eximg;
    }

    public String getSpecNotes() {
        return specNotes;
    }

    public void setSpecNotes(String specNotes) {
        this.specNotes = specNotes;
    }
    //empty constructor
    public ExerciseRVModel(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(exName);
        parcel.writeString(exArea);
        parcel.writeString(exSets);
        parcel.writeString(exduration);
        parcel.writeString(eximg);
        parcel.writeString(specNotes);
        parcel.writeString(exId);
    }
}
