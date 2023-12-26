package com.app.beyondlottotv.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.beyondlottotv.Model.AppRepository;
import com.app.beyondlottotv.Model.Prefrences;
import com.app.beyondlottotv.Model.RecyclerviewAdapter;
import com.app.beyondlottotv.Model.ScrapeArkansasJackpot;
import com.app.beyondlottotv.Model.ScrapeArkansasWebsite;
import com.app.beyondlottotv.Model.ScrapeCaliforniaJackpot;
import com.app.beyondlottotv.Model.ScrapeCaliforniaWebsite;
import com.app.beyondlottotv.Model.ScrapeFloridaJackpot;
import com.app.beyondlottotv.Model.ScrapeFloridaWebsiteTask;
import com.app.beyondlottotv.Model.ScrapeGeorgiaJackpot;
import com.app.beyondlottotv.Model.ScrapeGeorgiaWebsite;
import com.app.beyondlottotv.Model.ScrapeIdahoJackpot;
import com.app.beyondlottotv.Model.ScrapeIdahoWebsite;
import com.app.beyondlottotv.Model.ScrapeWebsiteTask;
import com.app.beyondlottotv.Model.Screen1;
import com.app.beyondlottotv.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainactivityPortraitActivity extends AppCompatActivity {
    static GridView gridView;
    String region;
    LinearLayout[] linearLogos;
    int headerlogosize = 0;
    ImageView[] imageLogos;
    TextView[] priceLogos;
    LinearLayout[] linearWinings;
    TextView[] tvDaysLogos;
    RelativeLayout logolyt, footerRelative;
    static RelativeLayout gridRelative;
    LinearLayout texasheaderlyt, georgiahaderlyt;
    static int sizeoflyt = 0;
    static ProgressBar pbar;
    static RelativeLayout headerrltv;
    String screenorientation = "landscape";
    static String from = "";
    static RelativeLayout parent;
    String accountType = "";
    private boolean isTextSwapRunning = false;
    LinearLayout headerlayout;
    private DisplayMetrics displayMetrics;
    public static RecyclerView gridRecyclerView;
    public static RecyclerviewAdapter recyclerViewadapter;
    public static GridLayoutManager gridLayoutManager;
    static int spancount = 0;
    boolean firsttime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainportrait);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getSetUserData(Context context) {
        AppRepository.getUser(MainactivityPortraitActivity.this, MainactivityPortraitActivity.this, from, (status, user) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    accountType = user.getAccount_type();
                    region = user.getRegion();
                    if (from.equals("screen1")) {
                        showHideHeader(user.getScreen1().isShow_header());
                        startTextSwap();
                        if (user.getScreen1().getOrientation().equals("portrait")) {
                            changeScrren(user.getScreen1().getTotal_boxes(), context);
                            setHeightofHeader(83);
                            setheaderBoxes("portrait");
                        } else {
                            setBoxes(user.getScreen1().getTotal_boxes(), context);
                            setHeightofHeader(75);
                            setheaderBoxes("horizontal");
                        }
                    } else if (from.equals("screen2")) {
                        showHideHeader(user.getScreen2().isShow_header());
                        if (user.getScreen2().getOrientation().equals("portrait")) {
                            changeScrren(user.getScreen2().getTotal_boxes(), context);
                            setHeightofHeader(83);
                            setheaderBoxes("portrait");
                        } else {
                            setBoxes(user.getScreen2().getTotal_boxes(), context);
                            setHeightofHeader(75);
                            setheaderBoxes("horizontal");
                        }
                    } else if (from.equals("screen3")) {
                        showHideHeader(user.getScreen3().isShow_header());
                        if (user.getScreen3().getOrientation().equals("portrait")) {
                            changeScrren(user.getScreen3().getTotal_boxes(), context);
                            setHeightofHeader(83);
                            setheaderBoxes("portrait");
                        } else {
                            setBoxes(user.getScreen3().getTotal_boxes(), context);
                            setHeightofHeader(75);
                            setheaderBoxes("horizontal");
                        }
                    } else if (from.equals("screen4")) {
                        showHideHeader(user.getScreen4().isShow_header());
                        if (user.getScreen4().getOrientation().equals("portrait")) {
                            changeScrren(user.getScreen4().getTotal_boxes(), context);
                            setHeightofHeader(83);
                            setheaderBoxes("portrait");
                        } else {
                            setBoxes(user.getScreen4().getTotal_boxes(), context);
                            setHeightofHeader(75);
                            setheaderBoxes("horizontal");
                        }
                    }

                    linearLogos = new LinearLayout[headerlogosize];
                    for (int i = 0; i < headerlogosize; i++) {
                        int linearId = getResources().getIdentifier("linearlogo" + (i + 1), "id", getPackageName());
                        linearLogos[i] = findViewById(linearId);

                    }

                    imageLogos = new ImageView[headerlogosize];
                    for (int i = 0; i < headerlogosize; i++) {
                        int imageId = getResources().getIdentifier("imagelogo" + (i + 1), "id", getPackageName());
                        imageLogos[i] = findViewById(imageId);
                    }

                    priceLogos = new TextView[headerlogosize];
                    for (int i = 0; i < headerlogosize; i++) {
                        int priceId = getResources().getIdentifier("pricelogo" + (i + 1), "id", getPackageName());
                        priceLogos[i] = findViewById(priceId);
                    }

                    linearWinings = new LinearLayout[headerlogosize];
                    for (int i = 0; i < headerlogosize; i++) {
                        int linearWinId = getResources().getIdentifier("linearwining" + (i + 1), "id", getPackageName());
                        linearWinings[i] = findViewById(linearWinId);
                    }

                    tvDaysLogos = new TextView[headerlogosize];
                    for (int i = 0; i < headerlogosize; i++) {
                        int tvDaysId = getResources().getIdentifier("tvdayslogo" + (i + 1), "id", getPackageName());
                        tvDaysLogos[i] = findViewById(tvDaysId);
                    }

                    if (user.getRegion().equals("Texas")) {
                        getSetGlobalPrices();
                        getsettexasImages();
                        getsetTexasDays();
                        getSetGlobaldaysandwinnings();
                    } else if (user.getRegion().equals("Georgia")) {
                        getSetGeorgiaGlobalPrices();
                        getsetGeorgiaImages();
                        getsetGeorgiaDays();
                        getSetGeorgiaWinnings();
                    } else if (user.getRegion().equals("Arkansas")) {
                        setArkansasImages();
                        setArkansasDays();
                        setArkansasGlobalPrices();
                        getSetArkansasWinnings();
                    } else if (user.getRegion().equals("California")) {
                        setCaliforniaDays();
                        getSetCaliforniaGlobalPrices();
                        setCaliforniaImages();
                        getSetCaliforniaWinnings();
                    } else if (user.getRegion().equals("Idaho")) {
                        setIdahoDays();
                        setIdahoImages();
                        getSetIdahoGlobalPrices();
                        getSetIdahoGlobaldaysandwinnings();
                    }else if (user.getRegion().equals("Florida")) {
                        setFloridaDays();
                        setFloridaImages();
                        getSetFloridaGlobalPrices();
                        getSetFloridaGlobaldaysandwinnings();
                    }
                });

                AppRepository.getGamesofUser(this, user, getApplicationContext(), from, (type, img) -> {
                });
            } else {
                AppRepository.checklogout(this, false, MainactivityPortraitActivity.this);

            }
        });
    }

    private void setFloridaDays() {
        if (headerlogosize == 5) {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("TUE | FRI");
            tvDaysLogos[2].setText("MON | WED");
            tvDaysLogos[3].setText("MON | WED");
            tvDaysLogos[4].setText("MON | WED");
        } else {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("TUE | FRI");
            tvDaysLogos[2].setText("MON | WED");
            tvDaysLogos[3].setText("MON | WED");
            tvDaysLogos[4].setText("MON | WED");
            tvDaysLogos[5].setText("MON | WED");
            tvDaysLogos[6].setText("MON | WED");
            tvDaysLogos[7].setText("MON | WED");
//            tvDaysLogos[8].setText("MON | WED");
//            tvDaysLogos[9].setText("MON | WED");
        }
    }

    private void getSetFloridaGlobaldaysandwinnings() {
        
    }

    private void getSetFloridaGlobalPrices() {
        AppRepository.getFloridaGlobalPrices(getApplicationContext(), (prices, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    if (headerlogosize == 5) {
                        priceLogos[0].setText(prices.getPowerball());
                        priceLogos[1].setText(prices.getMegamillions());
                        priceLogos[2].setText(prices.getLotto());
                        priceLogos[3].setText(prices.getCash4life());
                        priceLogos[4].setText(prices.getJackpot());
                    } else {
                        priceLogos[0].setText(prices.getPowerball());
                        priceLogos[1].setText(prices.getMegamillions());
                        priceLogos[2].setText(prices.getLotto());
                        priceLogos[3].setText(prices.getCash4life());
                        priceLogos[4].setText(prices.getJackpot());
                        priceLogos[5].setText(prices.getCashpop());
                        priceLogos[6].setText(prices.getFantasy5());
                        priceLogos[7].setText(prices.getPick5());
//                        priceLogos[8].setText(prices.getPick4());
                    }
                });
            }
        });
    }

    private void setFloridaImages() {
        if (headerlogosize == 5){
            imageLogos[0].setImageResource(R.drawable.powerballf);
            imageLogos[1].setImageResource(R.drawable.megamillionf);
            imageLogos[2].setImageResource(R.drawable.lottof);
            imageLogos[3].setImageResource(R.drawable.cash4lifef);
            imageLogos[4].setImageResource(R.drawable.jackpotf);
        }else {
            imageLogos[0].setImageResource(R.drawable.powerballf);
            imageLogos[1].setImageResource(R.drawable.megamillionf);
            imageLogos[2].setImageResource(R.drawable.lottof);
            imageLogos[3].setImageResource(R.drawable.cash4lifef);
            imageLogos[4].setImageResource(R.drawable.jackpotf);
            imageLogos[5].setImageResource(R.drawable.cashpopf);
            imageLogos[6].setImageResource(R.drawable.fantasy5f);
            imageLogos[7].setImageResource(R.drawable.pick5f);
//            imageLogos[8].setImageResource(R.drawable.pick4f);
//            imageLogos[9].setImageResource(R.drawable.pick3f);
//            imageLogos[10].setImageResource(R.drawable.pick2f);
        }
    }

    private void getsetGeorgiaDays() {
        if (headerlogosize == 5) {
            tvDaysLogos[0].setText("TUE |FRI");
            tvDaysLogos[1].setText("MON | WED | SAT");
            tvDaysLogos[2].setText("MON | WED");
            tvDaysLogos[3].setText("MON | WED");
            tvDaysLogos[4].setText("MON | WED");
        } else {
            tvDaysLogos[0].setText("TUE |FRI");
            tvDaysLogos[1].setText("MON | WED | SAT");
            tvDaysLogos[2].setText("MON | WED");
            tvDaysLogos[3].setText("MON | WED");
            tvDaysLogos[4].setText("MON | WED");
            tvDaysLogos[5].setText("MON | WED");
            tvDaysLogos[6].setText("MON | WED");
            tvDaysLogos[7].setText("MON | WED");
        }
    }

    private void getsetGeorgiaImages() {
        if (headerlogosize == 5) {
            imageLogos[0].setImageResource(R.drawable.megamillionsg);
            imageLogos[1].setImageResource(R.drawable.powerballg);
            imageLogos[2].setImageResource(R.drawable.cash3);
            imageLogos[3].setImageResource(R.drawable.cash4);
            imageLogos[4].setImageResource(R.drawable.keno);
        } else {
            imageLogos[0].setImageResource(R.drawable.megamillionsg);
            imageLogos[1].setImageResource(R.drawable.powerballg);
            imageLogos[2].setImageResource(R.drawable.cash3);
            imageLogos[3].setImageResource(R.drawable.cash4);
            imageLogos[4].setImageResource(R.drawable.keno);
            imageLogos[5].setImageResource(R.drawable.fantasy5);
            imageLogos[6].setImageResource(R.drawable.georgiafive);
            imageLogos[7].setImageResource(R.drawable.jumbobucks);
        }
    }

    private void getSetGeorgiaGlobalPrices() {
        AppRepository.getGeorgiaGlobalPrices(getApplicationContext(), (prices, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    if (headerlogosize == 5) {
                        priceLogos[0].setText(prices.getMegamillions());
                        priceLogos[1].setText(prices.getPowerball());
                        priceLogos[2].setText(prices.getCash3());
                        priceLogos[3].setText(prices.getCash4());
//                        priceLogos[2].setText(prices.getCashpop());
                        priceLogos[4].setText(prices.getKeno());
                    } else {

                        priceLogos[0].setText(prices.getMegamillions());
                        priceLogos[1].setText(prices.getPowerball());
                        priceLogos[2].setText(prices.getCash3());
                        priceLogos[3].setText(prices.getCash4());
//                        priceLogos[2].setText(prices.getCashpop());
                        priceLogos[4].setText(prices.getKeno());
                        priceLogos[5].setText(prices.getFantasy5());
//                        priceLogos[5].setText(prices.getCash4life());
                        priceLogos[6].setText(prices.getGeorgiafive());
                        priceLogos[7].setText(prices.getJumbobucks());
                    }
                });
            }
        });
    }


    private void getsetTexasDays() {
        if (headerlogosize == 5) {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("MON | WED | SAT");
            tvDaysLogos[2].setText("TUE | FRI");
            tvDaysLogos[3].setText("MON | THU");
            tvDaysLogos[4].setText("MON | SAT");
        } else {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("MON | WED | SAT");
            tvDaysLogos[2].setText("TUE | FRI");
            tvDaysLogos[3].setText("MON | THU");
            tvDaysLogos[4].setText("MON | SAT");
            tvDaysLogos[5].setText("MON | SAT");
            tvDaysLogos[6].setText("MON | SAT");
            tvDaysLogos[7].setText("MON | SAT");
        }
    }

    private void getsettexasImages() {
        if (headerlogosize == 5) {
            imageLogos[0].setImageResource(R.drawable.powerball);
            imageLogos[1].setImageResource(R.drawable.texaslotto);
            imageLogos[2].setImageResource(R.drawable.megaball);
            imageLogos[3].setImageResource(R.drawable.texastwosteps);
            imageLogos[4].setImageResource(R.drawable.picktheree);
        } else {
            imageLogos[0].setImageResource(R.drawable.powerball);
            imageLogos[1].setImageResource(R.drawable.texaslotto);
            imageLogos[2].setImageResource(R.drawable.megaball);
            imageLogos[3].setImageResource(R.drawable.texastwosteps);
            imageLogos[4].setImageResource(R.drawable.picktheree);
            imageLogos[5].setImageResource(R.drawable.dailyfour);
            imageLogos[6].setImageResource(R.drawable.cashfive);
            imageLogos[7].setImageResource(R.drawable.allornothing);
        }
    }

    private void setArkansasImages() {
        if (headerlogosize == 5) {
            imageLogos[0].setImageResource(R.drawable.powerballa);
            imageLogos[1].setImageResource(R.drawable.megamilliona);
            imageLogos[2].setImageResource(R.drawable.lottoa);
            imageLogos[3].setImageResource(R.drawable.naturalstatea);
            imageLogos[4].setImageResource(R.drawable.luckyforlifea);
        } else {
            imageLogos[0].setImageResource(R.drawable.powerballa);
            imageLogos[1].setImageResource(R.drawable.megamilliona);
            imageLogos[2].setImageResource(R.drawable.lottoa);
            imageLogos[3].setImageResource(R.drawable.naturalstatea);
            imageLogos[4].setImageResource(R.drawable.luckyforlifea);
            imageLogos[5].setImageResource(R.drawable.cash3a);
            imageLogos[6].setImageResource(R.drawable.cash4a);
            imageLogos[7].setImageResource(R.drawable.ara);
        }
    }


    private void setArkansasGlobalPrices() {
        AppRepository.getArkansasGlobalPrices(getApplicationContext(), (prices, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    if (headerlogosize == 5) {
                        priceLogos[0].setText(prices.getPowerball());
                        priceLogos[1].setText(prices.getMegamillion());
                        priceLogos[2].setText(prices.getLotto());
                        priceLogos[3].setText(prices.getNaturalstate());
                        priceLogos[4].setText(prices.getLuckyforlife());
                    } else {
                        priceLogos[0].setText(prices.getPowerball());
                        priceLogos[1].setText(prices.getMegamillion());
                        priceLogos[2].setText(prices.getLotto());
                        priceLogos[3].setText(prices.getNaturalstate());
                        priceLogos[4].setText(prices.getLuckyforlife());
                        priceLogos[5].setText(prices.getCash3());
                        priceLogos[6].setText(prices.getCash4());
                        priceLogos[7].setText(prices.getAr());
                    }

                });
            }
        });
    }

    private void setArkansasDays() {
        if (headerlogosize == 5) {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("TUE | FRI");
            tvDaysLogos[2].setText("MON | THU");
            tvDaysLogos[3].setText("TUE | FRI");
            tvDaysLogos[4].setText("MON | SAT");
        } else {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("TUE | FRI");
            tvDaysLogos[2].setText("MON | THU");
            tvDaysLogos[3].setText("TUE | FRI");
            tvDaysLogos[4].setText("MON | SAT");
            tvDaysLogos[5].setText("MON | SAT");
            tvDaysLogos[6].setText("MON | SAT");
            tvDaysLogos[7].setText("MON | SAT");
        }
    }

    private void setCaliforniaDays() {
        if (headerlogosize == 5) {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("TUE | FRI");
            tvDaysLogos[2].setText("MON | THU");
            tvDaysLogos[3].setText("TUE | FRI");
            tvDaysLogos[4].setText("MON | SAT");
        } else {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("TUE | FRI");
            tvDaysLogos[2].setText("MON | THU");
            tvDaysLogos[3].setText("TUE | FRI");
            tvDaysLogos[4].setText("MON | SAT");
            tvDaysLogos[5].setText("MON | SAT");
            tvDaysLogos[6].setText("MON | SAT");
            tvDaysLogos[7].setText("MON | SAT");
        }
    }


    private void setIdahoDays() {
        if (headerlogosize == 5) {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("TUE | FRI");
            tvDaysLogos[2].setText("MON | THU");
            tvDaysLogos[3].setText("TUE | FRI");
            tvDaysLogos[4].setText("MON | SAT");
        } else {
            tvDaysLogos[0].setText("MON | WED | SAT");
            tvDaysLogos[1].setText("TUE | FRI");
            tvDaysLogos[2].setText("MON | THU");
            tvDaysLogos[3].setText("TUE | FRI");
            tvDaysLogos[4].setText("MON | SAT");
            tvDaysLogos[5].setText("MON | SAT");
            tvDaysLogos[6].setText("MON | SAT");
        }
    }

    private void getSetIdahoGlobalPrices() {
        AppRepository.getIdahoGlobalPrices(getApplicationContext(), (prices, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    if (headerlogosize == 5) {
                        priceLogos[0].setText(prices.getPowerball());
                        priceLogos[1].setText(prices.getMegamillion());
                        priceLogos[2].setText(prices.getLottoAmerica());
                        priceLogos[3].setText(prices.getLuckyforlife());
                        priceLogos[4].setText(prices.getIdahocash());
                    } else {
                        priceLogos[0].setText(prices.getPowerball());
                        priceLogos[1].setText(prices.getMegamillion());
                        priceLogos[2].setText(prices.getLottoAmerica());
                        priceLogos[3].setText(prices.getLuckyforlife());
                        priceLogos[4].setText(prices.getIdahocash());
                        priceLogos[5].setText(prices.getFivestar());
                        priceLogos[6].setText(prices.getWeeklygrand());
                    }

                });
            }
        });
    }

    private void setIdahoImages() {
        if (headerlogosize == 5) {
            imageLogos[0].setImageResource(R.drawable.powerballi);
            imageLogos[1].setImageResource(R.drawable.megamillioni);
            imageLogos[2].setImageResource(R.drawable.lottoamericai);
            imageLogos[3].setImageResource(R.drawable.luckyforlifei);
            imageLogos[4].setImageResource(R.drawable.idahocashi);
        } else {
            imageLogos[0].setImageResource(R.drawable.powerballi);
            imageLogos[1].setImageResource(R.drawable.megamillioni);
            imageLogos[2].setImageResource(R.drawable.lottoamericai);
            imageLogos[3].setImageResource(R.drawable.luckyforlifei);
            imageLogos[4].setImageResource(R.drawable.idahocashi);
            imageLogos[5].setImageResource(R.drawable.fivestari);
            imageLogos[6].setImageResource(R.drawable.weeklygrand);
        }
    }


    private void setCaliforniaImages() {
        if (headerlogosize == 5) {
            imageLogos[0].setImageResource(R.drawable.powerballc);
            imageLogos[1].setImageResource(R.drawable.megamillionc);
            imageLogos[2].setImageResource(R.drawable.superlottoc);
            imageLogos[3].setImageResource(R.drawable.fantasy5c);
            imageLogos[4].setImageResource(R.drawable.daily3c);
        } else {
            imageLogos[0].setImageResource(R.drawable.powerballc);
            imageLogos[1].setImageResource(R.drawable.megamillionc);
            imageLogos[2].setImageResource(R.drawable.superlottoc);
            imageLogos[3].setImageResource(R.drawable.fantasy5c);
            imageLogos[4].setImageResource(R.drawable.daily3c);
            imageLogos[5].setImageResource(R.drawable.daily4c);
            imageLogos[6].setImageResource(R.drawable.dailyderbyc);
            imageLogos[7].setImageResource(R.drawable.hotspotc);
        }
    }

    private void getSetCaliforniaGlobalPrices() {
        AppRepository.getCaliforniaGlobalPrices(getApplicationContext(), (prices, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    if (headerlogosize == 5) {
                        priceLogos[0].setText(prices.getPowerball());
                        priceLogos[1].setText(prices.getMegamillion());
                        priceLogos[2].setText(prices.getSuperlotto());
                        priceLogos[3].setText(prices.getFantasy5());
                        priceLogos[4].setText(prices.getDaily3());
                    } else {
                        priceLogos[0].setText(prices.getPowerball());
                        priceLogos[1].setText(prices.getMegamillion());
                        priceLogos[2].setText(prices.getSuperlotto());
                        priceLogos[3].setText(prices.getFantasy5());
                        priceLogos[4].setText(prices.getDaily3());
                        priceLogos[5].setText(prices.getDaily4());
                        priceLogos[6].setText(prices.getDailyderby());
                        priceLogos[7].setText(prices.getHotspot());
                    }

                });
            }
        });
    }

    private void getSetGlobalPrices() {
        AppRepository.getGlobalPrices(getApplicationContext(), (prices, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    if (headerlogosize == 5) {
                        priceLogos[0].setText(prices.getPower_ball());
                        priceLogos[1].setText(prices.getTexas_loto());
                        priceLogos[2].setText(prices.getMega_ball());
                        priceLogos[3].setText(prices.getTexas_two_step());
                        priceLogos[4].setText(prices.getPick_3());
                    } else {
                        priceLogos[0].setText(prices.getPower_ball());
                        priceLogos[1].setText(prices.getTexas_loto());
                        priceLogos[2].setText(prices.getMega_ball());
                        priceLogos[3].setText(prices.getTexas_two_step());
                        priceLogos[4].setText(prices.getPick_3());
                        priceLogos[5].setText(prices.getDaily_4());
                        priceLogos[6].setText(prices.getCash_five());
                        priceLogos[7].setText(prices.getAll_or_nothing());
                    }

                });
            }
        });
    }


    private void getSetCaliforniaWinnings() {
        AppRepository.getCaliforniaWinings(getApplicationContext(), (winning, status) -> {
            if (status) {
                if (winning.getPowerballArray() != null) {
                    String[] numbersArray1 = {};
                    numbersArray1 = winning.getPowerballArray().split(" ");
                    linearWinings[0].removeAllViews();
                    for (int i = 0; i < numbersArray1.length - 1; i++) {
                        boolean last = false;
                        if (i == numbersArray1.length - 2) {
                            last = true;
                        }
                        TextView textView = createTextViewCalifornia(this, numbersArray1[i], last, "powerc");
                        linearWinings[0].addView(textView);
                    }
                    if (numbersArray1.length > 7) {
                        linearWinings[0].setVisibility(View.INVISIBLE);
                    }
                }

                if (winning.getMegaMillionsArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getMegaMillionsArray().split(" ");
                    linearWinings[1].removeAllViews();
                    for (int i = 0; i < numbersArray2.length - 1; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 2) {
                            last = true;
                        }
                        TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "megamilionc");
                        linearWinings[1].addView(textView);
                    }
                    if (numbersArray2.length > 7) {
                        linearWinings[1].setVisibility(View.INVISIBLE);
                    }
                }


                if (winning.getSuperlottoArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getSuperlottoArray().split(" ");
                    linearWinings[2].removeAllViews();
                    for (int i = 0; i < numbersArray2.length - 1; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 2) {
                            last = true;
                        }
                        TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "superlottoc");
                        linearWinings[2].addView(textView);
                    }
                    if (numbersArray2.length > 7) {
                        linearWinings[2].setVisibility(View.INVISIBLE);
                    }
                }

                if (winning.getFantasy5Array() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getFantasy5Array().split(" ");
                    linearWinings[3].removeAllViews();
                    for (int i = 0; i < numbersArray2.length; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 1) {
                            last = true;
                        }
                        TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "fantasy5");
                        linearWinings[3].addView(textView);
                    }
                    if (numbersArray2.length > 7) {
                        linearWinings[3].setVisibility(View.INVISIBLE);
                    }
                }

                if (winning.getDaily3dayArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getDaily3dayArray().split(" ");
                    linearWinings[4].removeAllViews();
                    for (int i = 0; i < numbersArray2.length; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 1) {
                            last = true;
                        }
                        TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "daily3day");
                        linearWinings[4].addView(textView);
                    }
                    if (numbersArray2.length > 7) {
                        linearWinings[4].setVisibility(View.INVISIBLE);
                    }
                }
                if (headerlogosize > 5) {
                    if (winning.getDaily3eveningArray() != null) {
                        String[] numbersArray2 = {};
                        numbersArray2 = winning.getDaily4Array().split(" ");
                        linearWinings[5].removeAllViews();
                        for (int i = 0; i < numbersArray2.length; i++) {
                            boolean last = false;
                            if (i == numbersArray2.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "daily4");
                            linearWinings[5].addView(textView);
                        }
                        if (numbersArray2.length > 7) {
                            linearWinings[5].setVisibility(View.INVISIBLE);
                        }
                    }


                }
            }
        });
    }

