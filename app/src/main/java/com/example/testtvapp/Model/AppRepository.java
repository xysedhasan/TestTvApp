package com.example.testtvapp.Model;

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

import com.example.testtvapp.Activities.LoginActivity;
import com.example.testtvapp.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.BiConsumer;

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

                        } else {
                            callback.accept(false, task.getException().getMessage());
                            //Toast.makeText(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


   public static void getGames(Activity activity,Context context ){

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
              Prefrences.setGamesListInLocal(context,gamesarr,"games");

                   getUser(activity,context);
                   Log.d(TAG, "onComplete:gamearr"+gamesarr.size());
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

    public static void getUser(Activity activity,Context context){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String current = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users")
                .document(current)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot , @Nullable FirebaseFirestoreException error) {
                         User user =  snapshot.toObject(User.class);
                        //checklogout
                        checklogout(activity,user,context);
                        getGamesofUser(user,context);

                    }
                });
    }

    public static void getGamesofUser(User user,Context context){
        Log.d(TAG, "getGamesofUser:pref: "+Prefrences.getGamesListFromLocal(context,"games").size());
        Log.d(TAG, "getGamesofUser:ar: "+ user.getArray_for_boxes());

        Game emptyGame = new Game();
        ArrayList<Game> gamesimages = new ArrayList<>();
        for (String usernumber : user.getArray_for_boxes()){
            if (usernumber != null && !usernumber.contains("null")){
                for (Game game : Prefrences.getGamesListFromLocal(context,"games")){
                    if (game.getNumber().equals(usernumber)){
                        gamesimages.add(game);
                    }
                }
            }else {
                gamesimages.add(emptyGame);
            }

        }
        MainActivity.initrecycler(context,gamesimages);
        Log.d(TAG, "getGamesofUser:imagezz "+gamesimages.size());

    }

    public static void checklogout(Activity activity,User user,Context context){
//        if (!user.isLogin_status()){
//            Prefrences.setisLoggedin(context,false);
//            Intent intent = new Intent(activity, LoginActivity.class);
//
//            context.startActivity(intent);
//        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void getGame(ArrayList<String> numberarr, BiConsumer<Boolean, ArrayList<Game>> callback){
          ArrayList<Game> gamesarr = new ArrayList<>();
        for (String number : numberarr){
            if (number != null && !number.contains("null")){
                db.collection("games").whereEqualTo("number",number).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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





}
