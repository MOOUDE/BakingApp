package com.example.android.bakingapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class MakeDetailsFragment extends Fragment {
    private ArrayList<Step> steps;
    private ArrayList<Ingredient> ingredients;
    public final String STEPS_KEY = "StepsKey";
    public final String STEPS_KEY_2 = "StepsKey2";
    public final String INTEGRADINTS_KEY = "integradentsKey";


    public final String CLICKED_POSITION = "clickedPosition";
    public final String STEP_INDEX = "stepIndex";
    public final String TWO_PANE = "twoPane";
    public final String PLAY_STATUS = "playStatus";




    private PlayerView playerView;
    private SimpleExoPlayer player;
    private TextView details;
    private String mediaUrl;
    private MediaSession mediaSession;
    private final String TAG = getTag();
    private Context context;
    private Dialog dialog;
    private boolean fullScreen;
    private int mStepIndex;

    private Button nextBtn,previousBtn;
    private ImageView noVedioImage;
    private Boolean vedioExist;

    private boolean mTwoPane;

    private long position = 0 ;
    private final String POSITION = "position";
    boolean PlayStatus;


    public void setmStepIndex(int mStepIndex) {
        this.mStepIndex = mStepIndex;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public void setSIngrediants(ArrayList<Ingredient> ingrediants) {
        this.ingredients = ingrediants;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.details_layout_fragment , container , false);

            steps = getArguments().getParcelableArrayList(STEPS_KEY_2);
            ingredients = getArguments().getParcelableArrayList(INTEGRADINTS_KEY);


        if(getArguments().getBoolean(TWO_PANE)){
            mTwoPane = true;
        }else {
            mTwoPane = false;
        }
            if(!mTwoPane)
             mStepIndex = getArguments().getInt(CLICKED_POSITION);


            position = 0;
        if (savedInstanceState != null ){
            position = savedInstanceState.getLong(POSITION);
            mStepIndex = savedInstanceState.getInt(STEP_INDEX , 0);
            PlayStatus = (savedInstanceState.getBoolean(PLAY_STATUS));
        }else{
            PlayStatus = true;
        }

            Log.d("MakeDetails" , "size is "+steps.size());

            playerView = (PlayerView) rootView.findViewById(R.id.exoPlayer);
            details = (TextView) rootView.findViewById(R.id.detailsText);
            nextBtn = (Button) rootView.findViewById(R.id.nextbtn);
            previousBtn = (Button) rootView.findViewById(R.id.previousbtn);
            noVedioImage = (ImageView) rootView.findViewById(R.id.noVedioImage);

        final boolean finalPlayStatus = PlayStatus;
        nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext() , "to Next " , Toast.LENGTH_SHORT).show();
                    mStepIndex++;
                    Log.d("index" , "index is "+mStepIndex);
                    goChangeContent(getContext() , mStepIndex , finalPlayStatus);
                }
            });

            previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext() , "to previous " , Toast.LENGTH_SHORT).show();
                mStepIndex--;
                goChangeContent(getContext() , mStepIndex , finalPlayStatus);
                Log.d("index" , "index is "+mStepIndex);

            }
          });

            context = container.getContext();

            details.setText(steps.get(mStepIndex).getDescription());
            mediaUrl = steps.get(mStepIndex).getVideoURL();



            initializePlayer(mediaUrl, context, position , PlayStatus);




        return rootView;

    }

    /**************/

    public void goChangeContent(Context context , int index , boolean playStatus){

        if (index > steps.size()-1  || index < 0 )
            index = 0 ;
        mStepIndex = index;
        stopExoPlayer();
        details.setText(steps.get(index).getDescription());
        mediaUrl = steps.get(index).getVideoURL();

        initializePlayer(mediaUrl, context, position , playStatus);

    }



    private void openFullscreenDialog() {
        dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        dialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreen = true;
        dialog.show();
    }



    /**************/


    private void initializePlayer(String mediaUrl ,Context con, long pos , boolean playStatus) {
        if(player == null ) {

            if(mediaUrl.equals("") || TextUtils.isEmpty(mediaUrl)) {
                noVedioImage.setVisibility(View.VISIBLE);
                vedioExist = false;

            }else{
                vedioExist = true;
                noVedioImage.setVisibility(View.INVISIBLE);
                playerView.setVisibility(View.VISIBLE);

            }
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            playerView.setPlayer(player);

            Uri uri = Uri.parse(mediaUrl);

            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, false);
            player.seekTo(pos);
            player.setPlayWhenReady(playStatus);


            if (getActivity().getResources().getConfiguration().orientation ==

                    Configuration.ORIENTATION_LANDSCAPE) {
                try {
                    if (vedioExist && !mTwoPane && (player != null)) {
                        openFullscreenDialog();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }else {
            player.setPlayWhenReady(playStatus);
        }

    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void stopExoPlayer(){
if(player != null) {
    player.stop();
    player.release();
    player = null;
}
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        boolean status = false;
        if((player) != null) {
            position = player.getCurrentPosition();
            outState.putLong(POSITION, position);


            status = player.getPlayWhenReady();
            outState.putBoolean(PLAY_STATUS ,status);
            Log.d(".State" , "In state " + status);

            outState.putInt(STEP_INDEX , mStepIndex);
        }

        outState.putBoolean(PLAY_STATUS ,status);
        Log.d(".MakesaveInstace" , "In position " + position);
    }




    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(mediaUrl , getContext() , position , getPlayStatus());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            // initialize player
            initializePlayer(mediaUrl , getContext() , position , getPlayStatus());

        }
    }



    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
           stopExoPlayer();
        }

        if(dialog !=null){
            dialog.dismiss();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
          if (Util.SDK_INT > 23) {
            stopExoPlayer();
        }
        if(dialog !=null){
            dialog.dismiss();
        }

    }

    public void setPlayStatus(boolean PlayStatus) {
        this.PlayStatus = PlayStatus;
    }

    public boolean getPlayStatus() {
        return PlayStatus;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(player !=null){
            stopExoPlayer();
        }
    }
}

