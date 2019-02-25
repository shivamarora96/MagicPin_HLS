package com.saproductions.magicpin_hls.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.saproductions.magicpin_hls.R;
import com.saproductions.magicpin_hls.model.Feeds;
import com.saproductions.magicpin_hls.other.Constants;
import com.saproductions.magicpin_hls.utility.RV_Adapter;
import com.saproductions.magicpin_hls.utility.RV_ViewHolder;
import com.saproductions.magicpin_hls.utility.VideoPlayer;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    boolean autoload = false;
    RV_Adapter adapter;

    private VideoPlayer currentPlayer, oldPlayer;
    private ArrayList<Feeds> feed;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Magicpin Feeds");

        autoload = getIntent().getBooleanExtra("AL", false);

      feed = new ArrayList<Feeds>();

        Feeds f1 = new Feeds("https://player.vimeo.com/external/286837767.m3u8?s=42570e8c4a91b98cdec7e7bfdf0eccf54e700b69");
        Feeds f2 = new Feeds("https://player.vimeo.com/external/286837810.m3u8?s=610b4fee49a71c2dbf22c01752372ff1c6459b9e");
        Feeds f3 = new Feeds("https://player.vimeo.com/external/286837723.m3u8?s=3df60d3c1c6c7a11df4047af99c5e05cc2e7ae96");
        Feeds f4 = new Feeds("https://player.vimeo.com/external/286837649.m3u8?s=9e486e9b932be72a8875afc6eaae21bab124a35a");
        Feeds f5 = new Feeds("https://player.vimeo.com/external/286837529.m3u8?s=20f83af6ea8fbfc8ce0c2001f32bf037f8b0f65f");
        Feeds f6 = new Feeds("https://player.vimeo.com/external/286837402.m3u8?s=7e01c398e2f01c29ecbd46e5e2dd53e0d6c1905d");

        feed.add(f1);
        feed.add(f2);
        feed.add(f3);
        feed.add(f4);
        feed.add(f5);
        feed.add(f6);



    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView  = (RecyclerView)findViewById(R.id.rv);
        adapter = new RV_Adapter(feed, HomeActivity.this, autoload);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewCacheSize(5);

        try{


            if(autoload) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        Log.i("SCROLL:", dx + "::" + dy);

                        if (Math.abs(dy) >= 7) {


                            int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                            Log.i("POS", position + "");
                            if (position >= 0) {
                                if (oldPlayer != null) {

                                    RV_ViewHolder holder = (RV_ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

                                    holder.player.stopVideo();
                                    oldPlayer.stopVideo();

                                    for (int i = 0; i < feed.size(); i++) {

                                        if (i != position) {
                                            RV_ViewHolder hol = (RV_ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                                            if (hol != null)
                                                hol.player.stopVideo();
                                        }

                                    }


                                }

                                RV_ViewHolder holder = (RV_ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                                currentPlayer = holder.player;
                                currentPlayer.startVideo(Constants.OPTION_HLS, feed.get(position).getVideoLink());
                                oldPlayer = currentPlayer;


                            }


                        }

                    }


                });

            }


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try{

            for(int i = 0 ; i<feed.size(); i++){
                RV_ViewHolder hol = (RV_ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                if (hol != null)
                    hol.player.stopVideo();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }




    }
}
