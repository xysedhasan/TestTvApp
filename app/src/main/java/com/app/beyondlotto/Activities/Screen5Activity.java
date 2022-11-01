package com.app.beyondlotto.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import com.app.beyondlotto.Model.AppRepository;
import com.app.beyondlotto.R;

public class Screen5Activity extends AppCompatActivity {
    ImageView imageView;
    ProgressBar pbar;
    VideoView videoView;
    private static final String TAG = "Screen5Activity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);

        init();
        keepScreenAwake();
        getSetUserData();
    }

    private void init() {
        imageView = findViewById(R.id.imagev);
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
        videoView = (VideoView) findViewById(R.id.myvideoview);
    }

    private void getSetUserData(){
        AppRepository.getUser(this, getApplicationContext(), "screen5", (status, user) -> {
            if (status) {
                AppRepository.getGamesofUser(this, user, getApplicationContext(), "screen5", (type, img) -> {

                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type != null && img != null) {
                                setMediaData(Screen5Activity.this, type, img, "");
                            } else {
                                Toast.makeText(Screen5Activity.this, "No Data found!", Toast.LENGTH_SHORT).show();
                                pbar.setVisibility(View.GONE);
                            }
                        }
                    });
                });
            }
        });
    }

    private void setMediaData(Context context, String type, String img, String orientation) {

        pbar.setVisibility(View.GONE);
        if (type != null) {
            if (type.equals("image")) {
                imageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(img).into(imageView);
            } else if (type.equals("video")) {
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