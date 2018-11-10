package com.example.android.bakingapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.BakingAdapter;
import com.example.android.bakingapp.model.Baking;
import com.example.android.bakingapp.network.GetBakingData;
import com.example.android.bakingapp.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {
private ArrayList<Baking> bakings;
private RecyclerView bakingRecyclerView;
private final String INTEGRADINTS_KEY = "integradentsKey";
private final String STEPS_KEY = "StepsKey";

private final  int WIDE_SCREEN_SPAN_COUNT = 3;
private final  int LAND_SCAPE_SPAN_COUNT = 2;

private Button refreshBtn;

private boolean mWideScreen;

    @Nullable private SimpleIdellingResources mIdlingResource
            = new SimpleIdellingResources();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(findViewById(R.id.main_linear) != null) {
            mWideScreen = true;
        }else{
            mWideScreen = false;
        }
        refreshBtn = findViewById(R.id.refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh(v);
            }
        });

        if(!isConnected()){
            refreshBtn.setVisibility(View.VISIBLE);
        }


        callJson();


    }

    private void callJson(){

        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        GetBakingData bakingService = retrofit.create(GetBakingData.class);

        bakingService.getBakings().enqueue(new Callback<ArrayList<Baking>>() {
            @Override
            public void onResponse(Call<ArrayList<Baking>> call, Response<ArrayList<Baking>> response) {

                if(mIdlingResource != null) {
                    Log.d(".MainActvity", "Idling false");
                    mIdlingResource.setIdleState(false);
                }

                Log.d(".MainActvity", "Got Data Sucssfully");
                bakings = response.body();


                bakingRecyclerView = (RecyclerView) findViewById(R.id.backingRecycler);
                if(mWideScreen){
                    bakingRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this ,WIDE_SCREEN_SPAN_COUNT));


                }else if(isLnadScape()){
                    bakingRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this ,LAND_SCAPE_SPAN_COUNT));

                } else {
                    bakingRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
                BakingAdapter.BakingClickedListener listener = new BakingAdapter.BakingClickedListener() {
                    @Override
                    public void onBakingClick(int clicked_position) {

                  Toast.makeText(MainActivity.this, "Position "
                          + clicked_position, Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(MainActivity.this , com.example.android.bakingapp.activity.BakingMake.class);
                        intent.putParcelableArrayListExtra(STEPS_KEY ,bakings.get(clicked_position).getSteps());
                        Log.d(".Indgrediant" , "ingrediant Size" + bakings.get(clicked_position).getIngredients().size());
                        intent.putParcelableArrayListExtra(INTEGRADINTS_KEY , bakings.get(clicked_position).getIngredients());
                        startActivity(intent);
                    }
                };


                bakingRecyclerView.setAdapter(new BakingAdapter(MainActivity.this ,bakings ,listener));
                if(mIdlingResource != null)
                   mIdlingResource.setIdleState(true);
                    Log.d(".MainActvity", "Idling true");


            }

            @Override
            public void onFailure(Call<ArrayList<Baking>> call, Throwable t) {
                Log.d(".MainActvity", "No Data "+t.getMessage() );


            }
        });

    }
    private boolean isLnadScape(){
        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            return true;
        }
        return false;
    }

    public void refresh(View v){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            Log.d("creating" , "idel");
            mIdlingResource = new SimpleIdellingResources();
        }
        return mIdlingResource;
    }





}