//    imageLogos[0].setImageResource(R.drawable.megamillionsg);
//    imageLogos[1].setImageResource(R.drawable.powerballg);
//    imageLogos[2].setImageResource(R.drawable.cash3);
//    imageLogos[3].setImageResource(R.drawable.cash4);
//    imageLogos[4].setImageResource(R.drawable.keno);
//    imageLogos[5].setImageResource(R.drawable.fantasy5);
//    imageLogos[6].setImageResource(R.drawable.georgiafive);
//    imageLogos[7].setImageResource(R.drawable.jumbobucks);
    private void getSetGeorgiaWinnings() {
        AppRepository.getGeorgiaWinings(getApplicationContext(), (winning, status) -> {
            if (status) {
                if (winning.getMegaMillionsArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getMegaMillionsArray().split(" ");
                    linearWinings[0].removeAllViews();
                    for (int i = 0; i < numbersArray2.length; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 1) {
                            last = true;
                        }
                        TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "megamilliong");
                        linearWinings[0].addView(textView);
                    }
                    if (numbersArray2.length > 5) {
                        linearWinings[0].setVisibility(View.INVISIBLE);
                    }
                }

                if (winning.getPowerballArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getPowerballArray().split(" ");
                    linearWinings[1].removeAllViews();
                    for (int i = 0; i < numbersArray2.length; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 1) {
                            last = true;
                        }
                        TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "powerg");
                        linearWinings[1].addView(textView);
                    }
                    if (numbersArray2.length > 5) {
                        linearWinings[1].setVisibility(View.INVISIBLE);
                    }
                }

                if (winning.getCash3dayArray() != null) {
                    String[] numbersArray1 = {};
                    numbersArray1 = winning.getCash3dayArray().split(" ");
                    linearWinings[2].removeAllViews();
                    for (int i = 0; i < numbersArray1.length; i++) {
                        boolean last = false;
                        if (i == numbersArray1.length - 1) {
                            last = true;
                        }
                        TextView textView = createTextViewCalifornia(this, numbersArray1[i], last, "cash3dayg");
                        linearWinings[2].addView(textView);
                    }
                    if (numbersArray1.length > 5) {
                        linearWinings[2].setVisibility(View.INVISIBLE);
                    }
                }

                if (winning.getCash4dayArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getCash4dayArray().split(" ");
                    linearWinings[3].removeAllViews();
                    for (int i = 0; i < numbersArray2.length ; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 1) {
                            last = true;
                        }
                        TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "cash4dayg");
                        linearWinings[3].addView(textView);
                    }
                    if (numbersArray2.length > 5) {
                        linearWinings[3].setVisibility(View.INVISIBLE);
                    }
                }


