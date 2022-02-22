package com.example.testtvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.testtvapp.Model.AppRepository;
import com.example.testtvapp.Model.Game;
import com.example.testtvapp.Model.GridAdapter;
import com.example.testtvapp.Model.RecyclerviewAdapter;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    static GridView gridView;
    private static final String TAG = "MainActivity";
    ArrayList<Game> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int width= getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        int height= getApplicationContext().getResources().getDisplayMetrics().heightPixels;

        Log.d(TAG, "onCreate: heightt:"+height);
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setVerticalScrollBarEnabled(false);


//        AppRepository.getUserGames((status,gamesarr)->{
//            if (status){
//                GridAdapter adpter= new GridAdapter(getApplicationContext(), gamesarr);
//                gridView.setAdapter(adpter);
//            }
//        });
       AppRepository.getGames(this,getApplicationContext());
     //   AppRepository.getUser(this,getApplicationContext());
//        AppRepository.getGames((status,games)->{
//            if (status){
                GridAdapter adpter= new GridAdapter(getApplicationContext(), games);
                gridView.setAdapter(adpter);
//            }
//        });
        //item click listner
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
            }
        });
    }

    public static  void initrecycler(Context context,ArrayList<Game> games){
        gridView.setVerticalScrollBarEnabled(false);
        GridAdapter adpter= new GridAdapter(context, games);
        gridView.setAdapter(adpter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}