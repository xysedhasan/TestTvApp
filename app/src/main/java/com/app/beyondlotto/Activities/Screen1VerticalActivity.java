package com.app.beyondlotto.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.beyondlotto.Model.AppRepository;
import com.app.beyondlotto.Model.Game;
import com.app.beyondlotto.Model.GridAdapterVertical;
import com.app.beyondlotto.Model.UserNew;
import com.app.beyondlotto.R;

import java.util.ArrayList;

public class Screen1VerticalActivity extends AppCompatActivity {

    static GridView gridView;
    private static final String TAG = "MainActivity";
    ArrayList<Game> games = new ArrayList<>();
    RelativeLayout footerRelative;
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
        setContentView(R.layout.activity_screen1_vertical);

//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                "MyApp::MyWakelockTag");
//        wakeLock.acquire();
//        init();
//
//        int width = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
//        int height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
//
//
//        gridView.setVerticalScrollBarEnabled(false);
//
//        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
//
//
//        AppRepository.getGames(this, getApplicationContext(), "screen1", (status1) -> {
//            if (status1) {
//                AppRepository.getUser(Screen1VerticalActivity.this, getApplicationContext(), "screen1", (status, user) -> {
//                    if (status) {
//                        AppRepository.getGamesofUser(this, user, getApplicationContext(), "screen1",(type,img)->{
//
//                        });
//                        setTextSizes(user);
//                        showHideHeader(user.getScreen1().isShow_header());
//                        setBoxes(user.getScreen1().getOrientation(), user.getScreen1().getTotal_boxes());
//                    }
//                });
//            }
//        });
//
//        //item click listner
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view, int position, long id) {
//            }
//        });
//
//        AppRepository.getGlobalPrices(getApplicationContext(), (prices) -> {
//            powerprice.setText(prices.getPower_ball());
//            lottoPrice.setText(prices.getTexas_loto());
//            megaballPrice.setText(prices.getMega_ball());
//            texastwo.setText(prices.getTexas_two_step());
//            pick3.setText(prices.getPick_3());
//            daily4.setText(prices.getDaily_4());
//            cashfive.setText(prices.getCash_five());
//            allornothing.setText(prices.getAll_or_nothing());
//        });
        // showHideHeader(getApplicationContext());

    }

    public static void initrecycler(Context context, ArrayList<Game> games, ArrayList<Boolean> animateArr, Boolean showheader, String orientation, int totalboxes, String empty_box, String imageurl) {
        gridView.setVerticalScrollBarEnabled(false);

        sizeoflyt = parent.getHeight() ;
        GridAdapterVertical adpter = new GridAdapterVertical(context, games, sizeoflyt, animateArr, "vertical", totalboxes, empty_box, imageurl);
        gridView.setAdapter(adpter);
        pbar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    public void setBoxes(String screenorientation, int totalboxes) {


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
        } else {
            gridView.setNumColumns(3);
        }


    }

    private void init() {
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
        gridView = (GridView) findViewById(R.id.gridview);
        gridRelative = findViewById(R.id.gridrltv);
        footerRelative = findViewById(R.id.bottomrltv);
        headerrltv = findViewById(R.id.header);

        powerprice = findViewById(R.id.powerbalprice);
        lottoPrice = findViewById(R.id.lottotexas);
        megaballPrice = findViewById(R.id.megamilions);
        texastwo = findViewById(R.id.texastwostep);
        pick3 = findViewById(R.id.pick3);
        daily4 = findViewById(R.id.daily4);
        cashfive = findViewById(R.id.cash_five);
        allornothing = findViewById(R.id.allornothing);
        parent = findViewById(R.id.parent);
        // rotatescreen(parent);
        dypowerprice = findViewById(R.id.powerbalpriceday);
        dylottoPrice = findViewById(R.id.lottotexasday);
        dymegaballPrice = findViewById(R.id.megamilionsday);
        dytexastwo = findViewById(R.id.texastwostepday);
        dypick3 = findViewById(R.id.pick3day);
        dydaily4 = findViewById(R.id.daily4day);
        dycashfive = findViewById(R.id.cash_fiveday);
        dyallornothing = findViewById(R.id.allornothingday);


    }

    private void setTextSizes(UserNew user) {
        if (user.getScreen1().getOrientation().equals("landscape")) {
        } else {
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

    private void showHideHeader(boolean showheader) {
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


    private void rotatescreen(RelativeLayout mainLayout) {
        int w = mainLayout.getWidth();
        int h = mainLayout.getHeight();

        mainLayout.setRotation(270.0f);
//        mainLayout.setTranslationX((w - h) / 2);
//        mainLayout.setTranslationY((h - w) / 2);

//        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) mainLayout.getLayoutParams();
//        lp.height = w;
//        lp.width = h;
//        mainLayout.requestLayout();
    }


}