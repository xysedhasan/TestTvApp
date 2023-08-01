package com.app.beyondlottotv.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.beyondlottotv.Model.AppRepository;
import com.app.beyondlottotv.Model.GridAdapter;
import com.app.beyondlottotv.Model.Prefrences;
import com.app.beyondlottotv.Model.Screen1;
import com.app.beyondlottotv.R;

import java.text.DecimalFormat;

public class MainactivityPortraitActivity extends AppCompatActivity {
    static GridView gridView;
    RelativeLayout logolyt, footerRelative;
    static RelativeLayout gridRelative;
    LinearLayout texasheaderlyt, georgiahaderlyt;
    static int sizeoflyt = 0;
    static ProgressBar pbar;
    static RelativeLayout headerrltv;
    String screenorientation = "landscape";
    static String from = "";
    static RelativeLayout parent;
    TextView cash3, cash4, cashpop, keno, megamilliong, powerballg, fantasy5, cash4life, georgia5, jumbobucks;
    TextView dycash3, dycash4, dycashpop, dykeno, dymegamilliong, dypowerballg, dyfantasy5, dycash4life, dygeorgia5, dyjumbobucks;
    LinearLayout cash3linearLayout, cash4linearLayout, cashpoplinearLayout, kenolinearLayout, megamillionglinearLayout, powerballglinearLayout, fantasy5linearLayout, cash4lifelinearLayout, georgia5linearLayout, jumbobuckslinearLayout;

    LinearLayout daily4layout, pick3layout, powerlinearLayout, megamilionslinearLayout, lottotexaslinearLayout, texastwosteplinearLayout, pick3linearLayout, daily4linearLayout, cash_fivelinearLayout, allornothinglinearLayout;
    TextView powerprice, lottoPrice, megaballPrice, texastwo, pick3, daily4, cashfive, allornothing;
    TextView dypowerprice, dylottoPrice, dymegaballPrice, dytexastwo, dypick3, dydaily4, dycashfive, dyallornothing;
    String[] powerpricetexts, lottoPricetexts, megaballPricetexts, texastwotexts, pick3texts, daily4texts, cashfivetexts, allornothingtexts;
    private DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainportrait);
        init();
        keepScreenAwake();
//        setUpGridView();
        getSetUserData();
        //item click listner
        gridView.setOnItemClickListener((parent, view, position, id) -> {
        });
    }

