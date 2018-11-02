package com.example.android.bakingapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.BakingAdapter;
import com.example.android.bakingapp.model.Baking;
import com.example.android.bakingapp.network.GetBakingData;
import com.example.android.bakingapp.network.RetrofitInstance;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
private ArrayList<Baking> bakings;
private RecyclerView bakingRecyclerView;
private final String INTEGRADINTS_KEY = "integradentsKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callJson();


    }

    private void callJson(){

        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        GetBakingData bakingService = retrofit.create(GetBakingData.class);

        bakingService.getBakings().enqueue(new Callback<ArrayList<Baking>>() {
            @Override
            public void onResponse(Call<ArrayList<Baking>> call, Response<ArrayList<Baking>> response) {
                Log.d(".MainActvity", "Got Data Sucssfully");
                bakings = response.body();


                bakingRecyclerView = (RecyclerView) findViewById(R.id.backingRecycler);
                bakingRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                BakingAdapter.BakingClickedListener listener = new BakingAdapter.BakingClickedListener() {
                    @Override
                    public void onBakingClick(int clicked_position) {

                  Toast.makeText(MainActivity.this, "Position "
                          + clicked_position, Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(MainActivity.this , com.example.android.bakingapp.activity.BakingMake.class);
                        intent.putParcelableArrayListExtra(INTEGRADINTS_KEY ,bakings.get(clicked_position).getIngredients());
                        startActivity(intent);
                    }
                };


                bakingRecyclerView.setAdapter(new BakingAdapter(MainActivity.this ,bakings ,listener));


            }

            @Override
            public void onFailure(Call<ArrayList<Baking>> call, Throwable t) {
                Log.d(".MainActvity", "No Data "+t.getMessage() );

            }
        });
    }




}
