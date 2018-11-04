package com.example.android.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MakeDetailsFragment extends Fragment {
    private Step step;
    public final String STEPS_KEY = "StepsKey";
    public final String STEPS_KEY_2 = "StepsKey2";

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private TextView details;
    private String mediaUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.details_layout_fragment , container , false);

        step = getArguments().getParcelable(STEPS_KEY_2);
        playerView = (PlayerView) rootView.findViewById(R.id.exoPlayer);
        details = (TextView) rootView.findViewById(R.id.detailsText);

        details.setText(step.getDescription());
        mediaUrl  = step.getVideoURL();

        initializePlayer(mediaUrl);



        return rootView;

    }


    private void initializePlayer(String mediaUrl) {

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(true);
        Uri uri = Uri.parse(mediaUrl);

        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);


    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }
}
