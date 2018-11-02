package com.example.android.bakingapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.IngredientsAdapter;
import com.example.android.bakingapp.model.Ingredient;

import java.util.ArrayList;

public class BakingMake extends AppCompatActivity {
private RecyclerView ingredientsRecycler;
private ArrayList<Ingredient> ingredients;
    public final String INTEGRADINTS_KEY = "integradentsKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_make);

        ingredients = getIntent().getParcelableArrayListExtra(INTEGRADINTS_KEY);

        ingredientsRecycler = (RecyclerView) findViewById(R.id.ingradentsRecycler);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(BakingMake.this));
        ingredientsRecycler.setAdapter(new IngredientsAdapter(ingredients, this));

    }


}
