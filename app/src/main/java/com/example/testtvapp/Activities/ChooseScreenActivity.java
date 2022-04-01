package com.example.testtvapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.testtvapp.R;

public class ChooseScreenActivity extends AppCompatActivity {


    Button screen1,screen2,screen3,screen4,screen5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_screen);
        init();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
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

    public void init(){
        screen1 = findViewById(R.id.rltvscreen1);
        screen2 = findViewById(R.id.rltvscreen2);
        screen3 = findViewById(R.id.rltvscreen3);
        screen4 = findViewById(R.id.rltvscreen4);
        screen5 = findViewById(R.id.rltvscreen5);
    }
}