package com.app.beyondlotto.Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.app.beyondlotto.Activities.ChooseScreenActivity;
import com.app.beyondlotto.Activities.LoginActivity;
import com.app.beyondlotto.Activities.Screen1Activity;
import com.app.beyondlotto.Activities.Screen2Activity;
import com.app.beyondlotto.Activities.Screen3Activity;
import com.app.beyondlotto.Activities.Screen4Activity;
import com.app.beyondlotto.Activities.Screen5Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AppRepository {
    private static final String TAG = "AppRepository";
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseAuth mAuth;

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

        db.collection("games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        if (snapshot.exists()) {
                            UserNew user = snapshot.toObject(UserNew.class);
                            //checklogout
                            if (user != null) {
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

        Game emptyGame = new Game();
        ArrayList<Game> gamesimages = new ArrayList<>();
        ArrayList<Boolean> animatearr = new ArrayList<>();
        animatearr.clear();
        // Log.d(TAG, "getGamesofUser: " + user.getScreen3());
        Prefrences.saveUserDetails(context, user, screenNo);
        Prefrences.setTotalBoxesScreen1(context, user.getScreen1().getTotal_boxes());
        if (screenNo.equals("screen1")) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

            int totalbox = user.getScreen1().getTotal_boxes();
            ArrayList<String> temp = new ArrayList<>();
            ArrayList<Boolean> tempAnimation = new ArrayList<>();
            temp.clear();
            gamesimages.clear();
            if (user.getScreen1().getArray_for_boxes().size() < totalbox) {

                for (int i = 0; i < totalbox; i++) {
                    temp.add("null");
                    tempAnimation.add(false);
                    animatearr.add(false);
                    gamesimages.add(null);
                }

                for (int i = 0; i < user.getScreen1().getArray_for_boxes().size(); i++) {
                    temp.set(i, user.getScreen1().getArray_for_boxes().get(i));
                }

                for (int i = 0; i < totalbox; i++) {
                    String gameNumber = "";
                    if (temp.get(i) != null && !temp.get(i).contains("null")) {
                        gameNumber = temp.get(i);
                        for (Game game : Prefrences.getGamesListFromLocal(context, "games")) {
                            if (game.getNumber().equals(gameNumber)) {
                                gamesimages.add(i, game);
                                break;
                            } else {
                                gamesimages.add(i, null);
                            }
                        }
                    } else {
                        gamesimages.add(i, null);
                    }
                }

                //assigning data temp animation array
                for (int i = 0; i < totalbox; i++) {

                    if (user.getScreen1().getAnimation_for_boxes().size() > i) {
                        if (user.getScreen1().getAnimation_for_boxes().get(i) != null) {
                            tempAnimation.set(i, user.getScreen1().getAnimation_for_boxes().get(i));
                        } else {
                            tempAnimation.set(i, false);
                        }
                    } else {
                        tempAnimation.set(i, false);
                    }

                }

                //assigning data to orignal array from temp array
                for (int i = 0; i < totalbox; i++) {
                    if (user.getScreen1() != null) {
                        if (user.getScreen1().getAnimation_for_boxes() != null) {
                            try {
                                animatearr.add(i, user.getScreen1().getAnimation_for_boxes().get(i));
                            } catch (Exception e) {
                                animatearr.add(i, false);
                            }
                        } else {
                            animatearr.add(i, false);
                        }
                    } else {
                        animatearr.add(i, false);
                    }
                }


//                for (int i = 0; i < totalbox; i++) {
//                    if (user.getScreen1().getAnimation_for_boxes() != null) {
//                        animatearr.add(i, tempAnimation.get(i));
//                    } else {
//                        animatearr.add(i, false);
//                    }
//                }


            } else {
                for (int i = 0; i < totalbox; i++) {
                    temp.add("null");
                    tempAnimation.add(false);
                    animatearr.add(false);
                    gamesimages.add(null);
                }
                for (int i = 0; i < totalbox; i++) {
                    String gameNumber = "";
                    if (user.getScreen1().getArray_for_boxes().get(i) != null && !user.getScreen1().getArray_for_boxes().get(i).contains("null")) {
                        gameNumber = user.getScreen1().getArray_for_boxes().get(i);
                        if (Prefrences.getGamesListFromLocal(context, "games") != null) {
                            for (Game game : Prefrences.getGamesListFromLocal(context, "games")) {
                                if (game.getNumber().equals(gameNumber)) {
                                    gamesimages.add(i, game);
                                    break;
                                } else {
                                    gamesimages.add(i, null);
                                }
                            }
                        }
                    }
//                     else {
//                         Game emptyGame1 = new Game();
//                        gamesimages.add(i, emptyGame1);
//                    }
                }

                // user.getScreen1().getTotal_boxes();
                //assigning data to orignal array from temp array
                for (int i = 0; i < totalbox; i++) {
                    if (user.getScreen1() != null) {
                        if (user.getScreen1().getAnimation_for_boxes() != null) {
                            try {
                                if (user.getScreen1().getAnimation_for_boxes().size() > i) {
                                    animatearr.add(i, user.getScreen1().getAnimation_for_boxes().get(i));
                                }else {
                                    animatearr.add(i, false);
                                }
                            } catch (Exception e) {
                                animatearr.add(i, false);
                            }
                        } else {
                            animatearr.add(i, false);
                        }
                    } else {
                        animatearr.add(i, false);
                    }
                }
            }

            //geting total boxes games
            ArrayList<Game> gamestemp = new ArrayList<>();
            for (int i = 0; i < totalbox; i++) {
                if (gamesimages.size() > i) {
                    gamestemp.add(gamesimages.get(i));
                }
            }
            Screen1Activity.initrecycler(context, gamestemp, animatearr, user.getScreen1().isShow_header(), user.getScreen1().getOrientation(), user.getScreen1().getTotal_boxes(), user.getScreen1().getEmpty_box(), user.getScreen1().getEmpty_box_custom_image());
        } else if (screenNo.equals("screen2")) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            int totalbox = user.getScreen2().getTotal_boxes();
            ArrayList<String> temp = new ArrayList<>();
            ArrayList<Boolean> tempAnimation = new ArrayList<>();
            if (user.getScreen2().getArray_for_boxes().size() < totalbox) {
                for (int i = 0; i < totalbox; i++) {
                    temp.add("null");
                    tempAnimation.add(false);
                    animatearr.add(false);
                    gamesimages.add(null);
                }

                for (int i = 0; i < user.getScreen2().getArray_for_boxes().size(); i++) {
                    temp.set(i, user.getScreen2().getArray_for_boxes().get(i));
                }

                for (int i = 0; i < totalbox; i++) {
                    String gameNumber = "";
                    if (temp.get(i) != null && !temp.get(i).contains("null")) {
                        gameNumber = temp.get(i);
                        for (Game game : Prefrences.getGamesListFromLocal(context, "games")) {
                            if (game.getNumber().equals(gameNumber)) {
                                gamesimages.add(i, game);
                                break;
                            } else {
                                gamesimages.add(i, null);
                            }
                        }
                    } else {
                        gamesimages.add(i, null);
                    }
                }
//
                //assigning data temp animation array
                for (int i = 0; i < user.getScreen2().getAnimation_for_boxes().size(); i++) {

                    tempAnimation.set(i, user.getScreen2().getAnimation_for_boxes().get(i));
                }

                //assigning data to orignal array from temp array
                for (int i = 0; i < totalbox; i++) {

                    if (user.getScreen2().getAnimation_for_boxes() != null) {
                        if (user.getScreen2().getAnimation_for_boxes().size() > i) {
                            animatearr.add(i, tempAnimation.get(i));
                        }else {
                            animatearr.add(i, false);
                        }

                    } else {
                        animatearr.add(i, false);
                    }
                }

            } else {
                for (int i = 0; i < totalbox; i++) {
                    temp.add("null");
                    tempAnimation.add(false);
                    animatearr.add(false);
                    gamesimages.add(null);
                }
                for (int i = 0; i < totalbox; i++) {
                    String gameNumber = "";
                    if (user.getScreen2().getArray_for_boxes().get(i) != null && !user.getScreen2().getArray_for_boxes().get(i).contains("null")) {
                        gameNumber = user.getScreen2().getArray_for_boxes().get(i);
                        for (Game game : Prefrences.getGamesListFromLocal(context, "games")) {
                            if (game.getNumber().equals(gameNumber)) {
                                gamesimages.add(i, game);
                                break;
                            } else {
                                gamesimages.add(i, null);
                            }
                        }
                    } else {
                        gamesimages.add(i, null);
                    }
                }

                //assigning data to orignal array from temp array
                for (int i = 0; i < totalbox; i++) {
                    if (user.getScreen2().getAnimation_for_boxes() != null) {
                        if (user.getScreen2().getAnimation_for_boxes().size() > i) {
                            animatearr.add(i, user.getScreen2().getAnimation_for_boxes().get(i));
                        }else {
                            animatearr.add(i, false);
                        }

                    } else {
                        animatearr.add(i, false);
                    }
                }
            }
            //geting total boxes games
            ArrayList<Game> gamestemp = new ArrayList<>();
            for (int i = 0; i < totalbox; i++) {
                gamestemp.add(gamesimages.get(i));
            }
            Screen2Activity.initrecycler(context, gamestemp, animatearr, user.getScreen2().isShow_header(), user.getScreen2().getOrientation(), user.getScreen2().getTotal_boxes(), user.getScreen2().getEmpty_box(), user.getScreen2().getEmpty_box_custom_image());

        } else if (screenNo.equals("screen3")) {
//            if (user.getScreen3().getOrientation().equals("landscape")) {
//                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            } else {
//                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
            if (user.getScreen3() != null) {
                if (user.getScreen3().getMedia_url() != null) {
                    callback.accept(user.getScreen3().getMedia_type(), user.getScreen3().getMedia_url());
                } else {
                    callback.accept(null, null);
                }
            } else {
                callback.accept(null, null);
            }
        } else if (screenNo.equals("screen4")) {

            if (user.getScreen4() != null) {
                if (user.getScreen4().getMedia_url() != null) {
                    callback.accept(user.getScreen4().getMedia_type(), user.getScreen4().getMedia_url());
                } else {
                    callback.accept(null, null);
                }
            } else {
                callback.accept(null, null);
            }
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


    public static void getGlobalPrices(Context context, BiConsumer<GlobalPrices, Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("global")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (snapshot.exists()) {
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


}
