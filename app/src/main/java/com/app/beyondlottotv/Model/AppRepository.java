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
import com.app.beyondlottotv.Api.ApiResponseCustomtoken;
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
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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
    }

    public static void getGames(Context context) {
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

    public static void getUser(Activity activity, Context context, String screenNo, BiConsumer<Boolean, UserNew> callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String current = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
                                Prefrences.setTotalBoxesScreen1(context, user.getScreen1().getTotal_boxes());
                                Prefrences.setTotalBoxesScreen2(context, user.getScreen2().getTotal_boxes());
                                Prefrences.setTotalBoxesScreen3(context, user.getScreen3().getTotal_boxes());
                                callback.accept(true, user);
                                try {
                                    checklogout(activity, user.isLogin_status(), context);
                                } catch (Exception e) {
                                    Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void getGamesofUser(Activity activity, UserNew user, Context context, String screenNo, BiConsumer<String, String> callback) {
        if (screenNo.equals("screen1")) {
            MainactivityPortraitActivity.initrecycler(context, user.getScreen1(), user.getScreen1().isShow_header(), user.getScreen1().getOrientation(), user.getScreen1().getTotal_boxes(), user.getScreen1().getEmpty_box(), user.getScreen1().getEmpty_box_custom_image());
//            Screen1Activity.initrecycler(context, user.getScreen1(), user.getScreen1().isShow_header(), user.getScreen1().getOrientation(), user.getScreen1().getTotal_boxes(), user.getScreen1().getEmpty_box(), user.getScreen1().getEmpty_box_custom_image());
        } else if (screenNo.equals("screen2")) {
            MainactivityPortraitActivity.initrecycler(context, user.getScreen2(), user.getScreen2().isShow_header(), user.getScreen2().getOrientation(), user.getScreen2().getTotal_boxes(), user.getScreen2().getEmpty_box(), user.getScreen2().getEmpty_box_custom_image());
//            Screen2Activity.initrecycler(context, user.getScreen2(), user.getScreen2().isShow_header(), user.getScreen2().getOrientation(), user.getScreen2().getTotal_boxes(), user.getScreen2().getEmpty_box(), user.getScreen2().getEmpty_box_custom_image());

        } else if (screenNo.equals("screen3")) {
            MainactivityPortraitActivity.initrecycler(context, user.getScreen3(), user.getScreen3().isShow_header(), user.getScreen3().getOrientation(), user.getScreen3().getTotal_boxes(), user.getScreen3().getEmpty_box(), user.getScreen3().getEmpty_box_custom_image());
//            Screen3Activity.initrecycler(context, user.getScreen3(), user.getScreen3().isShow_header(), user.getScreen3().getOrientation(), user.getScreen3().getTotal_boxes(), user.getScreen3().getEmpty_box(), user.getScreen3().getEmpty_box_custom_image());

        } else if (screenNo.equals("screen4")) {
            MainactivityPortraitActivity.initrecycler(context, user.getScreen4(), user.getScreen4().isShow_header(), user.getScreen4().getOrientation(), user.getScreen4().getTotal_boxes(), user.getScreen4().getEmpty_box(), user.getScreen4().getEmpty_box_custom_image());
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


    public static void makeApiCallofCustomToken(Context context, String id, BiConsumer<Boolean, String> callback) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"uid\":" + id + "\n}");
        Request request = new Request.Builder()
                .url("https://us-central1-beyondlottotv.cloudfunctions.net/createCustomToken")
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("TAG", "onResponseapi: " + responseBody);
                    ApiResponseCustomtoken token = new ApiResponseCustomtoken();
                    Gson gson = new Gson();
                    token = gson.fromJson(responseBody, ApiResponseCustomtoken.class);

                    firebaseAuth.signInWithCustomToken(token.getCustomToken())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        callback.accept(true, "");

                                        String current = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        db.collection("users")
                                                .document(current).update("login_status", true);
                                    } else {
                                        callback.accept(false, task.getException().getMessage());
                                    }
                                }
                            });


                } else {
                    Log.e("API_CALL", "Unsuccessful response: " + response.code());
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // Handle the failure here
//                Log.d(TAG, "onFailureapi: " + e);
            }
        });
    }


}
