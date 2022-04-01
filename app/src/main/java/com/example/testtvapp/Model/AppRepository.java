package com.example.testtvapp.Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.testtvapp.Activities.LoginActivity;
import com.example.testtvapp.Activities.Screen1Activity;
import com.example.testtvapp.Activities.Screen2Activity;
import com.example.testtvapp.Activities.Screen3Activity;
import com.example.testtvapp.Activities.Screen4Activity;
import com.example.testtvapp.Activities.Screen5Activity;
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

    public static void getGames(Activity activity, Context context, String screenNo) {

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

                   // getUser(activity, context, screenNo);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getUser(Activity activity, Context context, String screenNo,BiConsumer<Boolean,UserNew> callback) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String current = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users")
                .document(current)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        UserNew user = snapshot.toObject(UserNew.class);
                        //checklogout

                        callback.accept(true,user);
                        checklogout(activity, user.isLogin_status(), context);
                        getGamesofUser(activity, user, context, screenNo);

                    }
                });
    }






    public static void getGamesofUser(Activity activity, UserNew user, Context context, String screenNo) {


        Game emptyGame = new Game();
        ArrayList<Game> gamesimages = new ArrayList<>();
        ArrayList<Boolean> animatearr = new ArrayList<>();
        animatearr.clear();
        Log.d(TAG, "getGamesofUser: " + user.getScreen3());
        Prefrences.saveUserDetails(context, user, screenNo);
        if (screenNo.equals("screen1")) {

            if (user.getScreen1().getOrientation().equals("landscape")) {
//                activity.getRequestedOrientation();
//                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                activity.setRequestedOrientation(0);
            } else {
//                activity.getRequestedOrientation();
//                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                activity.setRequestedOrientation(1);
            }


            int totalbox = user.getScreen1().getTotal_boxes();
            ArrayList<String> temp = new ArrayList<>();
            ArrayList<Boolean> tempAnimation = new ArrayList<>();
            temp.clear();
            gamesimages.clear();
            if (user.getScreen1().getArray_for_boxes().size() < totalbox) {

                for (int i = 0; i < totalbox; i++) {
                    temp.add("null");
                    tempAnimation.add(false);
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
                                gamesimages.add(i, emptyGame);
                            }
                        }
                    } else {
                        gamesimages.add(i, emptyGame);
                    }
                }

                //assigning data temp animation array
                for (int i = 0; i < user.getScreen1().getAnimation_for_boxes().size(); i++) {
                    tempAnimation.set(i, user.getScreen1().getAnimation_for_boxes().get(i));
                }

                //assigning data to orignal array from temp array
                for (int i = 0; i < totalbox; i++) {
                    if (user.getScreen1().getAnimation_for_boxes() != null) {
                        animatearr.add(i, tempAnimation.get(i));
                    } else {
                        animatearr.add(i, false);
                    }
                }

            } else {

                for (int i = 0; i < totalbox; i++) {
                    String gameNumber = "";
                    if (user.getScreen1().getArray_for_boxes().get(i) != null && !user.getScreen1().getArray_for_boxes().get(i).contains("null")) {
                        gameNumber = user.getScreen1().getArray_for_boxes().get(i);
                        for (Game game : Prefrences.getGamesListFromLocal(context, "games")) {
                            if (game.getNumber().equals(gameNumber)) {
                                gamesimages.add(i, game);
                                break;
                            } else {
                                gamesimages.add(i, emptyGame);
                            }
                        }
                    } else {
                        gamesimages.add(i, emptyGame);

                    }
                }

                //assigning data to orignal array from temp array
                for (int i = 0; i < totalbox; i++) {
                    if (user.getScreen1().getAnimation_for_boxes() != null) {
                        animatearr.add(i, user.getScreen1().getAnimation_for_boxes().get(i));
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
            Screen1Activity.initrecycler(context, gamestemp, animatearr,user.getScreen1().getOrientation(),user.getScreen1().getTotal_boxes());
        } else if (screenNo.equals("screen2")) {

            if (user.getScreen2().getOrientation().equals("landscape")) {
                Log.d(TAG, "landscape: ");
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                Log.d(TAG, "portrait: ");
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            int totalbox = user.getScreen2().getTotal_boxes();
            ArrayList<String> temp = new ArrayList<>();
            ArrayList<Boolean> tempAnimation = new ArrayList<>();
            if (user.getScreen2().getArray_for_boxes().size() < totalbox) {
                for (int i = 0; i < totalbox; i++) {
                    temp.add("null");
                    tempAnimation.add(false);
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
                                gamesimages.add(i, emptyGame);
                            }
                        }
                    } else {
                        gamesimages.add(i, emptyGame);
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
                        animatearr.add(i, tempAnimation.get(i));
                    } else {
                        animatearr.add(i, false);
                    }
                }

            } else {
                for (int i = 0; i < totalbox; i++) {
                    String gameNumber = "";
                    if (user.getScreen2().getArray_for_boxes().get(i) != null && !user.getScreen2().getArray_for_boxes().get(i).contains("null")) {
                        gameNumber = user.getScreen2().getArray_for_boxes().get(i);
                        for (Game game : Prefrences.getGamesListFromLocal(context, "games")) {
                            if (game.getNumber().equals(gameNumber)) {
                                gamesimages.add(i, game);
                                break;
                            } else {
                                gamesimages.add(i, emptyGame);
                            }
                        }
                    } else {
                        gamesimages.add(i, emptyGame);

                    }
                }

                //assigning data to orignal array from temp array
                for (int i = 0; i < totalbox; i++) {
                    if (user.getScreen2().getAnimation_for_boxes() != null) {
                        animatearr.add(i, user.getScreen2().getAnimation_for_boxes().get(i));
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
            Screen2Activity.initrecycler(context, gamestemp, animatearr,user.getScreen2().getOrientation(),user.getScreen2().getTotal_boxes());

        } else if (screenNo.equals("screen3")) {
            if (user.getScreen3().getOrientation().equals("landscape")) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            Screen3Activity.setMediaData(context, user.getScreen3().getMedia_type(), user.getScreen3().getMedia_url(), user.getScreen3().getOrientation());
        } else if (screenNo.equals("screen4")) {
            if (user.getScreen4().getOrientation().equals("landscape")) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            Screen4Activity.setMediaData(context, user.getScreen4().getMedia_type(), user.getScreen4().getMedia_url(), user.getScreen4().getOrientation());
        } else if (screenNo.equals("screen5")) {
            if (user.getScreen5().getOrientation().equals("landscape")) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            Screen5Activity.setMediaData(context, user.getScreen5().getMedia_type(), user.getScreen5().getMedia_url(), user.getScreen5().getOrientation());
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


    @RequiresApi(api = Build.VERSION_CODES.N)
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
    }


    public static void getGlobalPrices(Context context, Consumer<GlobalPrices> callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("settings")
                .document("global")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        GlobalPrices prices = snapshot.toObject(GlobalPrices.class);
                        callback.accept(prices);

                    }
                });
    }


}