//                if (winning.getCashpopDayArray() != null) {
//                    String[] numbersArray2 = {};
//                    numbersArray2 = winning.getCashpopDayArray().split(" ");
//                    linearWinings[2].removeAllViews();
//                    for (int i = 0; i < numbersArray2.length  ; i++) {
//                        boolean last = false;
//                        if (i == numbersArray2.length - 1) {
//                            last = true;
//                        }
//                        TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "cashpopg");
//                        linearWinings[2].addView(textView);
//                    }
//                    if (numbersArray2.length > 5) {
//                        linearWinings[2].setVisibility(View.INVISIBLE);
//                    }
//                }


                if (headerlogosize > 5) {
                    if (winning.getFantasy5Array() != null) {
                        String[] numbersArray2 = {};
                        numbersArray2 = winning.getFantasy5Array().split(" ");
                        linearWinings[5].removeAllViews();
                        for (int i = 0; i < numbersArray2.length; i++) {
                            boolean last = false;
                            if (i == numbersArray2.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "fantasy5g");
                            linearWinings[5].addView(textView);
                        }
                        if (numbersArray2.length > 5) {
                            linearWinings[5].setVisibility(View.INVISIBLE);
                        }
                    }


//                    if (winning.getCash4lifeArray() != null) {
//                        String[] numbersArray2 = {};
//                        numbersArray2 = winning.getCash4lifeArray().split(" ");
//                        linearWinings[5].removeAllViews();
//                        for (int i = 0; i < numbersArray2.length; i++) {
//                            boolean last = false;
//                            if (i == numbersArray2.length - 1) {
//                                last = true;
//                            }
//                            TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "cash4g");
//                            linearWinings[5].addView(textView);
//                        }
//                        if (numbersArray2.length > 5) {
//                            linearWinings[5].setVisibility(View.INVISIBLE);
//                        }
//                    }

                    if (winning.getGeorgia5dayArray() != null) {
                        String[] numbersArray2 = {};
                        numbersArray2 = winning.getGeorgia5dayArray().split(" ");
                        linearWinings[6].removeAllViews();
                        for (int i = 0; i < numbersArray2.length; i++) {
                            boolean last = false;
                            if (i == numbersArray2.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "georgiag");
                            linearWinings[6].addView(textView);
                        }
                        if (numbersArray2.length > 5) {
                            linearWinings[6].setVisibility(View.INVISIBLE);
                        }
                    }

                    if (winning.getJumbobucksArray() != null) {
                        String[] numbersArray2 = {};
                        numbersArray2 = winning.getJumbobucksArray().split(" ");
                        linearWinings[7].removeAllViews();
                        for (int i = 0; i < numbersArray2.length; i++) {
                            boolean last = false;
                            if (i == numbersArray2.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextViewCalifornia(this, numbersArray2[i], last, "jumbog");
                            linearWinings[7].addView(textView);
                        }
                        if (numbersArray2.length > 5) {
                            linearWinings[7].setVisibility(View.INVISIBLE);
                        }
                    }

                }
            }
        });
    }


    private void getSetArkansasWinnings() {
        AppRepository.getArkansasWinings(getApplicationContext(), (winning, status) -> {
            if (status) {
                if (winning.getPowerballArray() != null) {
                    String[] numbersArray1 = {};
                    numbersArray1 = winning.getPowerballArray().split(" ");
                    linearWinings[0].removeAllViews();
                    for (int i = 0; i < numbersArray1.length - 1; i++) {
                        boolean last = false;
                        if (i == numbersArray1.length - 2) {
                            last = true;
                        }
                        TextView textView = createTextViewArkansas(this, numbersArray1[i], last, "powera");
                        linearWinings[0].addView(textView);
                    }

                }

                if (winning.getMegaMillionsArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getMegaMillionsArray().split(" ");
                    linearWinings[1].removeAllViews();
                    for (int i = 0; i < numbersArray2.length - 1; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 2) {
                            last = true;
                        }
                        TextView textView = createTextViewArkansas(this, numbersArray2[i], last, "megamiliona");
                        linearWinings[1].addView(textView);
                    }

                }


                if (winning.getLottoArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getLottoArray().split(" ");
                    linearWinings[2].removeAllViews();
                    for (int i = 0; i < numbersArray2.length - 1; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 2) {
                            last = true;
                        }
                        TextView textView = createTextViewArkansas(this, numbersArray2[i], last, "lotto");
                        linearWinings[2].addView(textView);
                    }

                }

                if (winning.getNaturalstateArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getNaturalstateArray().split(" ");
                    linearWinings[3].removeAllViews();
                    for (int i = 0; i < numbersArray2.length; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 1) {
                            last = true;
                        }
                        TextView textView = createTextViewArkansas(this, numbersArray2[i], last, "naturalstate");
                        linearWinings[3].addView(textView);
                    }

                }

                if (winning.getLuckyforlifeArray() != null) {
                    String[] numbersArray2 = {};
                    numbersArray2 = winning.getLuckyforlifeArray().split(" ");
                    linearWinings[4].removeAllViews();
                    for (int i = 0; i < numbersArray2.length; i++) {
                        boolean last = false;
                        if (i == numbersArray2.length - 1) {
                            last = true;
                        }
                        TextView textView = createTextViewArkansas(this, numbersArray2[i], last, "luckyforlife");
                        linearWinings[4].addView(textView);
                    }

                }
                if (headerlogosize > 5) {
                    if (winning.getCash3dayArray() != null) {
                        String[] numbersArray2 = {};
                        numbersArray2 = winning.getCash3dayArray().split(" ");
                        linearWinings[5].removeAllViews();
                        for (int i = 0; i < numbersArray2.length; i++) {
                            boolean last = false;
                            if (i == numbersArray2.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextViewArkansas(this, numbersArray2[i], last, "cash3");
                            linearWinings[5].addView(textView);
                        }

                    }

                    if (winning.getCash4dayArray() != null) {
                        String[] numbersArray2 = {};
                        numbersArray2 = winning.getCash4dayArray().split(" ");
                        linearWinings[6].removeAllViews();
                        for (int i = 0; i < numbersArray2.length; i++) {
                            boolean last = false;
                            if (i == numbersArray2.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextViewArkansas(this, numbersArray2[i], last, "cash4");
                            linearWinings[6].addView(textView);
                        }

                    }

                }
            }
        });
    }

    //idaho winnings
    private void getSetIdahoGlobaldaysandwinnings() {
        AppRepository.getIdahoWinings(getApplicationContext(), (winning, status) -> {
            if (status) {
                this.runOnUiThread(() -> {
                    if (winning.getPowerballArray() != null) {
                        String[] numbersArray1 = {};
                        numbersArray1 = winning.getPowerballArray().split(" ");
                        linearWinings[0].removeAllViews();
                        for (int i = 0; i < numbersArray1.length; i++) {
                            boolean last = false;
                            if (i == numbersArray1.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray1[i], last, "poweri");
                            linearWinings[0].addView(textView);
                        }

                    }

                    if (winning.getMegaMillionsArray() != null) {
                        String[] numbersArray2 = {};
                        numbersArray2 = winning.getMegaMillionsArray().split(" ");
                        linearWinings[1].removeAllViews();
                        for (int i = 0; i < numbersArray2.length; i++) {
                            boolean last = false;
                            if (i == numbersArray2.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray2[i], last, "megamilioni");
                            linearWinings[1].addView(textView);
                        }

                    }

                    if (winning.getLottoAmericaArray() != null) {
                        String[] numbersArray3 = {};
                        numbersArray3 = winning.getLottoAmericaArray().split(" ");
                        linearWinings[2].removeAllViews();
                        for (String number : numbersArray3) {
                            TextView textView = createTextView(this, number, false, "lottoi");
                            linearWinings[2].addView(textView);
                        }

                    }
                    if (winning.getLuckyforlifeArray() != null) {
                        String[] numbersArray4 = {};
                        numbersArray4 = winning.getLuckyforlifeArray().split(" ");
                        linearWinings[3].removeAllViews();
                        for (int i = 0; i < numbersArray4.length; i++) {
                            boolean last = false;
                            if (i == numbersArray4.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray4[i], last, "luckyforlifei");
                            linearWinings[3].addView(textView);
                        }

                    }

                    if (winning.getIdhaoCashArray() != null) {
                        String[] numbersArray5 = {};
                        numbersArray5 = winning.getIdhaoCashArray().split(" ");
                        linearWinings[4].removeAllViews();
//                        for (String number : numbersArray5) {
//                            TextView textView = createTextView(this, number);
//                            pick3linearLayout.addView(textView);
//                        }
                        for (int i = 0; i < numbersArray5.length; i++) {
                            boolean last = false;
                            if (i == numbersArray5.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray5[i], last, "idhaocash");
                            linearWinings[4].addView(textView);
                        }

                    }


                    if (headerlogosize > 5) {


                        if (winning.getFivestartdrawArray() != null) {
                            String[] numbersArray6 = {};
                            numbersArray6 = winning.getFivestartdrawArray().split(" ");
                            linearWinings[5].removeAllViews();
//                        for (String number : numbersArray6) {
//                            TextView textView = createTextView(this, number, false, "daily4");
//                            daily4linearLayout.addView(textView);
//                        }
                            for (int i = 0; i < numbersArray6.length; i++) {
                                boolean last = false;
                                if (i == numbersArray6.length - 1) {
                                    last = true;
                                }
                                TextView textView = createTextView(this, numbersArray6[i], last, "5stari");
                                linearWinings[5].addView(textView);
                            }

                        }


                        if (winning.getWeeklygrandArray() != null) {
                            String[] numbersArray7 = {};
                            numbersArray7 = winning.getWeeklygrandArray().split(" ");
                            linearWinings[6].removeAllViews();
                            for (String number : numbersArray7) {
                                TextView textView = createTextView(this, number, false, "weeklygrand");
                                linearWinings[6].addView(textView);
                            }

                        }


                    }
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
                        linearWinings[0].removeAllViews();
                        for (int i = 0; i < numbersArray1.length; i++) {
                            boolean last = false;
                            if (i == numbersArray1.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray1[i], last, "power");
                            linearWinings[0].addView(textView);
                        }

                    }

                    if (winning.getMegaMillionsArray() != null) {
                        String[] numbersArray2 = {};
                        numbersArray2 = winning.getMegaMillionsArray().split(" ");
                        linearWinings[1].removeAllViews();
                        for (int i = 0; i < numbersArray2.length; i++) {
                            boolean last = false;
                            if (i == numbersArray2.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray2[i], last, "megamilion");
                            linearWinings[1].addView(textView);
                        }

                    }

                    if (winning.getLottoTexasArray() != null) {
                        String[] numbersArray3 = {};
                        numbersArray3 = winning.getLottoTexasArray().split(" ");
                        linearWinings[2].removeAllViews();
                        for (String number : numbersArray3) {
                            TextView textView = createTextView(this, number, false, "lotto");
                            linearWinings[2].addView(textView);
                        }

                    }
                    if (winning.getTexasTwoStepArray() != null) {
                        String[] numbersArray4 = {};
                        numbersArray4 = winning.getTexasTwoStepArray().split(" ");
                        linearWinings[3].removeAllViews();
                        for (int i = 0; i < numbersArray4.length; i++) {
                            boolean last = false;
                            if (i == numbersArray4.length - 1) {
                                last = true;
                            }
                            TextView textView = createTextView(this, numbersArray4[i], last, "texastwo");
                            linearWinings[3].addView(textView);
                        }

                    }

                    if (winning.getPick3DayArray() != null) {
                        String[] numbersArray5 = {};
                        numbersArray5 = winning.getPick3DayArray().split(" ");
                        linearWinings[4].removeAllViews();
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
                            linearWinings[4].addView(textView);
                        }

                    }


                    Log.d("TAG", "getsdSetGlobaldaysandwinnings: " + headerlogosize);
                    if (headerlogosize > 5) {


                        if (winning.getDaily4DayArray() != null) {
                            String[] numbersArray6 = {};
                            numbersArray6 = winning.getDaily4DayArray().split(" ");
                            linearWinings[5].removeAllViews();
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
                                linearWinings[5].addView(textView);
                            }

                        }


                        if (winning.getCashFiveArray() != null) {
                            String[] numbersArray7 = {};
                            numbersArray7 = winning.getCashFiveArray().split(" ");
                            linearWinings[6].removeAllViews();
                            for (String number : numbersArray7) {
                                TextView textView = createTextView(this, number, false, "cash_five");
                                linearWinings[6].addView(textView);
                            }

                        }


                        if (winning.getAllOrNothingMorningArray() != null) {
                            String[] numbersArray8 = {};
                            numbersArray8 = winning.getAllOrNothingMorningArray().split(" ");
                            linearWinings[7].removeAllViews();
                            for (String number : numbersArray8) {
                                TextView textView = createTextView(this, number, true, "allornothing");
                                linearWinings[7].addView(textView);
                            }

                        }
                    }
                });
            }
        });
    }

    //khokhar@2921
    public static void initrecycler(Context context, Boolean subscribeInventory, Screen1 screen2, Boolean showheader, String orientation, int boxes, String empty_box, String imageurl) {
        gridView.setVerticalScrollBarEnabled(false);

        int w = parent.getWidth();
        int h = parent.getHeight();
        if (w == 0) {
            w = Prefrences.getScreenWidth(context);
        } else {
            Prefrences.setScreenWidth(context, w);
        }
        if (h == 0) {
            h = Prefrences.getScreenHeight(context);
        } else {
            Prefrences.setScreenHeight(context, h);
        }
        if (orientation.equals("portrait")) {
            if (h > w) {
                sizeoflyt = h - 5;
            } else {
                sizeoflyt = w - 5;
            }
        } else {
            if (h > w) {
                sizeoflyt = w;
            } else {
                sizeoflyt = h;
            }
        }
        if (showheader) {
            int dpValue = 80;
            int pixels = dpToPixels(context, dpValue);
            if (headerrltv.getHeight() == 0) {
                sizeoflyt = sizeoflyt - pixels;
            } else {
                sizeoflyt = sizeoflyt - headerrltv.getHeight();
            }
        }


        gridLayoutManager = new GridLayoutManager(context, spancount);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        recyclerViewadapter = new RecyclerviewAdapter(context, subscribeInventory, from, screen2, sizeoflyt, orientation, boxes, empty_box, imageurl);
        recyclerViewadapter.notifyDataSetChanged();
        gridRecyclerView.setAdapter(recyclerViewadapter);
        pbar.setVisibility(View.GONE);

    }


    public static int dpToPixels(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()
        );
    }


    public void keepScreenAwake() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();
    }

    public void setBoxes(int totalboxes, Context context) {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.parent);
        int h = displayMetrics.heightPixels;
        int w = displayMetrics.widthPixels;
        if (w == 0) {
            w = Prefrences.getScreenWidth(context);
        } else {
            Prefrences.setScreenWidth(context, w);
        }
        if (h == 0) {
            h = Prefrences.getScreenHeight(context);
        } else {
            Prefrences.setScreenHeight(context, h);
        }

        if (h > w) {
            int temp = h;
            h = w;
            w = temp;
        }

        mainLayout.setRotation(0.0f);
        mainLayout.setTranslationX(0);
        mainLayout.setTranslationY(0);
        ViewGroup.LayoutParams lp = mainLayout.getLayoutParams();
        lp.height = h;
        lp.width = w;
        mainLayout.requestLayout();


        if (totalboxes == 100 || totalboxes == 80 || totalboxes == 50) {
            gridView.setNumColumns(10);
            spancount = 10;
        } else if (totalboxes == 65) {
            spancount = 13;
            gridView.setNumColumns(13);
        } else if (totalboxes == 32) {
            spancount = 8;
            gridView.setNumColumns(8);
        } else {
            spancount = 6;
            gridView.setNumColumns(6);
        }
    }

    public void setPortraitBoxes(int totalboxes) {
        if (totalboxes == 100) {
            spancount = 10;
            gridView.setNumColumns(10);
        } else if (totalboxes == 80) {
            spancount = 8;
            gridView.setNumColumns(8);
        } else if (totalboxes == 65) {
            spancount = 5;
            gridView.setNumColumns(5);
        } else if (totalboxes == 50) {
            spancount = 5;
            gridView.setNumColumns(5);
        } else if (totalboxes == 32) {
            spancount = 4;
            gridView.setNumColumns(4);
        } else if (totalboxes == 18) {
            spancount = 3;
            gridView.setNumColumns(3);
        }


        headerlayout.setWeightSum(5f);
        headerlogosize = 5;


    }

    private void init() {
        from = getIntent().getStringExtra("from");
        gridRecyclerView = findViewById(R.id.recyclerViewGrid);
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
        gridView = findViewById(R.id.gridview);
        displayMetrics = getResources().getDisplayMetrics();
        gridRelative = findViewById(R.id.gridrltv);
        footerRelative = findViewById(R.id.bottomrltv);
        headerrltv = findViewById(R.id.header);
        logolyt = findViewById(R.id.logos);


        headerlayout = (LinearLayout) findViewById(R.id.logoslyt);

        parent = findViewById(R.id.parent);
        currentIndex = 0;
        handler = new Handler();
        getSetUserData(getApplicationContext());
        scrapingData();
        keepScreenAwake();

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
            handler.postDelayed(this, 30000);
        }
    };

    private void startTextSwap() {
        if (!isTextSwapRunning) {
            handler.postDelayed(swapTextRunnable, 30000);
            isTextSwapRunning = true;
        }
    }

    private void stopTextSwap() {
        if (isTextSwapRunning) {
            handler.removeCallbacks(swapTextRunnable);
            isTextSwapRunning = false;
        }
    }

    private void swapText() {
        if (currentIndex >= 2) {
            currentIndex = 0;
        }
        if (region.equals("Texas") || region.equals("Arkansas") || region.equals("California") || region.equals("Georgia") || region.equals("Idaho") || region.equals("Florida") ) {
            if (currentIndex == 0) {
                for (int i = 0; i < headerlogosize; i++) {
                    linearLogos[i].setVisibility(View.VISIBLE);
                    priceLogos[i].setVisibility(View.VISIBLE);
                    linearWinings[i].setVisibility(View.GONE);
                    tvDaysLogos[i].setVisibility(View.VISIBLE);
                }
            } else {
                for (int i = 0; i < headerlogosize; i++) {
                    linearLogos[i].setVisibility(View.VISIBLE);
                    priceLogos[i].setVisibility(View.VISIBLE);
                    tvDaysLogos[i].setVisibility(View.GONE);
                }
                for (int i = 0; i < headerlogosize; i++) {
                    if (linearWinings[i].getChildCount() < 7) {
                        linearWinings[i].setVisibility(View.VISIBLE);
                    } else {
                        linearWinings[i].setVisibility(View.INVISIBLE);
                    }
                }
            }
        } else {
            for (int i = 0; i < headerlogosize; i++) {
                linearLogos[i].setVisibility(View.VISIBLE);
                priceLogos[i].setVisibility(View.VISIBLE);
                linearWinings[i].setVisibility(View.GONE);
            }
        }
        currentIndex++;
    }

    private TextView createTextViewCalifornia(Context context, String text, boolean lastnumber, String lotteyname) {
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
            if (lotteyname.equals("powerc")) {
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.circle_border_maroon_bg_california);
            } else if (lotteyname.equals("megamilionc")) {
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setBackgroundResource(R.drawable.circle_border_yellow_bg_california);
            } else if (lotteyname.equals("superlottoc")) {
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.circle_border_blue_california);
            } else {
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setBackgroundResource(R.drawable.circle_border_black);
            }
        } else {
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setBackgroundResource(R.drawable.circle_border_black);
        }
        return textView;
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
            } else if (lotteyname.equals("megamilioni")) {
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setBackgroundResource(R.drawable.circle_border_yellow_bg);
            } else if (lotteyname.equals("texastwo") || lotteyname.equals("poweri")) {
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.circle_border_lightmaroon_bg);
            } else if (lotteyname.equals("lottoi")) {
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.circle_border_blue_idhao);
            } else if (lotteyname.equals("luckyforlifei")) {
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.circle_border_green_idaho);
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


    private TextView createTextViewArkansas(Context context, String text, boolean lastnumber, String lotteyname) {
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
            if (lotteyname.equals("powera") || lotteyname.equals("luckyforlife") || lotteyname.equals("megamiliona")) {
                textView.setTextColor(getResources().getColor(R.color.graydarktext));
                textView.setBackgroundResource(R.drawable.circle_border_maroon);
            } else {
                textView.setTextColor(getResources().getColor(R.color.graydarktext));
                textView.setBackgroundResource(R.drawable.circle_border_bluearkansas);
            }
        } else {
            textView.setTextColor(getResources().getColor(R.color.graydarktext));
            textView.setBackgroundResource(R.drawable.circle_border_bluearkansas);
        }
        return textView;
    }

    private void changeScrren(int totalBoxes, Context context) {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.parent);
        int w = displayMetrics.widthPixels;
        int h = displayMetrics.heightPixels;

        if (w == 0) {
            w = Prefrences.getScreenWidth(context);
        } else {
            Prefrences.setScreenWidth(context, w);
        }
        if (h == 0) {
            h = Prefrences.getScreenHeight(context);
        } else {
            Prefrences.setScreenHeight(context, h);
        }


        mainLayout.setRotation(270.0f);
        mainLayout.setTranslationX((w - h) / 2);
        mainLayout.setTranslationY((h - w) / 2);
        ViewGroup.LayoutParams lp = mainLayout.getLayoutParams();
        lp.height = w;
        lp.width = h;
        mainLayout.requestLayout();
        setPortraitBoxes(totalBoxes);
    }

    private void setheaderBoxes(String orientation) {
        if (orientation.equals("portrait")) {
            headerlogosize = 5;
            headerlayout.setWeightSum(5f);
        } else {

            if (region.equals("Georgia") ||region.equals("Texas") || region.equals("Arkansas") || region.equals("California") || region.equals("Florida")) {
                headerlogosize = 8;
                headerlayout.setWeightSum(8f);
            } else if (region.equals("Idaho")) {
                headerlogosize = 7;
                headerlayout.setWeightSum(7f);
            }
        }
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
        if (accountType.equals("A1")) {
            finishAffinity();
        } else {
            Intent intent = new Intent(MainactivityPortraitActivity.this, ChooseScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void scrapingData() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new ScrapeIdahoWebsite().execute();
                new ScrapeCaliforniaWebsite().execute();
                new ScrapeGeorgiaWebsite().execute();
                new ScrapeWebsiteTask().execute();
                new ScrapeArkansasWebsite().execute();
                new ScrapeFloridaWebsiteTask().execute();
                //jackpots
                new ScrapeArkansasJackpot().execute();
                new ScrapeCaliforniaJackpot().execute();
                new ScrapeIdahoJackpot().execute();
                new ScrapeGeorgiaJackpot().execute();
                new ScrapeFloridaJackpot().execute();


            }
        };
        timer.schedule(task, 5000);
    }
}