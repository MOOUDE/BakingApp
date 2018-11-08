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
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

 public class ListWidgetServics extends RemoteViewsService {

     private ArrayList<Step> mSteps;
     Step st;

     public ListWidgetServics() {
         Log.d(".List","setting msteps");

     }
     public ListWidgetServics(ArrayList<Step> steps) {
         this.mSteps = steps;


     }

     @Override
     public RemoteViewsFactory onGetViewFactory(Intent intent) {

         Log.d(".List","setting Parcelable ");


         return new WidegetListVewFactory(this.getApplicationContext(), mSteps);
     }
 }

 class WidegetListVewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Step>mSteps;


     public WidegetListVewFactory(Context context ,ArrayList<Step> steps){
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

        mSteps = BakingWedget.shareSteps();


        Log.d(".Test", "Data is chaingin : "+mSteps.size());


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d(".count","Counting");
        if(mSteps == null) return 0;
        return mSteps.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(mSteps == null || mSteps.size() == 0) return null;

        RemoteViews views = new RemoteViews(mContext.getPackageName() , R.layout.widget_row);

        Log.d(".Test", "Data is setting"+mSteps.get(position).getShortDescription());


        views.setTextViewText(R.id.stepNameWidget ,mSteps.get(position).getShortDescription());
        views.setTextViewText(R.id.stepDetailsWidget ,mSteps.get(position).getDescription());




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

