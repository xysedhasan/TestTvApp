package com.example.testtvapp.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Prefrences {

    public static void saveUserDetails(Context context, User user ) {

        SharedPreferences prefs = context.getSharedPreferences("userDetail", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putBoolean("isLoggedin", true);

        editor.apply();
    }

    public static boolean isLoggedin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getBoolean("isLoggedin", false);
    }

    public static void setisLoggedin(Context context,Boolean isLoggedin) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedin", isLoggedin);
        editor.apply();
    }


    public static void setGamesListInLocal(Context context, ArrayList<Game> list, String key) {

        SharedPreferences prefs = context.getSharedPreferences("UnreadMessages",  MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }



    public static  ArrayList<Game> getGamesListFromLocal(Context context,String key)
    {
        SharedPreferences prefs = context.getSharedPreferences("UnreadMessages",  MODE_PRIVATE);
        Gson gson = new Gson();
        String json = "";
        json = prefs.getString(key, " ");
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
