package com.example.dklabapp;

import android.provider.ContactsContract;

public class Instrument {
    private String name;
    private int instrumentID;
    private String description;
    private String website;
    //private String[] youtubeTutorials;
    private boolean avaliable;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(int instrumentID) {
        this.instrumentID = instrumentID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    /*public String[] getYoutubeTutorials() {
        return youtubeTutorials;
    }

    public void setYoutubeTutorials(String[] youtubeTutorials) {
        this.youtubeTutorials = youtubeTutorials;
    } */
    public boolean isAvaliable() {
        return avaliable;
    }
    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }

    public Instrument(String name, int instrumentID, String description, String website, boolean avaliable) { //String[] youtubeTutorials,
        this.name = name;
        this.instrumentID = instrumentID;
        this.description = description;
        this.website = website;
        this.avaliable = avaliable;
        //this.youtubeTutorials = youtubeTutorials;
    }

    public Instrument() {
        this.name = "";
        this.instrumentID = 0;
        this.description = "";
        this.website = "";
        this.avaliable = true;
    }
}
