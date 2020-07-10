package com.example.gardenmate;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Upload implements Serializable {

    private String mKey;
    private String mName;
    private String mImageUrl;
    private String mNotes;
    private Boolean mIdentified;

    public Upload(){}

    public Upload(String name, String imageUrl)
    {
        if(name.trim().equals(""))
        {
            name = "Unknown";
        }
        mName = name;
        mImageUrl = imageUrl;
        mNotes = "";
        mIdentified = false;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    public Boolean getIdentified() {
        return mIdentified;
    }

    public void setIdentified(Boolean mIdentified) {
        this.mIdentified = mIdentified;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String mKey) {
        this.mKey = mKey;
    }
}
