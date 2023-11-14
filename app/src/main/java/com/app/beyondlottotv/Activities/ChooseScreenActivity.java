package com.app.beyondlottotv.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.app.beyondlottotv.Model.AppRepository;
import com.app.beyondlottotv.Model.GeorgiaScrapeTask;
import com.app.beyondlottotv.Model.ScrapeWebsiteTask;
import com.app.beyondlottotv.Model.UserNew;
import com.app.beyondlottotv.R;

public class ChooseScreenActivity extends AppCompatActivity {


    Button screen1, screen2, screen3, screen4, screen5, screen6, screen7;
    UserNew userme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_screen);
        init();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AppRepository.getUser(this, getApplicationContext(), "", (status, user) -> {
            Log.d("TAG", "status: "+status);
            if (status) {
                if (user != null) {
                    userme = user;
                    if (user.getAccount_type().equals("A1")) {
                        //        A1 - 1 screen lotto
                        screen1.setVisibility(View.VISIBLE);
                        screen2.setVisibility(View.GONE);
                        screen3.setVisibility(View.GONE);
                        screen4.setVisibility(View.GONE);
                        screen5.setVisibility(View.GONE);
                        screen6.setVisibility(View.GONE);
                        screen7.setVisibility(View.GONE);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(ChooseScreenActivity.this, MainactivityPortraitActivity.class);
                                intent.putExtra("from", "screen1");
                                startActivity(intent);
                            }
                        }, 3000);


                    } else if (user.getAccount_type().equals("B2")) {
                        //        B2 - 2 Screen lotto
                        screen1.setVisibility(View.VISIBLE);
                        screen2.setVisibility(View.VISIBLE);
                        screen3.setVisibility(View.GONE);
                        screen4.setVisibility(View.GONE);
                        screen5.setVisibility(View.GONE);
                        screen6.setVisibility(View.GONE);
                        screen7.setVisibility(View.GONE);
                    } else if (user.getAccount_type().equals("C3")) {
                        //        C3 - 3 Screen lotto
                        screen1.setVisibility(View.VISIBLE);
                        screen2.setVisibility(View.VISIBLE);
                        screen3.setVisibility(View.VISIBLE);
                        screen4.setVisibility(View.GONE);
                        screen5.setVisibility(View.GONE);
                        screen6.setVisibility(View.GONE);
                        screen7.setVisibility(View.GONE);
                        screen1.setNextFocusRightId(R.id.rltvscreen2);
                        screen2.setNextFocusLeftId(R.id.rltvscreen1);
                    } else if (user.getAccount_type().equals("D4")) {
                        //        D4 - 4 screens lotto
                        screen1.setVisibility(View.VISIBLE);
                        screen2.setVisibility(View.VISIBLE);
                        screen3.setVisibility(View.VISIBLE);
                        screen4.setVisibility(View.VISIBLE);
                        screen5.setVisibility(View.GONE);
                        screen6.setVisibility(View.GONE);
                        screen7.setVisibility(View.GONE);

                        screen1.setNextFocusRightId(R.id.rltvscreen2);
                        screen3.setNextFocusLeftId(R.id.rltvscreen1);
                        screen3.setNextFocusRightId(R.id.rltvscreen4);
                        screen4.setNextFocusLeftId(R.id.rltvscreen3);

                    } else if (user.getAccount_type().equals("E5")) {
                        //        E5 - 1 lotto 1 Add
                        screen1.setVisibility(View.VISIBLE);
                        screen2.setVisibility(View.GONE);
                        screen3.setVisibility(View.GONE);
                        screen4.setVisibility(View.GONE);
                        screen5.setVisibility(View.VISIBLE);
                        screen6.setVisibility(View.GONE);
                        screen7.setVisibility(View.GONE);

                        screen5.setBackground(getResources().getDrawable(R.drawable.screen2hoverforads));

                        screen1.setNextFocusRightId(R.id.rltvscreen3);
                        screen3.setNextFocusLeftId(R.id.rltvscreen2);
                        screen3.setNextFocusRightId(R.id.rltvscreen4);
                        screen4.setNextFocusLeftId(R.id.rltvscreen3);

                    } else if (user.getAccount_type().equals("F6")) {
                        //        F6 - 2 lotto 2 Add
                        screen1.setVisibility(View.VISIBLE);
                        screen2.setVisibility(View.VISIBLE);
                        screen3.setVisibility(View.GONE);
                        screen4.setVisibility(View.GONE);
                        screen5.setVisibility(View.VISIBLE);
                        screen6.setVisibility(View.VISIBLE);
                        screen7.setVisibility(View.GONE);

                        screen1.setBackground(getResources().getDrawable(R.drawable.button1hover));
                        screen2.setBackground(getResources().getDrawable(R.drawable.button2hover));
                        screen5.setBackground(getResources().getDrawable(R.drawable.screen3hoverforads));
                        screen6.setBackground(getResources().getDrawable(R.drawable.screen4hoverforads));

                        screen1.setNextFocusRightId(R.id.rltvscreen2);
                        screen2.setNextFocusLeftId(R.id.rltvscreen1);
                        screen2.setNextFocusRightId(R.id.rltvscreen5);
                        screen5.setNextFocusLeftId(R.id.rltvscreen2);
                        screen5.setNextFocusRightId(R.id.rltvscreen6);
                        screen6.setNextFocusLeftId(R.id.rltvscreen5);

                    } else if (user.getAccount_type().equals("G7")) {
                        //        G7 - 4 lotto - 3 adds
                        screen1.setVisibility(View.VISIBLE);
                        screen2.setVisibility(View.VISIBLE);
                        screen3.setVisibility(View.VISIBLE);
                        screen4.setVisibility(View.VISIBLE);
                        screen5.setVisibility(View.VISIBLE);
                        screen6.setVisibility(View.VISIBLE);
                        screen7.setVisibility(View.VISIBLE);

                        screen1.setBackground(getResources().getDrawable(R.drawable.button1hover));
                        screen2.setBackground(getResources().getDrawable(R.drawable.button2hover));
                        screen3.setBackground(getResources().getDrawable(R.drawable.button3hover));
                        screen4.setBackground(getResources().getDrawable(R.drawable.button4hover));
                        screen5.setBackground(getResources().getDrawable(R.drawable.button5hover));
                        screen6.setBackground(getResources().getDrawable(R.drawable.button6hover));
                        screen7.setBackground(getResources().getDrawable(R.drawable.button7hover));

                        screen1.setNextFocusRightId(R.id.rltvscreen2);
                        screen2.setNextFocusLeftId(R.id.rltvscreen1);
                        screen2.setNextFocusRightId(R.id.rltvscreen3);
                        screen3.setNextFocusLeftId(R.id.rltvscreen2);
                        screen3.setNextFocusRightId(R.id.rltvscreen4);
                        screen4.setNextFocusLeftId(R.id.rltvscreen3);
                        screen4.setNextFocusRightId(R.id.rltvscreen5);
                        screen5.setNextFocusLeftId(R.id.rltvscreen4);
                        screen5.setNextFocusRightId(R.id.rltvscreen6);
                        screen6.setNextFocusLeftId(R.id.rltvscreen5);
                        screen6.setNextFocusRightId(R.id.rltvscreen7);
                    }
                } else {
                    AppRepository.checklogout(this, false, ChooseScreenActivity.this);
                }
            } else {
                AppRepository.checklogout(this, false, ChooseScreenActivity.this);

            }
        });
        loadGamesInLocalStorate();
        screen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScreenActivity.this, MainactivityPortraitActivity.class);
                intent.putExtra("from", "screen1");
                startActivity(intent);
            }
        });

        screen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ChooseScreenActivity.this, VideoAdsActivity.class);
                Intent intent = new Intent(ChooseScreenActivity.this, MainactivityPortraitActivity.class);
                intent.putExtra("from", "screen2");
                startActivity(intent);
            }
        });

        screen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseScreenActivity.this, MainactivityPortraitActivity.class);
                intent.putExtra("from", "screen3");
                startActivity(intent);
            }
        });

        screen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScreenActivity.this, MainactivityPortraitActivity.class);
                intent.putExtra("from", "screen4");
                startActivity(intent);
            }
        });

        screen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScreenActivity.this, Screen5Activity.class);
                startActivity(intent);
            }
        });

        screen6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScreenActivity.this, Screen6Activity.class);
                startActivity(intent);
            }
        });
        screen7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScreenActivity.this, Screen7Activity.class);
                startActivity(intent);
            }
        });

    }

    private void loadGamesInLocalStorate() {
        AppRepository.getGames(this);
    }

    public void init() {
        screen1 = findViewById(R.id.rltvscreen1);
        screen2 = findViewById(R.id.rltvscreen2);
        screen3 = findViewById(R.id.rltvscreen3);
        screen4 = findViewById(R.id.rltvscreen4);
        screen5 = findViewById(R.id.rltvscreen5);
        screen6 = findViewById(R.id.rltvscreen6);
        screen7 = findViewById(R.id.rltvscreen7);

//        new ScrapeWebsiteTask().execute();
//        new GeorgiaScrapeTask().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}