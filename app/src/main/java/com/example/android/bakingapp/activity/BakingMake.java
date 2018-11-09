package com.example.android.bakingapp.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.IngredientsAdapter;
import com.example.android.bakingapp.adapter.StepsAdapter;
import com.example.android.bakingapp.fragments.MakeDetailsFragment;
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
public final String CLICKED_POSITION = "clickedPosition";
public final String TWO_PANE = "twoPane";

private boolean mTwoPane;
private int clickedPosition;



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
        stepRecyclerView.setAdapter(new StepsAdapter( steps, getApplicationContext() , null));
*/


       if (findViewById(R.id.vertical_linear) != null){
           mTwoPane = true;
       }else {
           mTwoPane = false;
       }

       if(savedInstanceState == null){

           clickedPosition = getIntent().getIntExtra(CLICKED_POSITION , 0);
           steps = getIntent().getParcelableArrayListExtra(STEPS_KEY);
           ingredients = getIntent().getParcelableArrayListExtra(INTEGRADINTS_KEY);
           Log.d("MakeDetails" , "ing size is "+ingredients.size());



           SideListFragment sideList = new SideListFragment();

           Bundle bundle = new Bundle();
           bundle.putParcelableArrayList(STEPS_KEY_2 , steps);
           bundle.putParcelableArrayList(INTEGRADINTS_KEY , ingredients);

           sideList.setArguments(bundle);

           FragmentManager fragmentManager = getSupportFragmentManager();
           fragmentManager.beginTransaction().add(R.id.sideList , sideList).commit();

            if(mTwoPane) {
                MakeDetailsFragment makeDetailsFragment = new MakeDetailsFragment();

                Bundle bundle2 = new Bundle();

                Log.d("MakeDetails" , "step size is "+steps.size());
                bundle2.putParcelableArrayList(STEPS_KEY_2 , steps);
                bundle2.putParcelableArrayList(INTEGRADINTS_KEY , ingredients);

                bundle2.putInt(CLICKED_POSITION , clickedPosition);
                bundle2.putBoolean(TWO_PANE , mTwoPane);

                makeDetailsFragment.setArguments(bundle2);

                fragmentManager.beginTransaction().add(R.id.detailsFramgent,
                        makeDetailsFragment).commit();
            }

       }

    }



    @Override
    public void onItemClicked(int position) {
        Log.d(".BakingMake" , "Item Clicked msg from Baking Activity "+position);

        if(!mTwoPane) {
            Intent intent = new Intent(this, MakeDetails.class);
            intent.putExtra(STEPS_KEY, steps);
            intent.putExtra(INTEGRADINTS_KEY , ingredients);
            Log.d("Ingrediant" , "size is ing "+ingredients.size());

            intent.putExtra(CLICKED_POSITION, position);
            startActivity(intent);
        }else{

            MakeDetailsFragment makeDetailsFragment = new MakeDetailsFragment();
            makeDetailsFragment.setSteps(steps);

            Bundle bundle = new Bundle();
            bundle.putBoolean(TWO_PANE, mTwoPane);
            bundle.putParcelableArrayList(STEPS_KEY_2 , steps);
            bundle.putParcelableArrayList(INTEGRADINTS_KEY , ingredients);

            makeDetailsFragment.setArguments(bundle);
            makeDetailsFragment.setmStepIndex(position);
            Log.d(".Ingrediants" ,"size before send to fragment "+ingredients.size());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailsFramgent , makeDetailsFragment).commit();

        }
       }
    }

