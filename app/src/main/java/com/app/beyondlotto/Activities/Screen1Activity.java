package com.app.beyondlotto.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.beyondlotto.Model.AppRepository;
import com.app.beyondlotto.Model.Game;
import com.app.beyondlotto.Model.GridAdapter;
import com.app.beyondlotto.Model.UserNew;
import com.app.beyondlotto.R;

import java.util.ArrayList;

public class Screen1Activity extends AppCompatActivity {

    static GridView gridView;
    private static final String TAG = "MainActivity";
    RelativeLayout logolyt, footerRelative;
    static RelativeLayout gridRelative;
    static int sizeoflyt = 0;
    static ProgressBar pbar;
    static RelativeLayout headerrltv;
    static RelativeLayout parent;
    TextView powerprice, lottoPrice, megaballPrice, texastwo, pick3, daily4, cashfive, allornothing;
    TextView dypowerprice, dylottoPrice, dymegaballPrice, dytexastwo, dypick3, dydaily4, dycashfive, dyallornothing;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        init();
        keepScreenAwake();
        setUpGridView();
        getSetGlobalPrices();
        getSetUserData();

        //item click listner
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
            }
        });
    }

    private void setUpGridView(){
        int height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, height / 6);
        rel_btn.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        logolyt.setLayoutParams(rel_btn);
    }

    private  void getSetUserData(){
        AppRepository.getUser(Screen1Activity.this, Screen1Activity.this, "screen1", (status, user) -> {
            if (status) {
                AppRepository.getGamesofUser(this, user, getApplicationContext(), "screen1",(type,img)->{

                });
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showHideHeader(user.getScreen1().isShow_header());
                        setBoxes(user.getScreen1().getTotal_boxes());
                    }
                });
            }
        });
    }

    private void getSetGlobalPrices(){
        AppRepository.getGlobalPrices(getApplicationContext(),(prices, status)->{
            if (status){
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        powerprice.setText(prices.getPower_ball());
                        lottoPrice.setText(prices.getTexas_loto());
                        megaballPrice.setText(prices.getMega_ball());
                        texastwo.setText(prices.getTexas_two_step());
                        pick3.setText(prices.getPick_3());
                        daily4.setText(prices.getDaily_4());
                        cashfive.setText(prices.getCash_five());
                        allornothing.setText(prices.getAll_or_nothing());
                    }
                });
            }
        });
    }


    //khokhar@2921
    public static void initrecycler(Context context, ArrayList<Game> games, ArrayList<Boolean> animateArr,Boolean showheader,String orientation,int boxes,String empty_box,String imageurl  ) {

        gridView.setVerticalScrollBarEnabled(false);
        if (showheader) {
            sizeoflyt = parent.getHeight() - headerrltv.getHeight() - 5;
        } else {
            sizeoflyt = parent.getHeight() - 5;
        }

        GridAdapter adpter = new GridAdapter(context,"1", games, sizeoflyt, animateArr,orientation,boxes,empty_box,imageurl );
        gridView.setAdapter(adpter);
        pbar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void keepScreenAwake(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();
    }

    public void setBoxes(int totalboxes) {
        if (totalboxes == 100 || totalboxes == 80 || totalboxes == 50) {
            gridView.setNumColumns(10);
        }else if (totalboxes == 65) {
            gridView.setNumColumns(13);
        }else if (totalboxes == 32) {
            gridView.setNumColumns(8);
        } else {
            gridView.setNumColumns(6);
        }
    }

    private void init() {
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
        gridView = (GridView) findViewById(R.id.gridview);
        gridRelative = findViewById(R.id.gridrltv);
        footerRelative = findViewById(R.id.bottomrltv);
        headerrltv = findViewById(R.id.header);
        logolyt = (RelativeLayout) findViewById(R.id.logos);
        powerprice = findViewById(R.id.powerbalprice);
        lottoPrice = findViewById(R.id.lottotexas);
        megaballPrice = findViewById(R.id.megamilions);
        texastwo = findViewById(R.id.texastwostep);
        pick3 = findViewById(R.id.pick3);
        daily4 = findViewById(R.id.daily4);
        cashfive = findViewById(R.id.cash_five);
        allornothing = findViewById(R.id.allornothing);
        parent = findViewById(R.id.parent);

        dypowerprice = findViewById(R.id.powerbalpriceday);
        dylottoPrice = findViewById(R.id.lottotexasday);
        dymegaballPrice = findViewById(R.id.megamilionsday);
        dytexastwo = findViewById(R.id.texastwostepday);
        dypick3 = findViewById(R.id.pick3day);
        dydaily4 = findViewById(R.id.daily4day);
        dycashfive = findViewById(R.id.cash_fiveday);
        dyallornothing = findViewById(R.id.allornothingday);
    }

    public void showHideHeader(boolean showheader) {
        if (showheader) {
            headerrltv.setVisibility(View.VISIBLE);
        } else {
            headerrltv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}