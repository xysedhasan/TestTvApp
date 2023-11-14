package com.app.beyondlottotv.Activities;

import androidx.annotation.NonNull;
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

import com.app.beyondlottotv.Model.Screen3;
import com.bumptech.glide.Glide;
import com.app.beyondlottotv.Model.AppRepository;
import com.app.beyondlottotv.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.net.MalformedURLException;

public class Screen5Activity extends AppCompatActivity {
    ImageView imageView;
    ProgressBar pbar;
    VideoView videoView;
    boolean myouTubePlayerinitialized = false;

    YouTubePlayerView youTubePlayerView;


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
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
        imageView = findViewById(R.id.imagev);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        videoView = (VideoView) findViewById(R.id.myvideoview);
    }

    String videoid = "";
    private void getSetUserData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            AppRepository.getUser(this, getApplicationContext(), "screen5", (status, user) -> {
                if (status) {
                    Screen3 screen3 = user.getScreen5();
                    if (user.getScreen5().getMedia_type().equals("image")) {
                        setMediaData(this,user.getScreen5().getMedia_url());
                    } else {
                        try {
                            videoid = AppRepository.getYoutubeVideoIdFromUrl(screen3.getVideo_url());
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                                .controls(0)
                                .rel(0)
                                .ivLoadPolicy(1)
                                .ccLoadPolicy(1)
                                .build();

                        getLifecycle().addObserver(youTubePlayerView);
                        youTubePlayerView.setEnableAutomaticInitialization(false);

                        if (myouTubePlayerinitialized){
                            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    // Load the first video
                                    if (!videoid.equals("") && videoid != null) {
                                        youTubePlayer.loadVideo(videoid, 0);
                                        youTubePlayerView.setVisibility(View.VISIBLE);
                                    }else {
                                        Toast.makeText(Screen5Activity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }

                                    youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                                        @Override
                                        public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                                            super.onStateChange(youTubePlayer, state);

                                            if (state == PlayerConstants.PlayerState.ENDED) {
                                                // Video ended, play the next one
    //                                playNextVideo(youTubePlayer);
                                                youTubePlayer.seekTo(0);
                                            }
                                        }
                                    });
                                }
                            });


                        }else {
                            youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    myouTubePlayerinitialized = true;
                                    // Load the first video
                                    if (!videoid.equals("") && videoid != null) {
                                        youTubePlayer.loadVideo(videoid, 0);
                                        youTubePlayerView.setVisibility(View.VISIBLE);
                                    }else {
                                        Toast.makeText(Screen5Activity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }

                                    youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                                        @Override
                                        public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                                            super.onStateChange(youTubePlayer, state);

                                            if (state == PlayerConstants.PlayerState.ENDED) {
                                                // Video ended, play the next one
    //                                playNextVideo(youTubePlayer);
                                                youTubePlayer.seekTo(0);
                                            }
                                        }
                                    });
                                }
                            }, true, iFramePlayerOptions);
                        }

                    }

                }
            });
        }
    }

    private void setMediaData(Context context, String img) {
        pbar.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        Glide.with(context).load(img).into(imageView);
    }


    public void keepScreenAwake() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();
    }
}