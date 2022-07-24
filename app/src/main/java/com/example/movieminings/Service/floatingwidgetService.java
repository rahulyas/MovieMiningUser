package com.example.movieminings.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class floatingwidgetService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
   /* public floatingwidgetService() {
    }
    WindowManager mwindowManager ;
    private View mFloatingWidget;
    Uri videoUri;
    SimpleExoPlayer exoPlayer;
    PlayerView playerView;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null){
            String uriStr = intent.getStringExtra("videoUri");
            videoUri = Uri.parse(uriStr);
            if(mwindowManager != null  && mFloatingWidget.isShown() && exoPlayer != null){
                mwindowManager.removeView(mFloatingWidget);
                mFloatingWidget = null;
                mwindowManager = null;
                exoPlayer.setPlayWhenReady(false);
                exoPlayer.release();
                exoPlayer = null;
            }
            final  WindowManager.LayoutParams params;
            mFloatingWidget = LayoutInflater.from(this).inflate(R.layout.custom_pop_up_window,null);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                params = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
            }else {
                params = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);


            }
            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.x =200;
            params.y = 200;

            mwindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
            mwindowManager.addView(mFloatingWidget,params);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this,trackSelector);
            playerView = mFloatingWidget.findViewById(R.id.playerView);

            ImageView imageViewclose = mFloatingWidget.findViewById(R.id.imageViewDissmiss);

            ImageView imageViewMaximize = mFloatingWidget.findViewById(R.id.imageViewMaximize);

            imageViewMaximize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mwindowManager != null && mFloatingWidget.isShown() && exoPlayer != null)
                    {
                        mwindowManager.removeView(mFloatingWidget);
                        mFloatingWidget = null;
                        mwindowManager = null;
                        exoPlayer.setPlayWhenReady(false);
                        exoPlayer.release();
                        exoPlayer = null;
                        stopSelf();
                        Intent openActivtyIntent = new Intent(floatingwidgetService.this,
                                VideoPlay.class);
                        openActivtyIntent.putExtra("videoUri",videoUri.toString());
                        openActivtyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(openActivtyIntent);
                    }

                }
            });

            imageViewclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mwindowManager != null && mFloatingWidget.isShown() && exoPlayer != null)
                    {
                        mwindowManager.removeView(mFloatingWidget);
                        mFloatingWidget = null;
                        mwindowManager = null;
                        exoPlayer.setPlayWhenReady(false);
                        exoPlayer.release();
                        exoPlayer = null;
                        stopSelf();

                    }

                }
            });
            playVideos();

            mFloatingWidget.findViewById(R.id.relativeLayoutpopup).setOnTouchListener(new View.OnTouchListener() {

                private int initialX,initialY ;
                private float initialTouchX,initialTouchY;
                @Override
                public boolean onTouch(View view, MotionEvent event) {

                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            initialX =params.x;
                            initialY =params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            return true;

                        case MotionEvent.ACTION_UP:
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int)(event.getRawX()-initialTouchX);
                            params.y = initialY + (int)(event.getRawY()-initialTouchY);
                            mwindowManager.updateViewLayout(mFloatingWidget,params);
                            return true;
                    }
                    return false;
                }
            });

        }

        return super.onStartCommand(intent, flags, startId);
    }
    public void playVideos(){

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector
                    (new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(floatingwidgetService.this,trackSelector);


            String playerInfo = Util.getUserAgent(this,"VideoPlayer");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this,playerInfo);

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                    dataSourceFactory,extractorsFactory,null,null);
            playerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mFloatingWidget != null)
            mwindowManager.removeView(mFloatingWidget);


    }
*/
}
