package com.saproductions.magicpin_hls.utility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.saproductions.magicpin_hls.R;

/**
 * Created by shivam on 23/2/19.
 */

public class RV_ViewHolder extends RecyclerView.ViewHolder {

    public PlayerView playerView;
    public TextView username;
    public TextView date;
    public TextView feed;
    public VideoPlayer player;

    public RV_ViewHolder(View itemView, Context context) {
        super(itemView);

        playerView = (PlayerView)itemView.findViewById(R.id.feedplayer);
        username = (TextView)itemView.findViewById(R.id.feedusername);
        date = (TextView)itemView.findViewById(R.id.feeddate);
        feed = (TextView)itemView.findViewById(R.id.feedDescription);
    }


}
