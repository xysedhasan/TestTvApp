package com.app.beyondlotto.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
        imageView = findViewById(R.id.imagev);
        videoView = (VideoView) findViewById(R.id.myvideoview);
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
        AppRepository.getUser(this, getApplicationContext(),"screen4",(status,user)->{
            if (status){
                AppRepository.getGamesofUser(this, user, getApplicationContext(), "screen4",(type,img)->{
                    if (type != null && img != null){
                        setMediaData(Screen4Activity.this, type, img, "");
                    }else {
                        Toast.makeText(this, "No Data found!", Toast.LENGTH_SHORT).show();
                        pbar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public   void setMediaData(Context context, String type, String img, String orientation) {
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
}