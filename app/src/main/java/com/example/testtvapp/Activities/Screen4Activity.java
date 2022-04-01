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

public class Screen4Activity extends AppCompatActivity {
    static ImageView imageView;
    ProgressBar pbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4);
        imageView = findViewById(R.id.imagev);
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
        AppRepository.getUser(this, getApplicationContext(),"screen4",(status,user)->{
            if (status){

            }
        });
    }

    public static void setMediaData(Context context, String type, String img, String orientation) {
        if (type != null){
            if (type.equals("image")){
                Glide.with(context).load(img).into(imageView);
            }
        }

    }
}