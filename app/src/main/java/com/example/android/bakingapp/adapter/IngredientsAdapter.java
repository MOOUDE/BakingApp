package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
private ArrayList<Ingredient> ingredient;
private Context context;
private String measureText,ingredientText;
private double quanitiyText;


    public IngredientsAdapter(ArrayList<Ingredient> ingredient, Context context) {
        this.ingredient = ingredient;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ingradent_layout , viewGroup , false);
        IngredientItem item = new IngredientItem(view);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        measureText = ingredient.get(i).getMeasure();
        quanitiyText = ingredient.get(i).getQuantity();
        ingredientText = ingredient.get(i).getIngredient();

        Log.d(".Baking" , "Size is "+ingredient.size());
        ((IngredientItem)viewHolder).ingredient.setText(ingredientText);
        ((IngredientItem)viewHolder).measure.setText(measureText);
        ((IngredientItem)viewHolder).quantity.setText(String.valueOf(quanitiyText));

    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }
    class IngredientItem extends RecyclerView.ViewHolder{
        private TextView quantity;
        private TextView measure,ingredient;

        public IngredientItem(@NonNull View itemView) {
            super(itemView);

            quantity = (TextView)itemView.findViewById(R.id.quantity);
            measure = (TextView)itemView.findViewById(R.id.measure);
            ingredient = (TextView)itemView.findViewById(R.id.ingredient);


        }
    }
}
