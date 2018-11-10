package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activity.BakingWedget;
import com.example.android.bakingapp.model.Baking;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

 public class ListWidgetServics extends RemoteViewsService {

     private ArrayList<Ingredient> mIngredinants;
     Step st;

     public ListWidgetServics() {
         Log.d(".List","setting msteps");

     }
     public ListWidgetServics(ArrayList<Ingredient> ingredients) {
         this.mIngredinants = ingredients;



     }

     @Override
     public RemoteViewsFactory onGetViewFactory(Intent intent) {

         Log.d(".List","setting Parcelable ");


         return new WidegetListVewFactory(this.getApplicationContext(), mIngredinants);
     }
 }

 class WidegetListVewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Ingredient>mIngrediant;


     public WidegetListVewFactory(Context context ,ArrayList<Ingredient> ingredients){
        Log.d(".Test", "Data is call");
        this.mContext = context;


    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mIngrediant = BakingWedget.shareIngrident();


        Log.d(".Test", "Data is chaingin : "+mIngrediant.size());


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d(".count","Counting");
        if(mIngrediant == null) return 0;
        return mIngrediant.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(mIngrediant == null || mIngrediant.size() == 0) return null;

        RemoteViews views = new RemoteViews(mContext.getPackageName() , R.layout.widget_row);

        Log.d(".Test", "Data is setting"+mIngrediant.get(position).getIngredient());


        views.setTextViewText(R.id.IngrediantName ,mIngrediant.get(position).getIngredient());
        views.setTextViewText(R.id.ingrediantMuch ,mIngrediant.get(position).getQuantity().toString());
        views.setTextViewText(R.id.ingrediantUnit ,mIngrediant.get(position).getMeasure());




        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}