//    private void setUpGridView() {
//        int height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
//        gridView.setVerticalScrollBarEnabled(false);
//        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
//        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, height / 6);
//        rel_btn.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        logolyt.setLayoutParams(rel_btn);
//    }

    private void getSetUserData() {
        AppRepository.getUser(MainactivityPortraitActivity.this, MainactivityPortraitActivity.this, from, (status, user) -> {
            if (status) {
                AppRepository.getGamesofUser(this, user, getApplicationContext(), from, (type, img) -> {
                });
                this.runOnUiThread(() -> {
                    if (from.equals("screen1")) {
                        showHideHeader(user.getScreen1().isShow_header());
                        if (user.getScreen1().getOrientation().equals("portrait")) {
                            changeScrren(user.getScreen1().getTotal_boxes());
                            stopTextSwap();
                            currentIndex = 0;
                            swapText();
                            setHeightofHeader(83);
                        } else {
                            setBoxes(user.getScreen1().getTotal_boxes());
                            startTextSwap();
                            setHeightofHeader(75);
                        }
                    } else if (from.equals("screen2")) {
                        showHideHeader(user.getScreen2().isShow_header());
                        if (user.getScreen2().getOrientation().equals("portrait")) {
                            changeScrren(user.getScreen2().getTotal_boxes());
                            stopTextSwap();
                            currentIndex = 0;
                            swapText();
                            setHeightofHeader(83);
                        } else {
                            setBoxes(user.getScreen2().getTotal_boxes());
                            startTextSwap();
                            setHeightofHeader(75);
                        }
                    } else if (from.equals("screen3")) {
                        showHideHeader(user.getScreen3().isShow_header());
                        if (user.getScreen3().getOrientation().equals("portrait")) {
                            changeScrren(user.getScreen3().getTotal_boxes());
                            stopTextSwap();
                            currentIndex = 0;
                            swapText();
                            setHeightofHeader(83);
                        } else {
                            setBoxes(user.getScreen3().getTotal_boxes());
                            startTextSwap();
                            setHeightofHeader(75);
                        }
                    } else if (from.equals("screen4")) {
                        showHideHeader(user.getScreen4().isShow_header());
                        if (user.getScreen4().getOrientation().equals("portrait")) {
                            changeScrren(user.getScreen4().getTotal_boxes());
                            stopTextSwap();
                            currentIndex = 0;
                            swapText();
                            setHeightofHeader(83);
                        } else {
                            setBoxes(user.getScreen4().getTotal_boxes());
                            startTextSwap();
                            setHeightofHeader(75);
                        }
                    }
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

    //get all winnigs
    private void getSetGlobaldaysandwinnings() {
        AppRepository.getGlobalDaysandWinings(getApplicationContext(), (winning, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    if (winning.getPowerballArray() != null) {
                        String[] numbersArray1 = {};
                        numbersArray1 = winning.getPowerballArray().split(" ");
                        powerlinearLayout.removeAllViews();
                        for (int i = 0; i < numbersArray1.length; i++) {
                            boolean last = false;
                            if (i == numbersArray1.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray1[i], last, "power");
                            powerlinearLayout.addView(textView);
                        }
                        if (numbersArray1.length > 7) {
                            powerlinearLayout.setVisibility(View.INVISIBLE);
                        }
                    }

                    if (winning.getMegaMillionsArray() != null) {
                        String[] numbersArray2 = {};
                        numbersArray2 = winning.getMegaMillionsArray().split(" ");
                        megamilionslinearLayout.removeAllViews();
//                        for (String number : numbersArray2) {
//                            TextView textView = createTextView(this, number);
//                            megamilionslinearLayout.addView(textView);
//                        }
                        for (int i = 0; i < numbersArray2.length; i++) {
                            boolean last = false;
                            if (i == numbersArray2.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray2[i], last, "megamilion");
                            megamilionslinearLayout.addView(textView);
                        }
                        if (numbersArray2.length > 7) {
                            megamilionslinearLayout.setVisibility(View.INVISIBLE);
                        }
                    }

                    if (winning.getLottoTexasArray() != null) {
                        String[] numbersArray3 = {};
                        numbersArray3 = winning.getLottoTexasArray().split(" ");
                        lottotexaslinearLayout.removeAllViews();
                        for (String number : numbersArray3) {
                            TextView textView = createTextView(this, number, false, "lotto");
                            lottotexaslinearLayout.addView(textView);
                        }
                        if (numbersArray3.length > 7) {
                            lottotexaslinearLayout.setVisibility(View.INVISIBLE);
                        }
                    }
                    if (winning.getTexasTwoStepArray() != null) {
                        String[] numbersArray4 = {};
                        numbersArray4 = winning.getTexasTwoStepArray().split(" ");
                        texastwosteplinearLayout.removeAllViews();
                        for (int i = 0; i < numbersArray4.length; i++) {
                            boolean last = false;
                            if (i == numbersArray4.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray4[i], last, "texastwo");
                            texastwosteplinearLayout.addView(textView);
                        }
                        if (numbersArray4.length > 7) {
                            texastwosteplinearLayout.setVisibility(View.INVISIBLE);
                        }
                    }

                    if (winning.getPick3DayArray() != null) {
                        String[] numbersArray5 = {};
                        numbersArray5 = winning.getPick3DayArray().split(" ");
                        pick3linearLayout.removeAllViews();
//                        for (String number : numbersArray5) {
//                            TextView textView = createTextView(this, number);
//                            pick3linearLayout.addView(textView);
//                        }
                        for (int i = 0; i < numbersArray5.length; i++) {
                            boolean last = false;
                            if (i == numbersArray5.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray5[i], last, "pick3");
                            pick3linearLayout.addView(textView);
                        }
                        if (numbersArray5.length > 7) {
                            pick3linearLayout.setVisibility(View.INVISIBLE);
                        }
                    }


                    if (winning.getDaily4DayArray() != null) {
                        String[] numbersArray6 = {};
                        numbersArray6 = winning.getDaily4DayArray().split(" ");
                        daily4linearLayout.removeAllViews();
//                        for (String number : numbersArray6) {
//                            TextView textView = createTextView(this, number, false, "daily4");
//                            daily4linearLayout.addView(textView);
//                        }
                        for (int i = 0; i < numbersArray6.length; i++) {
                            boolean last = false;
                            if (i == numbersArray6.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray6[i], last, "daily4");
                            daily4linearLayout.addView(textView);
                        }
                        if (numbersArray6.length > 6) {
                            daily4linearLayout.setVisibility(View.INVISIBLE);
                        }
                    }


                    if (winning.getCashFiveArray() != null) {
                        String[] numbersArray7 = {};
                        numbersArray7 = winning.getCashFiveArray().split(" ");
                        cash_fivelinearLayout.removeAllViews();
                        for (String number : numbersArray7) {
                            TextView textView = createTextView(this, number, false, "cash_five");
                            cash_fivelinearLayout.addView(textView);
                        }
                        if (numbersArray7.length > 6) {
                            cash_fivelinearLayout.setVisibility(View.INVISIBLE);
                        }
                    }


                    if (winning.getAllOrNothingMorningArray() != null) {
                        String[] numbersArray8 = {};
                        numbersArray8 = winning.getAllOrNothingMorningArray().split(" ");
                        allornothinglinearLayout.removeAllViews();
                        for (String number : numbersArray8) {
                            TextView textView = createTextView(this, number, true, "allornothing");
                            allornothinglinearLayout.addView(textView);
                        }
                        if (numbersArray8.length > 6) {
                            allornothinglinearLayout.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });


    }

    //khokhar@2921
    public static void initrecycler(Context context, Screen1 screen2, Boolean showheader, String orientation, int boxes, String empty_box, String imageurl) {

        gridView.setVerticalScrollBarEnabled(false);
        if (orientation.equals("portrait")) {

            if (parent.getHeight() > parent.getWidth()) {
                sizeoflyt = parent.getHeight() - 5;
            } else {
                sizeoflyt = parent.getWidth() - 5;
            }

        } else {
            if (parent.getHeight() > parent.getWidth()) {
                sizeoflyt = parent.getWidth();
            } else {
                sizeoflyt = parent.getHeight();
            }
        }
        if (showheader) {
            sizeoflyt = sizeoflyt - headerrltv.getHeight();
        }

        GridAdapter adpter = new GridAdapter(context, from, screen2, sizeoflyt, orientation, boxes, empty_box, imageurl);
        gridView.setAdapter(adpter);
        pbar.setVisibility(View.GONE);
    }


    public void keepScreenAwake() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();
    }

    public void setBoxes(int totalboxes) {

        daily4layout.setVisibility(View.VISIBLE);
        pick3layout.setVisibility(View.VISIBLE);

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.parent);
        int w = displayMetrics.widthPixels;
        int h = displayMetrics.heightPixels;
        mainLayout.setRotation(0.0f);
        mainLayout.setTranslationX(0);
        mainLayout.setTranslationY(0);
        ViewGroup.LayoutParams lp = mainLayout.getLayoutParams();
        lp.height = h;
        lp.width = w;
        mainLayout.requestLayout();


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

    public void setPortraitBoxes(int totalboxes) {
        if (totalboxes == 100) {
            gridView.setNumColumns(10);
        } else if (totalboxes == 80) {
            gridView.setNumColumns(8);
        } else if (totalboxes == 65) {
            gridView.setNumColumns(5);
        } else if (totalboxes == 50) {
            gridView.setNumColumns(5);
        } else if (totalboxes == 32) {
            gridView.setNumColumns(4);
        } else if (totalboxes == 18) {
            gridView.setNumColumns(3);
        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.logoslyt);
        layout.setWeightSum(6f);
        daily4layout.setVisibility(View.GONE);
        pick3layout.setVisibility(View.GONE);
    }

    private void init() {
        from = getIntent().getStringExtra("from");
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
        gridView = findViewById(R.id.gridview);
        gridRelative = findViewById(R.id.gridrltv);
        footerRelative = findViewById(R.id.bottomrltv);
        headerrltv = findViewById(R.id.header);
        logolyt = findViewById(R.id.logos);
        powerprice = findViewById(R.id.powerbalprice);
        lottoPrice = findViewById(R.id.lottotexas);
        megaballPrice = findViewById(R.id.megamilions);
        texastwo = findViewById(R.id.texastwostep);
        pick3 = findViewById(R.id.pick3);
        daily4 = findViewById(R.id.daily4);
        cashfive = findViewById(R.id.cash_five);
        allornothing = findViewById(R.id.allornothing);
        parent = findViewById(R.id.parent);
        displayMetrics = getResources().getDisplayMetrics();
        dypowerprice = findViewById(R.id.tvpowerdayndwinnings);
        dylottoPrice = findViewById(R.id.tvlottotexasday);
        dymegaballPrice = findViewById(R.id.tvmegamilionsday);
        dytexastwo = findViewById(R.id.tvtexastwostepday);
        dypick3 = findViewById(R.id.tvpick3day);
        dydaily4 = findViewById(R.id.daily4day);
        dycashfive = findViewById(R.id.tvcash_fiveday);
        dyallornothing = findViewById(R.id.tvallornothingday);

        daily4layout = findViewById(R.id.daily4lyt);
        pick3layout = findViewById(R.id.pick3lyt);

        powerlinearLayout = findViewById(R.id.powerlinearlyt);
        megamilionslinearLayout = findViewById(R.id.megamilionslinearlyt);
        lottotexaslinearLayout = findViewById(R.id.lottotexaslinearlyt);
        texastwosteplinearLayout = findViewById(R.id.texastwosteplinearlyt);
        pick3linearLayout = findViewById(R.id.pick3linearlyt);
        daily4linearLayout = findViewById(R.id.daily4linearlyt);
        cash_fivelinearLayout = findViewById(R.id.tvcash_fivelinearlyt);
        allornothinglinearLayout = findViewById(R.id.allornothinglinearlyt);

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

    @Override
    protected void onPause() {
        super.onPause();
        stopTextSwap();
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
            handler.postDelayed(this, 50000);
        }
    };

    private void startTextSwap() {
        handler.postDelayed(swapTextRunnable, 50000);
    }

    private void stopTextSwap() {
        handler.removeCallbacks(swapTextRunnable);
    }

    private void swapText() {
        if (currentIndex >= 2) {
            currentIndex = 0;
        }

        if (currentIndex == 0) {
            dypowerprice.setVisibility(View.VISIBLE);
            dylottoPrice.setVisibility(View.VISIBLE);
            dymegaballPrice.setVisibility(View.VISIBLE);
            dytexastwo.setVisibility(View.VISIBLE);
            dypick3.setVisibility(View.VISIBLE);
            dydaily4.setVisibility(View.VISIBLE);
            dycashfive.setVisibility(View.VISIBLE);
            dyallornothing.setVisibility(View.VISIBLE);


            powerlinearLayout.setVisibility(View.GONE);
            megamilionslinearLayout.setVisibility(View.GONE);
            lottotexaslinearLayout.setVisibility(View.GONE);
            texastwosteplinearLayout.setVisibility(View.GONE);
            pick3linearLayout.setVisibility(View.GONE);
            daily4linearLayout.setVisibility(View.GONE);
            cash_fivelinearLayout.setVisibility(View.GONE);
            allornothinglinearLayout.setVisibility(View.GONE);
        } else {
            dypowerprice.setVisibility(View.GONE);
            dylottoPrice.setVisibility(View.GONE);
            dymegaballPrice.setVisibility(View.GONE);
            dytexastwo.setVisibility(View.GONE);
            dypick3.setVisibility(View.GONE);
            dydaily4.setVisibility(View.GONE);
            dycashfive.setVisibility(View.GONE);
            dyallornothing.setVisibility(View.GONE);

            powerlinearLayout.setVisibility(View.VISIBLE);
            megamilionslinearLayout.setVisibility(View.VISIBLE);
            lottotexaslinearLayout.setVisibility(View.VISIBLE);
            texastwosteplinearLayout.setVisibility(View.VISIBLE);
            pick3linearLayout.setVisibility(View.VISIBLE);
            daily4linearLayout.setVisibility(View.VISIBLE);
            cash_fivelinearLayout.setVisibility(View.VISIBLE);
            allornothinglinearLayout.setVisibility(View.VISIBLE);

            Animation moveanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation);
            allornothinglinearLayout.startAnimation(moveanim);
            allornothinglinearLayout.startAnimation(moveanim);

        }

//        dyallornothing.setText(allornothingtexts[currentIndex]);
//        dycashfive.setText(cashfivetexts[currentIndex]);
//        dytexastwo.setText(texastwotexts[currentIndex]);
//        dymegaballPrice.setText(megaballPricetexts[currentIndex]);
//        dylottoPrice.setText(lottoPricetexts[currentIndex]);
//        dypick3.setText(pick3texts[currentIndex]);
//        dydaily4.setText(daily4texts[currentIndex]);

        currentIndex++;


    }

    private TextView createTextView(Context context, String text, boolean lastnumber, String lotteyname) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(10);
        int widthInDp = 16;
        int heightInDp = 16;
        float scale = context.getResources().getDisplayMetrics().density;
        int widthInPixels = (int) (widthInDp * scale + 0.5f);
        int heightInPixels = (int) (heightInDp * scale + 0.5f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthInPixels, heightInPixels);
        textView.setLayoutParams(layoutParams);
        layoutParams.setMargins(0, 0, 3, 5);
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(null, Typeface.BOLD);
        if (lastnumber) {
            if (lotteyname.equals("power")) {
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.circle_border_maroon_bg);
            } else if (lotteyname.equals("megamilion")) {
                textView.setTextColor(getResources().getColor(R.color.textblue));
                textView.setBackgroundResource(R.drawable.circle_border_yellow_bg);
            } else if (lotteyname.equals("texastwo")) {
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.circle_border_lightmaroon_bg);
            } else if (lotteyname.equals("pick3")) {
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.fireball);
                textView.setGravity(Gravity.START);
                textView.setPadding(4, 2, 0, 0);
            } else if (lotteyname.equals("daily4")) {
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.fireball);
                textView.setGravity(Gravity.START);
                textView.setPadding(4, 2, 0, 0);
            } else {
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setBackgroundResource(R.drawable.circle_border);
            }

        } else {
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setBackgroundResource(R.drawable.circle_border);

        }
        return textView;
    }

    private void changeScrren(int totalBoxes) {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.parent);
        int w = displayMetrics.widthPixels;
        int h = displayMetrics.heightPixels;
        mainLayout.setRotation(270.0f);
        mainLayout.setTranslationX((w - h) / 2);
        mainLayout.setTranslationY((h - w) / 2);
        ViewGroup.LayoutParams lp = mainLayout.getLayoutParams();
        lp.height = w;
        lp.width = h;
        mainLayout.requestLayout();
        setPortraitBoxes(totalBoxes);
    }

    private void setHeightofHeader(int dp) {
        RelativeLayout mainLayout = findViewById(R.id.header);
        int newHeightInDp = dp;
        float scale = getResources().getDisplayMetrics().density;
        int newHeightInPixels = (int) (newHeightInDp * scale + 0.5f);
        ViewGroup.LayoutParams layoutParams = mainLayout.getLayoutParams();
        layoutParams.height = newHeightInPixels;
        mainLayout.setLayoutParams(layoutParams);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainactivityPortraitActivity.this, ChooseScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}