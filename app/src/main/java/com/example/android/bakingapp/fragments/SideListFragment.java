package com.example.android.bakingapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activity.BakingMake;
import com.example.android.bakingapp.activity.MainActivity;
import com.example.android.bakingapp.adapter.StepsAdapter;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class SideListFragment extends Fragment {

    private RecyclerView stepRecyclerView;
    private ArrayList<Step> steps;
    public final String STEPS_KEY_2 = "StepsKey2";


    public onRecyclerItemClicked mCallBack;

    public interface onRecyclerItemClicked{
        public void onItemClicked(int position);
    }



    public SideListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.side_list_layout , container , false);

        steps = getArguments().getParcelableArrayList(STEPS_KEY_2);
        stepRecyclerView = (RecyclerView) rootView.findViewById(R.id.stepsRecyclerView);
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(  getActivity()));

        StepsAdapter.onStepClicked stepListener = new StepsAdapter.onStepClicked(){

            @Override
            public void onOneStepClicked(View v, int position) {

                Toast.makeText(container.getContext(), "Position "
                        + position, Toast.LENGTH_SHORT).show();
                Log.d(".side","clicked");
                mCallBack.onItemClicked(position);

            }
        };


        stepRecyclerView.setAdapter(new StepsAdapter( steps, rootView.getContext() , stepListener));



        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{

            mCallBack = (onRecyclerItemClicked) context;

        }catch (Exception e){
            Log.d(".BackingMake" , "error loading");
        }

    }
}
