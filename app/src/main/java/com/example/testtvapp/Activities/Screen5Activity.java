package com.example.testtvapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.testtvapp.Model.AppRepository;
import com.example.testtvapp.R;

public class Screen5Activity extends AppCompatActivity {
    static ImageView imageView;
    static ProgressBar pbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);
        AppRepository.getUser(this, getApplicationContext(),"screen5",(status,user)->{
            if (status){

            }
        });
        imageView = findViewById(R.id.imagev);
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
    }

    public static void setMediaData(Context context, String type, String img, String orientation) {
        pbar.setVisibility(View.GONE);
        if (type != null){
            if (type.equals("image")){
                Glide.with(context).load(img).into(imageView);

            }
        }

    }
}