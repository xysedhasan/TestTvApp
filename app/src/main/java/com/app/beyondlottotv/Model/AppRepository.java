package com.app.beyondlottotv.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.app.beyondlottotv.Activities.LoginActivity;
import com.app.beyondlottotv.Activities.MainactivityPortraitActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.BiConsumer;


public class AppRepository {

    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void login(Context context, String email, String password, BiConsumer<Boolean, String> callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(context), new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.accept(true, "");

                            String current = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Prefrences.setId(context, current);
                            db.collection("users")
                                    .document(current).update("login_status", true);
                        } else {
                            callback.accept(false, task.getException().getMessage());
                        }
                    }
                });
    }


    public static void updatelogin() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String current = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users")
                .document(current).update("login_status", true);
        LoginData loginData = new LoginData("", "", 0, false);
        db.collection("login")
                .document("thisisthelogingeneratedcode")
                .set(loginData);
    }

    public static void getGames(Context context) {
        db.collection("games").whereEqualTo("number","2530").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Game game = document.toObject(Game.class);
                    Log.d("TAG", "imgurl: "+game.image_url);
                }
                Log.d("TAG", "onCompletesize: "+task.getResult().size());
            }
        });

        //get games according to the region type
        db.collection("games").whereEqualTo("region", Prefrences.getRegion(context)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Game> gamesarr = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Game game = document.toObject(Game.class);
                        gamesarr.add(game);
                    }
                    Prefrences.setGamesListInLocal(context, gamesarr, "games");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void getUser(Activity activity, Context context, String screenNo, BiConsumer<Boolean, UserNew> callback) {

        Log.d("TAG", "getUser: ");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String current = "";
        if (Prefrences.getId(context) != null && !Prefrences.getId(context).equals("")) {
            current = Prefrences.getId(context);
        } else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                current = user.getUid();
            } else {
                checklogout(activity, false, context);
            }
        }

        Log.d("TAG", "getUsercurrent: " + current);
        if (!current.isEmpty()) {
            db.collection("users")
                    .document(current)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                            if (snapshot != null && snapshot.exists()) {
                                UserNew user = snapshot.toObject(UserNew.class);
                                //checklogout
                                if (user != null) {
                                    Prefrences.saveUserDetails(context, user, screenNo);
                                    try {
                                        if (user.getScreen1() != null) {
                                            Prefrences.setTotalBoxesScreen1(context, user.getScreen1().getTotal_boxes());
                                        }
                                        if (user.getScreen2() != null) {
                                            Prefrences.setTotalBoxesScreen2(context, user.getScreen2().getTotal_boxes());
                                        }
                                        if (user.getScreen3() != null) {
                                            Prefrences.setTotalBoxesScreen3(context, user.getScreen3().getTotal_boxes());
                                        }
                                        checklogout(activity, user.isLogin_status(), context);
                                    } catch (Exception e) {
                                        Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    callback.accept(true, user);
                                } else {
                                    callback.accept(false, null);
                                }
                            } else {
                                callback.accept(false, null);
                            }
                        }
                    });
        } else {
            callback.accept(false, null);
            checklogout(activity, false, context);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void getGamesofUser(Activity activity, UserNew user, Context context, String screenNo, BiConsumer<String, String> callback) {

        if (screenNo.equals("screen1")) {
            MainactivityPortraitActivity.initrecycler(context, user.isSubscribeInventory(), user.getScreen1(), user.getScreen1().isShow_header(), user.getScreen1().getOrientation(), user.getScreen1().getTotal_boxes(), user.getScreen1().getEmpty_box(), user.getScreen1().getEmpty_box_custom_image());
//            Screen1Activity.initrecycler(context, user.getScreen1(), user.getScreen1().isShow_header(), user.getScreen1().getOrientation(), user.getScreen1().getTotal_boxes(), user.getScreen1().getEmpty_box(), user.getScreen1().getEmpty_box_custom_image());
        } else if (screenNo.equals("screen2")) {
            MainactivityPortraitActivity.initrecycler(context, user.isSubscribeInventory(), user.getScreen2(), user.getScreen2().isShow_header(), user.getScreen2().getOrientation(), user.getScreen2().getTotal_boxes(), user.getScreen2().getEmpty_box(), user.getScreen2().getEmpty_box_custom_image());
//            Screen2Activity.initrecycler(context, user.getScreen2(), user.getScreen2().isShow_header(), user.getScreen2().getOrientation(), user.getScreen2().getTotal_boxes(), user.getScreen2().getEmpty_box(), user.getScreen2().getEmpty_box_custom_image());

        } else if (screenNo.equals("screen3")) {
            MainactivityPortraitActivity.initrecycler(context, user.isSubscribeInventory(), user.getScreen3(), user.getScreen3().isShow_header(), user.getScreen3().getOrientation(), user.getScreen3().getTotal_boxes(), user.getScreen3().getEmpty_box(), user.getScreen3().getEmpty_box_custom_image());
//            Screen3Activity.initrecycler(context, user.getScreen3(), user.getScreen3().isShow_header(), user.getScreen3().getOrientation(), user.getScreen3().getTotal_boxes(), user.getScreen3().getEmpty_box(), user.getScreen3().getEmpty_box_custom_image());

        } else if (screenNo.equals("screen4")) {
            MainactivityPortraitActivity.initrecycler(context, user.isSubscribeInventory(), user.getScreen4(), user.getScreen4().isShow_header(), user.getScreen4().getOrientation(), user.getScreen4().getTotal_boxes(), user.getScreen4().getEmpty_box(), user.getScreen4().getEmpty_box_custom_image());
//            Screen4Activity.initrecycler(context, user.getScreen4(), user.getScreen4().isShow_header(), user.getScreen4().getOrientation(), user.getScreen4().getTotal_boxes(), user.getScreen4().getEmpty_box(), user.getScreen4().getEmpty_box_custom_image());
        } else if (screenNo.equals("screen5")) {
            if (user.getScreen5() != null) {
                if (user.getScreen5().getMedia_url() != null) {
                    callback.accept(user.getScreen5().getMedia_type(), user.getScreen5().getMedia_url());
                } else {
                    callback.accept(null, null);
                }
            } else {
                callback.accept(null, null);
            }
        } else if (screenNo.equals("screen6")) {

            if (user.getScreen4() != null) {
                if (user.getScreen6().getMedia_url() != null) {
                    callback.accept(user.getScreen6().getMedia_type(), user.getScreen6().getMedia_url());
                } else {
                    callback.accept(null, null);
                }
            } else {
                callback.accept(null, null);
            }
        } else if (screenNo.equals("screen7")) {
            if (user.getScreen7() != null) {
                if (user.getScreen5().getMedia_url() != null) {
                    callback.accept(user.getScreen7().getMedia_type(), user.getScreen7().getMedia_url());
                } else {
                    callback.accept(null, null);
                }
            } else {
                callback.accept(null, null);
            }
        }
    }

    public static void checklogout(Activity activity, Boolean islogin, Context context) {
        Log.d("TAG", "checklogout: " + islogin);
        Prefrences.setisLoggedin(context, islogin);
        if (!islogin) {
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    /*@RequiresApi(api = Build.VERSION_CODES.N)
    public static void getGame(ArrayList<String> numberarr, BiConsumer<Boolean, ArrayList<Game>> callback) {
        ArrayList<Game> gamesarr = new ArrayList<>();
        for (String number : numberarr) {
            if (number != null && !number.contains("null")) {
                db.collection("games").whereEqualTo("number", number).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Game game = document.toObject(Game.class);
                                gamesarr.add(game);
                                break;
                            }
                            callback.accept(true, gamesarr);
                            Log.d(TAG, "onComplete: second");

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.accept(false, null);
                    }
                });
            }
        }
    }*/


    public static void getFloridaGlobalPrices(Context context, BiConsumer<FloridaGlobalPrices, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("foridaglobal")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            FloridaGlobalPrices prices = snapshot.toObject(FloridaGlobalPrices.class);
                            if (prices != null) {
                                callback.accept(prices, true);
                            } else {
                                callback.accept(prices, false);
                            }
                        }
                    }
                });
    }


    public static void getGeorgiaGlobalPrices(Context context, BiConsumer<GeorgiaGlobalPrices, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("georgiaglobal")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            GeorgiaGlobalPrices prices = snapshot.toObject(GeorgiaGlobalPrices.class);
                            if (prices != null) {
                                callback.accept(prices, true);
                            } else {
                                callback.accept(prices, false);
                            }
                        }
                    }
                });
    }

    public static void getCaliforniaGlobalPrices(Context context, BiConsumer<Californiaglobal, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("californiaglobal")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            Californiaglobal prices = snapshot.toObject(Californiaglobal.class);
                            if (prices != null) {
                                callback.accept(prices, true);
                            } else {
                                callback.accept(prices, false);
                            }
                        }
                    }
                });
    }

    public static void getArkansasGlobalPrices(Context context, BiConsumer<Arkansasglobal, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("arkansasglobal")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            Arkansasglobal prices = snapshot.toObject(Arkansasglobal.class);
                            if (prices != null) {
                                callback.accept(prices, true);
                            } else {
                                callback.accept(prices, false);
                            }
                        }
                    }
                });
    }

    public static void getIdahoGlobalPrices(Context context, BiConsumer<Idahoglobal, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("idahoglobal")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            Idahoglobal prices = snapshot.toObject(Idahoglobal.class);
                            if (prices != null) {
                                callback.accept(prices, true);
                            } else {
                                callback.accept(prices, false);
                            }
                        }
                    }
                });
    }

    public static void getGlobalPrices(Context context, BiConsumer<GlobalPrices, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("global")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            Log.d("CriticalBug", "getGlobalPrices-onEvent: ");
                            GlobalPrices prices = snapshot.toObject(GlobalPrices.class);
                            if (prices != null) {
                                callback.accept(prices, true);
                            } else {
                                callback.accept(prices, false);
                            }
                        }
                    }
                });
    }

    public static void getCustomTokenofLogin(Context context, BiConsumer<LoginData, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("login")
                .document("thisisthelogingeneratedcode")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            LoginData loginData = snapshot.toObject(LoginData.class);
                            if (loginData != null) {
                                callback.accept(loginData, true);
                            } else {
                                callback.accept(loginData, false);
                            }
                        }
                    }
                });
    }

    public static void getGlobalDaysandWinings(Context context, BiConsumer<GlobalDayndWinning, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("winningNumberBalls")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            GlobalDayndWinning dayandwinnigns = snapshot.toObject(GlobalDayndWinning.class);
                            Log.d("TAG", "onEventarray: " + dayandwinnigns.getAllOrNothingMorningArray());
                            if (dayandwinnigns != null) {
                                callback.accept(dayandwinnigns, true);
                            } else {
                                callback.accept(dayandwinnigns, false);
                            }
                        }
                    }
                });
    }

    public static void getArkansasWinings(Context context, BiConsumer<ArkansasWinnings, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("winningNumbersArkansas")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            ArkansasWinnings dayandwinnigns = snapshot.toObject(ArkansasWinnings.class);
                            if (dayandwinnigns != null) {
                                callback.accept(dayandwinnigns, true);
                            } else {
                                callback.accept(dayandwinnigns, false);
                            }
                        }
                    }
                });
    }

    public static void getCaliforniaWinings(Context context, BiConsumer<CaliforniaWinnings, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("winningNumbersCalifornia")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            CaliforniaWinnings dayandwinnigns = snapshot.toObject(CaliforniaWinnings.class);
                            if (dayandwinnigns != null) {
                                callback.accept(dayandwinnigns, true);
                            } else {
                                callback.accept(dayandwinnigns, false);
                            }
                        }
                    }
                });
    }

    public static void getIdahoWinings(Context context, BiConsumer<IdahoWinnings, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("winningNumbersIdaho")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            IdahoWinnings dayandwinnigns = snapshot.toObject(IdahoWinnings.class);
                            if (dayandwinnigns != null) {
                                callback.accept(dayandwinnigns, true);
                            } else {
                                callback.accept(dayandwinnigns, false);
                            }
                        }
                    }
                });
    }

    public static void getGeorgiaWinings(Context context, BiConsumer<GeorgiaWinnings, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("winningNumbersGeorgia")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot != null && snapshot.exists()) {
                            GeorgiaWinnings dayandwinnigns = snapshot.toObject(GeorgiaWinnings.class);
                            if (dayandwinnigns != null) {
                                callback.accept(dayandwinnigns, true);
                            } else {
                                callback.accept(dayandwinnigns, false);
                            }
                        }
                    }
                });
    }


    public static void signinWithCustomToken(String token, BiConsumer<String, Boolean> callback) {
        firebaseAuth.signInWithCustomToken(token)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.accept("", true);
                        } else {
                            callback.accept(task.getException().getMessage(), false);
                        }
                    }
                });
    }

    public static String getYoutubeVideoIdFromUrl(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }

}
