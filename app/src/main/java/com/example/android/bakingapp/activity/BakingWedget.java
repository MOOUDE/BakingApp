package com.example.android.bakingapp.activity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.BakingAdapter;
import com.example.android.bakingapp.adapter.ListWidgetServics;
import com.example.android.bakingapp.adapter.WidgetService;
import com.example.android.bakingapp.model.Baking;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.network.GetBakingData;
import com.example.android.bakingapp.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWedget extends AppWidgetProvider {
    public static int POSITION_TO_FETCH;

    private static ArrayList<Baking> bakingsToDisplay;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId , ArrayList<Baking> bakings) {


        bakingsToDisplay = bakings;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_wedget);



        // to start service
        Intent getDataIntent = new Intent(context, WidgetService.class);
        getDataIntent.setAction(WidgetService.ACTION_GET_DATA);
        PendingIntent getDataPendingIntent = PendingIntent.getService(
                context,
                0,
                getDataIntent,
                PendingIntent.FLAG_UPDATE_CURRENT );
        Log.d(".ServiceWeget", "name is ");

        context.startService(getDataIntent);




        //////////
        // Get current width to decide on single plant vs garden grid view
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);


        if (bakings != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                POSITION_TO_FETCH = ThreadLocalRandom.current().nextInt(0, bakings.size() );
            }

            //  views.setTextViewText(R.id.bakeTitle, "Title : " + bakings.get(0).getName());
            RemoteViews rv = getListRemoteView(context, bakings.get(POSITION_TO_FETCH).getName());


            // Instruct the widget manager to update the widget
            // appWidgetManager.updateAppWidget(appWidgetId, views);

            appWidgetManager.updateAppWidget(appWidgetId, rv);

        }



    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        WidgetService.startActtionSendData(context);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId , bakingsToDisplay);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static void loadData(){


    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void updateBakingWidget(Context context ,
                                          AppWidgetManager appWidgetManager ,
                                          ArrayList<Baking> bakingsToUpdate,
                                          int [] appWidgetIds
                                         ){


        for (int i  = 0 ; i<appWidgetIds.length ;i++){
            Log.d(".size" , "size is "+bakingsToUpdate.size());
            updateAppWidget(context , appWidgetManager , appWidgetIds[i] , bakingsToUpdate);
        }

    }
    private static RemoteViews getListRemoteView(Context context ,String title) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_wedget);

    views.setTextViewText(R.id.bakeTitle ,title );
        Log.d(".listcall" ,"list call");



        // Set the PlantDetailActivity intent to launch when clicked
        Intent appIntent = new Intent(context, ListWidgetServics.class);
      //  appIntent.putExtra( "Steps", testStep);

        //appIntent.putParcelableArrayListExtra("Steps" , bakingsToDisplay.get(0).getSteps());
        PendingIntent appPendingIntent = PendingIntent.getActivity(context,
                0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setRemoteAdapter(R.id.widget_list, appIntent);

        views.setPendingIntentTemplate(R.id.widget_list, appPendingIntent);
        views.setOnClickFillInIntent(R.id.widget_list , appIntent);




        // Handle empty gardens
        views.setEmptyView(R.id.widget_list, R.id.empty_view);
        return views;


    }
    public static ArrayList<Ingredient> shareIngrident() {
        if (bakingsToDisplay != null) {
            return bakingsToDisplay.get(POSITION_TO_FETCH).getIngredients();
        }
        return bakingsToDisplay.get(0).getIngredients();
    }


}



