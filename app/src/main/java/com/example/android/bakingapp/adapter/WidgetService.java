package com.example.android.bakingapp.adapter;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activity.BakingWedget;
import com.example.android.bakingapp.activity.MainActivity;
import com.example.android.bakingapp.model.Baking;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.network.GetBakingData;
import com.example.android.bakingapp.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WidgetService extends IntentService {
static ArrayList<Baking> bakings;
boolean isUpdateAction;
    public static final String ACTION_GET_DATA =
            "com.example.android.mygarden.action.get_data";

    public static final String ACTION_SEND_DATA =
            "com.example.android.mygarden.action.send_data";


    public WidgetService() {
        super("WidgetSevice");

        Log.d(".ServiceWeget", "calling intent  ");

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(".ServiceWeget", "handel intent ");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_DATA.equals(action)) {
                Log.d(".ServiceWeget", "calling intent  ");
                isUpdateAction = false;
                getData(this ,isUpdateAction);

            }else if(ACTION_SEND_DATA.equals(action)){
                isUpdateAction = true;
                getData(this , isUpdateAction);
            }
        }
    }


    public static void startActtionGetData(Context context) {
            Intent intent = new Intent(context, WidgetService.class);
            intent.setAction(ACTION_GET_DATA);
        Log.d(".ServiceWeget", " Start service  ");
        context.startService(intent);
    }

    public static void startActtionSendData(Context context) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(ACTION_SEND_DATA);
        Log.d(".ServiceWeget", "Sending Data");
        context.startService(intent);
    }

    private void sendData(ArrayList<Baking>bakingsToSend){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this , BakingWedget.class));

        Log.d(".sendData" , "Data is refreshing");
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds , R.id.widget_list);

        BakingWedget.updateBakingWidget(this , appWidgetManager , bakingsToSend , appWidgetIds);
    }





    private void getData(final Context context , final boolean isUpdate) {


        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        final GetBakingData bakingService = retrofit.create(GetBakingData.class);

        bakingService.getBakings().enqueue(new Callback<ArrayList<Baking>>() {
            @Override
            public void onResponse(Call<ArrayList<Baking>> call, Response<ArrayList<Baking>> response) {
                Log.d(".ServiceWeget", "Got Data Sucssfully");
                bakings = response.body();
                response.body();
                Log.d(".ServiceWeget", "name Example :  " + bakings.get(BakingWedget.POSITION_TO_FETCH).getName());

                if(isUpdate){
                    sendData(bakings);
                }
                RemoteViews v = new RemoteViews(context.getPackageName(), R.layout.baking_wedget);
                v.setTextViewText(R.id.bakeTitle, "" + bakings.get(BakingWedget.POSITION_TO_FETCH).getName());


            }

            @Override
            public void onFailure(Call<ArrayList<Baking>> call, Throwable t) {
                Log.d(".ServiceWeget", "no Data "+t.getMessage());

            }
        });
    }




}




