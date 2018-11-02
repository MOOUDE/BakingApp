package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BakingList {
    @SerializedName("BakingList")
    private ArrayList<Baking> BakingList;

    public ArrayList<Baking> getBakings() {
        return BakingList;
    }

    public void setBakings(ArrayList<Baking> bakings) {
        this.BakingList = bakings;
    }
}

