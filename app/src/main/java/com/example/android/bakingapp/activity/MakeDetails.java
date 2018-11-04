package com.example.android.bakingapp.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.MakeDetailsFragment;
import com.example.android.bakingapp.model.Step;

public class MakeDetails extends AppCompatActivity {

    private Step step;
    public final String STEPS_KEY = "StepsKey";
    public final String STEPS_KEY_2 = "StepsKey2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_details);
        step = getIntent().getParcelableExtra(STEPS_KEY);

        MakeDetailsFragment makeDetailsFragment = new MakeDetailsFragment();



        Bundle bundle = new Bundle();
        bundle.putParcelable(STEPS_KEY_2 , step);
        makeDetailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();


        fragmentManager.beginTransaction().add(R.id.detailsFramgent , makeDetailsFragment).commit();



    }
}
