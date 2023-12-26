package com.app.beyondlottotv.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.app.beyondlottotv.Model.AppRepository;
import com.app.beyondlottotv.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class VideoAdsActivity extends AppCompatActivity {

    private List<String> videoIds = new ArrayList<>();
    private int currentVideoIndex = 0;
        private String stringJavaScript = "<html><body style=\"margin:0;padding:0;\">" +
                "<iframe id=\"youtubePlayer\" width=\"100%\" height=\"100%\" " +
                "src=\"https://www.youtube.com/embed/I0_DSVq7sUc?si=tx1vKilNJg5IJ9So&autoplay=1&showinfo=0&controls=0\" " +
                "title=\"YouTube video player\" frameborder=\"0\" " +
                "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" " +
                "allowfullscreen></iframe>" +
                "<script>" +
                "var player; " +
                "function onYouTubeIframeAPIReady() {" +
                "    player = new YT.Player('youtubePlayer', {" +
                "        events: {" +
                "            'onStateChange': onPlayerStateChange" +
                "        }" +
                "    });" +
                "} " +
                "function onPlayerStateChange(event) {" +
                "    if (event.data == YT.PlayerState.ENDED) {" +
                "        player.seekTo(0); " +
                "    }" +
                "}" +
                "</script>" +
                "</body></html>";

        private WebView webView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_video_ads);

//            webView = findViewById(R.id.webView);
//            webView.loadData(stringJavaScript, "text/html", "utf-8");
//            webView.getSettings().setJavaScriptEnabled(true);
//            webView.setWebChromeClient(new WebChromeClient());
//            webView.getSettings().setLoadWithOverviewMode(true);
//            webView.getSettings().setUseWideViewPort(true);
//            // Hide the controls
//            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                AppRepository.getUser(this,getApplicationContext(),"Screen5",(status,user)->{

                });
            }

            // Add video IDs to the list
            videoIds.add("pFuh3K3wQg8");
            videoIds.add("tYZi9ugG7Gk");
            videoIds.add("p2AWYanIHkc");

            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
            IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                    .controls(0)
                    .rel(0)
                    .ivLoadPolicy(1)
                    .ccLoadPolicy(1)
                    .build();

            getLifecycle().addObserver(youTubePlayerView);
            youTubePlayerView.setEnableAutomaticInitialization(false);
            youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    // Load the first video
                    youTubePlayer.loadVideo(videoIds.get(currentVideoIndex), 0);


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



//            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                @Override
//                public void onReady(@NonNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
//                    // Load the first video
//                    youTubePlayer.loadVideo(videoIds.get(currentVideoIndex), 0);
//
//
//                    youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
//                        @Override
//                        public void onStateChange(@NonNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
//                            super.onStateChange(youTubePlayer, state);
//
//                            if (state == PlayerConstants.PlayerState.ENDED) {
//                                // Video ended, play the next one
//                                playNextVideo(youTubePlayer);
//                            }
//                        }
//                    });
//                }
//            });
        }

    private void playNextVideo(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
        currentVideoIndex++;

        // Check if we reached the end of the list, loop back to the beginning
        if (currentVideoIndex >= videoIds.size()) {
            currentVideoIndex = 0;
        }

        // Load and play the next video
        youTubePlayer.loadVideo(videoIds.get(currentVideoIndex), 0);
    }

}
