package com.app.beyondlottotv.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.app.beyondlottotv.Model.AppRepository;
import com.app.beyondlottotv.R;

public class Screen3Activity extends AppCompatActivity {

    String video_url = "http://file2.video9.in/english/movie/2014/x-men-_days_of_future_past/X-Men-%20Days%20of%20Future%20Past%20Trailer%20-%20[Webmusic.IN].3gp";
    private String path = "";
    private VideoView mVideoView;
    private ProgressDialog progDailog;
    ProgressDialog progressDialog=null;
    ImageView imageView;
    VideoView videoView;
    ProgressBar pbar;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        init();
        keepScreenAwake();
        getSetUserData();

//        mVideoView = (VideoView) findViewById(R.id.video);
//        path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//        if (path == "") {
//
//            // Tell the user to provide a media file URL/path.
//            Toast.makeText(Screen3Activity.this, "Please edit VideoViewDemo                Activity, and set path" +
//                    " variable to your media file URL/path", Toast.LENGTH_LONG).show();
//            return;
//        } else {
//            /*
//             * Alternatively,for streaming media you can use
//             * mVideoView.setVideoURI(Uri.parse(URLstring));
//             */
//            mVideoView.setVideoPath(path);
//         //   mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
//            mVideoView.setMediaController(new MediaController(this));
//            mVideoView.requestFocus();
//
//            progDailog = ProgressDialog.show(this, "Please wait ...",
//                    "Retrieving data ...", true);
//            progDailog.setCancelable(true);
//
//
//            mVideoView.setOnPreparedListener(
//                    new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mediaPlayer) {
//                            // optional need Vitamio 4.0
//                            //mediaPlayer.setPlaybackSpeed(1.0f);
//                            progDailog.dismiss();
//                        }
//                    });
//
//
//            //mediaPlayer.setPlaybackSpeed(1.0f);
 //       }

    }
//    @Override
//    protected void onPause() {
//        mVideoView.pause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        mVideoView.resume();
//        progDailog.show();
//        super.onResume();
//    }

    private void init(){
        videoView = (VideoView) findViewById(R.id.myvideoview);
        imageView = findViewById(R.id.imagev);
        pbar = findViewById(R.id.pbar);
    }

    private void getSetUserData(){
        Log.d("Screen3Activity", "getSetUserData: ");
        AppRepository.getUser(this, getApplicationContext(),"screen3",(status,user)->{
            if (status){
                AppRepository.getGamesofUser(this, user, getApplicationContext(), "screen3",(type,img)->{
                    Log.d("Screen3Activity", "getGamesofUser: ");
                    this.runOnUiThread(() -> {
                        if (type != null && img != null){
                            setMediaData(Screen3Activity.this, type, img, "");
                        }else {
                            Toast.makeText(Screen3Activity.this, "No Data found!", Toast.LENGTH_SHORT).show();
                            pbar.setVisibility(View.GONE);
                        }
                    });
                });
            }
        });
    }


    public void setMediaData(Context context, String type,String img,String orientation) {
        pbar.setVisibility(View.GONE);
        if (type != null){
            if (type.equals("image")){
                imageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(img).into(imageView);
            }else if (type.equals("video")) {
                videoView.setVisibility(View.VISIBLE);
                String LINK = img;
                MediaController mc = new MediaController(context);

                mc.setAnchorView(videoView);
                mc.setMediaPlayer(videoView);
                Uri video = Uri.parse(LINK);
                videoView.setMediaController(mc);
                videoView.setVideoURI(video);
                videoView.start();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                    }
                });
            }
        }

    }

    public void keepScreenAwake(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();
    }
}