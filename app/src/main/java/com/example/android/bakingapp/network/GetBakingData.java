package com.example.android.bakingapp.network;

import com.example.android.bakingapp.model.Baking;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetBakingData {
    @GET("baking.json")
    Call<ArrayList<Baking>> getBakings();

}
