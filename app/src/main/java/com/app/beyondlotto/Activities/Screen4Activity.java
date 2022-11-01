package com.app.beyondlotto.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
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

public class Screen4Activity extends AppCompatActivity {
    static ImageView imageView;
    ProgressBar pbar;
    VideoView videoView;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4);

        init();
        keepScreenAwake();
        getSetUserData();
    }

    private void getSetUserData() {
        AppRepository.getUser(this, getApplicationContext(),"screen4",(status,user)->{
            if (status){
                AppRepository.getGamesofUser(this, user, getApplicationContext(), "screen4",(type,img)->{
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type != null && img != null){
                                setMediaData(Screen4Activity.this, type, img, "");
                            }else {
                                Toast.makeText(Screen4Activity.this, "No Data found!", Toast.LENGTH_SHORT).show();
                                pbar.setVisibility(View.GONE);
                            }
                        }
                    });
                });
            }
        });
    }

    private void init() {
        imageView = findViewById(R.id.imagev);
        videoView = (VideoView) findViewById(R.id.myvideoview);
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
    }

    public void setMediaData(Context context, String type, String img, String orientation) {
        pbar.setVisibility(View.GONE);
        if (type != null){
            if (type.equals("image")){
                imageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(img).into(imageView);
            }else if (type.equals("video")) {
                String LINK = img;
                MediaController mc = new MediaController(context);
                videoView.setVisibility(View.VISIBLE);
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