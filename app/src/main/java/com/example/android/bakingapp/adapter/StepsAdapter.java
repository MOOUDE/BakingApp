package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Step> Steps;
    private Context context;
    private String shortDescription;
    private String stepThumbnil;
    private String description;
    final private onStepClicked onStepCallback;


    public interface onStepClicked{
        void onOneStepClicked(View v ,int position);
    }
    public StepsAdapter(ArrayList<Step> Steps, Context context , onStepClicked onStepCallback) {
        this.Steps = Steps;
        this.context = context;
        this.onStepCallback = onStepCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.step_layout, viewGroup , false);
        StepItem item = new StepItem(view ,onStepCallback);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {



        shortDescription = Steps.get(i).getShortDescription();
        stepThumbnil = Steps.get(i).getThumbnailURL();
        description = Steps.get(i).getDescription();

        Log.d(".StepAdapter" , "Size is "+Steps.size());

        ((StepItem)viewHolder).shortDescription.setText(shortDescription);
        ((StepItem)viewHolder).description.setText(description);

        if (stepThumbnil != null && !stepThumbnil.equals("") && !(TextUtils.isEmpty(stepThumbnil))) {
            Glide.with(context).load(stepThumbnil).into(
                    ((StepItem)viewHolder).stepThumbNil);
            Log.d(".StepsAdapter" , "ImageUrl" + stepThumbnil);
        }else{
            Log.d(".StepsAdapter" , "Null image " + stepThumbnil);

            Glide.with(context).load(this.context.getDrawable(R.drawable.bakewell_tarts)).into(
                    ((StepItem)viewHolder).stepThumbNil);

        }


    }

    @Override
    public int getItemCount() {
        return Steps.size();
    }
    class StepItem extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView shortDescription;
        private TextView description;
        private ImageView stepThumbNil;
        private onStepClicked onStepclicked;

        public StepItem(@NonNull View itemView , onStepClicked onStepclicked) {
            super(itemView);

            shortDescription = (TextView)itemView.findViewById(R.id.shortDescription);
            description = (TextView)itemView.findViewById(R.id.description);
            stepThumbNil = (ImageView) itemView.findViewById(R.id.stepThumbnil);
            this.onStepclicked = onStepclicked;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int click = getAdapterPosition();
            onStepclicked.onOneStepClicked(v,click);
        }
    }


}
