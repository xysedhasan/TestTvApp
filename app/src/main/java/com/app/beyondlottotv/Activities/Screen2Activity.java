package com.app.beyondlottotv.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.beyondlottotv.Model.AppRepository;
import com.app.beyondlottotv.Model.Game;
import com.app.beyondlottotv.Model.GridAdapter;
import com.app.beyondlottotv.Model.Screen1;
import com.app.beyondlottotv.R;

import java.util.ArrayList;

public class Screen2Activity extends AppCompatActivity {
    static GridView gridView;
    private static final String TAG = "MainActivity";

    RelativeLayout logolyt, footerRelative;
    static RelativeLayout gridRelative;
    static int sizeoflyt = 0;
    static RelativeLayout headerrltv;
    static RelativeLayout parent;
    static ProgressBar pbar;
    TextView powerprice, lottoPrice, megaballPrice, texastwo, pick3, daily4, cashfive, allornothing;
    TextView dypowerprice, dylottoPrice, dymegaballPrice, dytexastwo, dypick3, dydaily4, dycashfive, dyallornothing;
    String[] powerpricetexts, lottoPricetexts, megaballPricetexts, texastwotexts, pick3texts, daily4texts, cashfivetexts, allornothingtexts;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        init();
        keepScreenAwake();
        setupGridView();
        getSetGlobalPrices();
        getSetUserData();

        //item click listner
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
            }
        });
    }

    private void getSetUserData() {
        Log.d("Screen2Activity", "getSetUserData: ");
        AppRepository.getUser(Screen2Activity.this, Screen2Activity.this, "screen2", (status, user) -> {
            if (status) {
                AppRepository.getGamesofUser(this, user, getApplicationContext(), "screen2", (type, img) -> {
                    Log.d("Screen2Activity", "AppRepository.getGamesofUser: ");
                });

                this.runOnUiThread(() -> {
                    showHideHeader(user.getScreen2().isShow_header());
                    setBoxes(user.getScreen2().getTotal_boxes());
                });
            }
        });
    }


    private void getSetGlobalPrices() {
        AppRepository.getGlobalPrices(getApplicationContext(), (prices, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    powerprice.setText(prices.getPower_ball());
                    lottoPrice.setText(prices.getTexas_loto());
                    megaballPrice.setText(prices.getMega_ball());
                    texastwo.setText(prices.getTexas_two_step());
                    pick3.setText(prices.getPick_3());
                    daily4.setText(prices.getDaily_4());
                    cashfive.setText(prices.getCash_five());
                    allornothing.setText(prices.getAll_or_nothing());
                });
            }
        });
    }

    private void setupGridView() {
        int height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, height / 6);
        rel_btn.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        logolyt.setLayoutParams(rel_btn);
    }

    private void keepScreenAwake() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();
    }

    //khokhar@2921
    public static void initrecycler(Context context, Screen1 screen2, Boolean showheader, String orientation, int boxes, String empty_box, String imageurl) {

        gridView.setVerticalScrollBarEnabled(false);
        if (showheader) {
            sizeoflyt = parent.getHeight() - headerrltv.getHeight() - 5;
        } else {
            sizeoflyt = parent.getHeight() - 5;
        }
        GridAdapter adpter = new GridAdapter(context, "2",screen2,  sizeoflyt,  orientation, boxes, empty_box, imageurl);
        gridView.setAdapter(adpter);
        pbar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setBoxes(int totalboxes) {

        if (totalboxes == 100 || totalboxes == 80 || totalboxes == 50) {
            gridView.setNumColumns(10);
        } else if (totalboxes == 65) {
            gridView.setNumColumns(13);
        } else if (totalboxes == 32) {
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

        currentIndex = 0;
        handler = new Handler();


        getSetGlobalPrices();
        getSetGlobaldaysandwinnings();
    }

    public void showHideHeader(boolean showheader) {
        if (showheader) {
            headerrltv.setVisibility(View.VISIBLE);
        } else {
            headerrltv.setVisibility(View.GONE);
        }
    }

    private void getSetGlobaldaysandwinnings() {
        AppRepository.getGlobalDaysandWinings(getApplicationContext(), (winning, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    if (winning.getPowerballArray() != null) {
                        powerpricetexts = new String[]{"MON | WED | SAT", winning.getPowerballArray()};
                    } else {
                        powerpricetexts = new String[]{"MON | WED | SAT", ""};
                    }
                    if (winning.getLottoTexasArray() != null) {
                        lottoPricetexts = new String[]{"MON | WED | SAT", winning.getLottoTexasArray()};
                    } else {
                        lottoPricetexts = new String[]{"MON | WED | SAT", ""};
                    }

                    if (winning.getMegaMillionsArray() != null) {
                        megaballPricetexts = new String[]{"TUE | FRI", winning.getMegaMillionsArray()};
                    } else {
                        megaballPricetexts = new String[]{"TUE | FRI", ""};
                    }
                    if (winning.getTexasTwoStepArray() != null) {
                        texastwotexts = new String[]{"MON | THU", winning.getTexasTwoStepArray()};
                    } else {
                        texastwotexts = new String[]{"MON | THU", ""};
                    }
                    if (winning.getCashFiveArray() != null) {
                        cashfivetexts = new String[]{"MON | SAT", winning.getCashFiveArray()};
                    } else {
                        cashfivetexts = new String[]{"MON | SAT", ""};
                    }
                    if (winning.getAllOrNothingMorningArray() != null) {
                        allornothingtexts = new String[]{"MON | SAT", winning.getAllOrNothingMorningArray()};
                    } else {
                        allornothingtexts = new String[]{"MON | SAT", ""};
                    }
                });
            }
        });

        startTextSwap();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Handler handler;
    private int currentIndex;


    private Runnable swapTextRunnable = new Runnable() {
        @Override
        public void run() {
            swapText();
            handler.postDelayed(this, 5000);
        }
    };

    private void startTextSwap() {
        handler.postDelayed(swapTextRunnable, 5000);
    }

    private void stopTextSwap() {
        handler.removeCallbacks(swapTextRunnable);
    }

    private void swapText() {
        Log.d(TAG, "swapTextlength: "+powerpricetexts.length);
        if (currentIndex >= powerpricetexts.length) {
            currentIndex = 0;
        }
        dypowerprice.setText(powerpricetexts[currentIndex]);
        dyallornothing.setText(allornothingtexts[currentIndex]);
        dycashfive.setText(cashfivetexts[currentIndex]);
        dytexastwo.setText(texastwotexts[currentIndex]);
        dymegaballPrice.setText(megaballPricetexts[currentIndex]);
        dylottoPrice.setText(lottoPricetexts[currentIndex]);
        currentIndex++;
    }
}