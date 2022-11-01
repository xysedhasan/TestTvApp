package com.app.beyondlotto.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.beyondlotto.Model.AppRepository;
import com.app.beyondlotto.Model.UserNew;
import com.app.beyondlotto.R;

public class ChooseScreenActivity extends AppCompatActivity {


    Button screen1,screen2,screen3,screen4,screen5;
    UserNew userme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_screen);
        init();
        loadGamesInLocalStorate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
       //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AppRepository.getUser(this,getApplicationContext(),"",(status,user)->{
            if (user!= null){
                userme = user;
                if (user.getAccount_type().equals("basic")){
                    screen1.setVisibility(View.VISIBLE);
                    screen2.setVisibility(View.GONE);
                    screen3.setVisibility(View.GONE);
                    screen4.setVisibility(View.GONE);
                    screen5.setVisibility(View.GONE);
                }else if(user.getAccount_type().equals("standard")){
                    screen1.setVisibility(View.VISIBLE);
                    screen2.setVisibility(View.GONE);
                    screen3.setVisibility(View.GONE);
                    screen4.setVisibility(View.GONE);
                    screen5.setVisibility(View.GONE);
                }else if(user.getAccount_type().equals("ultimate")){
                    screen1.setVisibility(View.VISIBLE);
                    screen2.setVisibility(View.VISIBLE);
                    screen3.setVisibility(View.GONE);
                    screen4.setVisibility(View.GONE);
                    screen5.setVisibility(View.GONE);
                    screen1.setNextFocusRightId(R.id.rltvscreen2);
                    screen2.setNextFocusLeftId(R.id.rltvscreen1);
                }else if(user.getAccount_type().equals("premium")){
                    screen1.setVisibility(View.VISIBLE);
                    screen2.setVisibility(View.GONE);
                    screen3.setVisibility(View.VISIBLE);
                    screen4.setVisibility(View.VISIBLE);
                    screen5.setVisibility(View.GONE);

                    screen3.setBackground(getResources().getDrawable(R.drawable.button2hoverforads));
                    screen4.setBackground(getResources().getDrawable(R.drawable.button3hover));
                    screen1.setNextFocusRightId(R.id.rltvscreen3);
                    screen3.setNextFocusLeftId(R.id.rltvscreen1);
                    screen3.setNextFocusRightId(R.id.rltvscreen4);
                    screen4.setNextFocusLeftId(R.id.rltvscreen3);

                }else if(user.getAccount_type().equals("business")){
                    screen1.setVisibility(View.VISIBLE);
                    screen2.setVisibility(View.VISIBLE);
                    screen3.setVisibility(View.VISIBLE);
                    screen4.setVisibility(View.VISIBLE);
                    screen5.setVisibility(View.VISIBLE);

                    screen1.setBackground(getResources().getDrawable(R.drawable.button1hover));
                    screen2.setBackground(getResources().getDrawable(R.drawable.button2hover));
                    screen3.setBackground(getResources().getDrawable(R.drawable.button3hover));
                    screen4.setBackground(getResources().getDrawable(R.drawable.button4hover));
                    screen5.setBackground(getResources().getDrawable(R.drawable.button5hover));

                    screen1.setNextFocusRightId(R.id.rltvscreen2);
                    screen2.setNextFocusLeftId(R.id.rltvscreen1);
                    screen2.setNextFocusRightId(R.id.rltvscreen3);
                    screen3.setNextFocusLeftId(R.id.rltvscreen2);
                    screen3.setNextFocusRightId(R.id.rltvscreen4);
                    screen4.setNextFocusLeftId(R.id.rltvscreen3);
                    screen4.setNextFocusRightId(R.id.rltvscreen5);
                }
            }
        });
        screen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScreenActivity.this, Screen1Activity.class);
                startActivity(intent);
            }
        });

        screen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScreenActivity.this, Screen2Activity.class);
                startActivity(intent);
            }
        });

        screen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScreenActivity.this, Screen3Activity.class);
                startActivity(intent);
            }
        });

        screen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScreenActivity.this, Screen4Activity.class);
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


    }

    private void loadGamesInLocalStorate(){
        AppRepository.getGames(this);
    }

    public void init(){
        screen1 = findViewById(R.id.rltvscreen1);
        screen2 = findViewById(R.id.rltvscreen2);
        screen3 = findViewById(R.id.rltvscreen3);
        screen4 = findViewById(R.id.rltvscreen4);
        screen5 = findViewById(R.id.rltvscreen5);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}