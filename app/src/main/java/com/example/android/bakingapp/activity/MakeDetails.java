package com.example.android.bakingapp.activity;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.MakeDetailsFragment;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class MakeDetails extends AppCompatActivity {

    private ArrayList<Step> steps;
    public final String STEPS_KEY = "StepsKey";
    public final String STEPS_KEY_2 = "StepsKey2";
    public final String CLICKED_POSITION = "clickedPosition";
    private int clickedPosition;




    private FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_details);

        steps = getIntent().getParcelableArrayListExtra(STEPS_KEY);

        clickedPosition = getIntent().getIntExtra(CLICKED_POSITION , 0);


         fragmentManager = getSupportFragmentManager();

         if(savedInstanceState == null) {
             createFragment();
         }


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);


    }
    private void createFragment(){
        MakeDetailsFragment makeDetailsFragment = new MakeDetailsFragment();

        Bundle bundle = new Bundle();

        Log.d("MakeDetails" , "step size is "+steps.size());
        bundle.putParcelableArrayList(STEPS_KEY_2 , steps);
        bundle.putInt(CLICKED_POSITION , clickedPosition);
        makeDetailsFragment.setArguments(bundle);


        fragmentManager.beginTransaction().add(R.id.detailsFramgent , makeDetailsFragment).commit();

    }

}
