package com.example.testtvapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.testtvapp.Model.AppRepository;
import com.example.testtvapp.Model.Game;
import com.example.testtvapp.R;

import java.util.ArrayList;

import io.vov.vitamio.LibsChecker;

public class Screen3Activity extends AppCompatActivity {

    String video_url = "http://file2.video9.in/english/movie/2014/x-men-_days_of_future_past/X-Men-%20Days%20of%20Future%20Past%20Trailer%20-%20[Webmusic.IN].3gp";
    private String path = "";
    private VideoView mVideoView;
    private ProgressDialog progDailog;
    ProgressDialog progressDialog=null;
    static ImageView imageView;
    ProgressBar pbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        AppRepository.getUser(this, getApplicationContext(),"screen3",(status,user)->{
            if (status){

            }
        });
        imageView = findViewById(R.id.imagev);
        pbar = findViewById(R.id.pbar);
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

    public static void setMediaData(Context context, String type,String img,String orientation) {
        if (type != null){
            if (type.equals("image")){
                Glide.with(context).load(img).into(imageView);
            }
        }

    }
}