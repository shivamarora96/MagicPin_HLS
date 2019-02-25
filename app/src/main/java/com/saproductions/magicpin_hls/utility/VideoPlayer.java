package com.saproductions.magicpin_hls.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.saproductions.magicpin_hls.other.Constants;

/**
 * Created by shivam on 23/2/19.
 */

public class VideoPlayer {

    private SimpleExoPlayer player;
    private Context context;
    private PlayerView playerview;
    private long playbackPosition ;
    private int currentWindow  ;
    private boolean playWhenReady ;
    private static DefaultBandwidthMeter bandwidthMeter;

    public VideoPlayer(Context c , PlayerView view, boolean playWhenReady){
        playerview = view;
        context = c;
        this.playWhenReady = playWhenReady;
        bandwidthMeter = new DefaultBandwidthMeter();
    }

    private void initializePlayer(String option, String uriPlay){

        TrackSelection.Factory trackSelection = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context),new DefaultTrackSelector(trackSelection), new DefaultLoadControl());
        playerview.setPlayer(player);

        player.setPlayWhenReady(this.playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        if(uriPlay == null)
            return;

        Uri uri = Uri.parse(uriPlay);

        MediaSource mediaSource = buildMediaSource(uri , option);

        if(mediaSource == null)
                return ;

        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri, String option) {

        DataSource.Factory manifestDataSourceFactory = new DefaultHttpDataSourceFactory("ua");

        if(option.equalsIgnoreCase(Constants.OPTION_HLS)){
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplmayer-magicpin")).createMediaSource(uri);
        }

        else if(option.equalsIgnoreCase(Constants.OPTION_EXTRACTOR))
            return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-magicpin")).createMediaSource(uri);


        return null;
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();

            player = null;
        }
    }

    public boolean startVideo(String option, String uri){
        //if (Util.SDK_INT > 23)
            initializePlayer(option, uri);
            Log.i("Start", "Initialize video called");
            return true;

    }


    public boolean stopVideo(){
       // if (Util.SDK_INT <= 23 )

            if(player==null)
                return false;

            player.stop(false);
            releasePlayer();
            return  true;


    }


}
