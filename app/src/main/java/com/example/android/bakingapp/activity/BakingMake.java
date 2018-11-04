package com.example.android.bakingapp.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.StepsAdapter;
import com.example.android.bakingapp.fragments.SideListFragment;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class BakingMake extends AppCompatActivity implements SideListFragment.onRecyclerItemClicked{
private RecyclerView ingredientsRecycler;
private ArrayList<Ingredient> ingredients;

private RecyclerView stepRecyclerView;
public ArrayList<Step> steps;
public final String STEPS_KEY = "StepsKey";
public final String STEPS_KEY_2 = "StepsKey2";


    public final String INTEGRADINTS_KEY = "integradentsKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baking_activity);

        /*
        ingredients = getIntent().getParcelableArrayListExtra(INTEGRADINTS_KEY);

        ingredientsRecycler = (RecyclerView) findViewById(R.id.ingradentsRecycler);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(BakingMake.this));
        ingredientsRecycler.setAdapter(new IngredientsAdapter(ingredients, this));

       */

       /*

        stepRecyclerView = (RecyclerView) findViewById(R.id.stepsRecyclerView);
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(BakingMake.this));
        stepRecyclerView.setAdapter(new StepsAdapter( steps, this));
        */
        steps = getIntent().getParcelableArrayListExtra(STEPS_KEY);

        SideListFragment sideList = new SideListFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS_KEY_2 , steps);
        sideList.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();


        fragmentManager.beginTransaction().add(R.id.sideList , sideList).commit();


    }


    @Override
    public void onItemClicked(int position) {
        Log.d(".BakingMake" , "Item Clicked msg from Baking Activity "+position);



        Intent intent = new Intent(this , MakeDetails.class);
        intent.putExtra(STEPS_KEY ,steps.get(position));
        startActivity(intent);


    }
}
