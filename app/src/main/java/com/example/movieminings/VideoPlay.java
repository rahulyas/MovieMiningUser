package com.example.movieminings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieminings.Service.floatingwidgetService;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class VideoPlay extends AppCompatActivity {

       Uri videoUri;
       PlayerView playerView;
       ExoPlayer exoPlayer ;
       ExtractorsFactory extractorsFactory;
       ImageView exo_floating_widget ;

    String playerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        setFullScreen();

        hideActionBar();
        playerView = findViewById(R.id.playerView);
        exo_floating_widget = findViewById(R.id.exo_floating_widget);


        Intent intent = getIntent();

        if(intent != null ){
            String uri_str = intent.getStringExtra("videoUri");
            videoUri = Uri.parse(uri_str);
        }


        exo_floating_widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exoPlayer.setPlayWhenReady(false);
                exoPlayer.release();
                Intent serviceintent = new Intent(VideoPlay.this,
                        floatingwidgetService.class);
                serviceintent.putExtra("videoUri", videoUri.toString());
                startService(serviceintent);
            }
        });


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelector trackSelector = new DefaultTrackSelector(this);

       // exoPlayer = PlayerFactory.newSimpleInstance(this,trackSelector);
        extractorsFactory = new DefaultExtractorsFactory();
        playVideo();


    }

    private void hideActionBar() {

        getSupportActionBar().hide();
    }

    private void setFullScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }



    private void playVideo(){

        try {
            //String playerInfo = Util.getUserAgent(this,"MovieMiningS");

            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this,playerInfo);
            //MediaSource mediaSource = new DefaultMediaSourceFactory(videoUri,dataSourceFactory,extractorsFactory,null,null);
//videoUri,dataSourceFactory,extractorsFactory,null,null
            playerView.setPlayer(exoPlayer);
            //exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.release();
    }
}
