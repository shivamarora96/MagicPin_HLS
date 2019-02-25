package com.saproductions.magicpin_hls.utility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.PlayerView;
import com.saproductions.magicpin_hls.R;
import com.saproductions.magicpin_hls.model.Feeds;
import com.saproductions.magicpin_hls.other.Constants;

import java.util.ArrayList;

/**
 * Created by shivam on 23/2/19.
 */

public class RV_Adapter extends RecyclerView.Adapter<RV_ViewHolder> {

    private ArrayList<Feeds> mData;
    Context context;
    final int ANIM_DURATION = 500;
    boolean autoload = false;


    public RV_Adapter(ArrayList<Feeds> mData, Context context, boolean al) {
        this.mData = mData;
        this.context = context;
        this.autoload = al;
    }


    @Override
    public void onViewAttachedToWindow(RV_ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int pos = holder.getAdapterPosition();

        if (!autoload)
        holder.player.startVideo(Constants.OPTION_HLS,  mData.get(pos).getVideoLink());


    }

    @Override
    public void onViewDetachedFromWindow(RV_ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.player.stopVideo();
    }

    @Override
    public RV_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedlayout, parent, false);
        return new RV_ViewHolder(childView, context);

    }


    @Override
    public void onBindViewHolder(RV_ViewHolder holder, int position) {
        Feeds currentFeed = mData.get(position);

        setAnimation_Left_in(holder.itemView, position+1);

        //VideoPlayer videoPlayer = new VideoPlayer(context, holder.playerView);

        holder.username.setText(currentFeed.getUsername());
        holder.date.setText(currentFeed.getDate());
        holder.feed.setText(currentFeed.getFeeddata());
        holder.player = new VideoPlayer(context, holder.playerView, autoload);

        if(!autoload)
        holder.player.startVideo(Constants.OPTION_HLS, currentFeed.getVideoLink());

        if(position == 0)
            holder.player.startVideo(Constants.OPTION_HLS, currentFeed.getVideoLink());

    }

    private void setAnimation_Left_in(View view, int pos) {

        int animStyle1 = android.R.anim.slide_in_left;
        int animStyle2 = R.anim.slide_in_right;
        int style = animStyle1;

        if(pos%2==0)
            style = animStyle2;

        Animation anim = AnimationUtils.loadAnimation(context, style);// animation file

        anim.setDuration(ANIM_DURATION);
        view.startAnimation(anim);
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }
}
