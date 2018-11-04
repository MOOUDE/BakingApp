package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Baking;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;


import java.util.ArrayList;
import java.util.zip.Inflater;

public class BakingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
private Context context;
private ArrayList<Baking> bakings;
private String bakingImageUrl,bakingFullName;
final private BakingClickedListener bakingClickedListener;

    public BakingAdapter(Context context, ArrayList<Baking> bakings , BakingClickedListener bakingClickedListener) {
        this.context = context;
        this.bakings = bakings;
        this.bakingClickedListener = bakingClickedListener;
    }

    public interface BakingClickedListener {

        void onBakingClick(int clicked_position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.baking_layout,viewGroup , false);
        Item item = new Item(view , bakingClickedListener);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        bakingFullName = bakings.get(i).getName();
        bakingImageUrl = bakings.get(i).getImage();


        ((Item)viewHolder).bakingName.setText(bakingFullName);


        Glide.with(context).load("https://images.pexels.com/" +
                "photos/242429/pexels-photo-242429.jpeg?auto=compress&" +
                "cs=tinysrgb&h=350").into(((Item)viewHolder).bakingImage);


    }

    @Override
    public int getItemCount() {
        return bakings.size();
    }
public class Item extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView bakingImage;
    private TextView bakingName;
    private BakingClickedListener mListner;

        public Item(@NonNull View itemView , BakingClickedListener bListner) {
            super(itemView);
            bakingImage = itemView.findViewById(R.id.backingImage);
            bakingName  = itemView.findViewById(R.id.backingName);
            mListner = bListner;
            itemView.setOnClickListener(this);

        }

    @Override
    public void onClick(View view) {
        int click = getAdapterPosition();
        mListner.onBakingClick(click);

    }
}
}
