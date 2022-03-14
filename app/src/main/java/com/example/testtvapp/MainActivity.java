package com.example.testtvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testtvapp.Model.AppRepository;
import com.example.testtvapp.Model.Game;
import com.example.testtvapp.Model.GridAdapter;
import com.example.testtvapp.Model.Prefrences;
import com.example.testtvapp.Model.RecyclerviewAdapter;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    static GridView gridView;
    private static final String TAG = "MainActivity";
    ArrayList<Game> games = new ArrayList<>();
    RelativeLayout logolyt, footerRelative;
    static RelativeLayout gridRelative;
    static int sizeoflyt = 0;
    static RelativeLayout headerrltv;
    static RelativeLayout parent;
    TextView powerprice, lottoPrice, megaballPrice, texastwo, pick3, daily4, cashfive, allornothing;
    TextView dypowerprice, dylottoPrice, dymegaballPrice, dytexastwo, dypick3, dydaily4, dycashfive, dyallornothing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getOrientation();
        Prefrences.setOrienation(getApplicationContext(), 1);

        if (Prefrences.getOrientation(getApplicationContext()) == 0) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();


        init();

        int width = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        int height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;


        gridView.setVerticalScrollBarEnabled(false);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, height / 6);
        rel_btn.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        logolyt.setLayoutParams(rel_btn);

        AppRepository.getGames(this, getApplicationContext());
        //item click listner
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
            }
        });

        AppRepository.getGlobalPrices(getApplicationContext(), (prices) -> {
            powerprice.setText(prices.getPower_ball());
            lottoPrice.setText(prices.getTexas_loto());
            megaballPrice.setText(prices.getMega_ball());
            texastwo.setText(prices.getTexas_two_step());
            pick3.setText(prices.getPick_3());
            daily4.setText(prices.getDaily_4());
            cashfive.setText(prices.getCash_five());
            allornothing.setText(prices.getAll_or_nothing());
        });
        // showHideHeader(getApplicationContext());

    }

    //khokhar@2921
    public static void initrecycler(Context context, ArrayList<Game> games) {
        gridView.setVerticalScrollBarEnabled(false);
        if (headerrltv.getVisibility() == View.VISIBLE){
           sizeoflyt = parent.getHeight() - headerrltv.getHeight() - 5;
           // sizeoflyt = gridRelative.getHeight();
        }else {
            sizeoflyt = parent.getHeight() - 5;
        }

        ArrayList<Game> temp = new ArrayList<>();
        for (int i = 0; i < Prefrences.getNumberOfBoxes(context); i++)
            temp.add(games.get(i));
        GridAdapter adpter = new GridAdapter(context, temp, sizeoflyt);
        gridView.setAdapter(adpter);
        setBoxes(context);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


    public static void setBoxes(Context context) {

        if (Prefrences.getOrientation(context) == 0) {
            if (Prefrences.getNumberOfBoxes(context) == 50) {
                gridView.setNumColumns(10);
            } else if (Prefrences.getNumberOfBoxes(context) == 32) {
                gridView.setNumColumns(8);
            } else {
                gridView.setNumColumns(6);
            }
        } else {
            if (Prefrences.getNumberOfBoxes(context) == 50) {
                gridView.setNumColumns(5);
            } else if (Prefrences.getNumberOfBoxes(context) == 32) {
                gridView.setNumColumns(4);
            } else {
                gridView.setNumColumns(3);
            }
        }

    }

    private void init() {
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


        if(Prefrences.getOrientation(getApplicationContext()) == 0){
        }else{
            powerprice.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
            lottoPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
            megaballPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
            texastwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
            pick3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
            daily4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
            cashfive.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));
            allornothing.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_font));

            dypowerprice.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_fonttwo));
            dylottoPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_fonttwo));
            dymegaballPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_fonttwo));
            dytexastwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_fonttwo));
            dypick3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_fonttwo));
            dydaily4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_fonttwo));
            dycashfive.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_fonttwo));
            dyallornothing.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.result_fonttwo));
        }
    }

    public static void showHideHeader(Context context) {
        if (Prefrences.isShowHeader(context)) {
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