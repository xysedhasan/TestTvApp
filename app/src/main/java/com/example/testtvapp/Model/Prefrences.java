package com.example.testtvapp.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Prefrences {

    public static void saveUserDetails(Context context, UserNew user, String screenNo) {

        SharedPreferences prefs = context.getSharedPreferences("userDetail", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putBoolean("isLoggedin", user.isLogin_status());


        if (screenNo.equals("screen1")) {
            editor.putBoolean("show_header", user.getScreen1().isShow_header());
            editor.putString("orientation", user.getScreen1().getOrientation());
            editor.putInt("noOfBoxes", user.getScreen1().getTotal_boxes());
            editor.putString("empty_box", user.getScreen1().getEmpty_box());
            editor.putString("empty_box_custom_image", user.getScreen1().getEmpty_box_custom_image());
        } else if (screenNo.equals("screen2")) {
            editor.putBoolean("show_header", user.getScreen2().isShow_header());
            editor.putString("orientation", user.getScreen1().getOrientation());
            editor.putInt("noOfBoxes", user.getScreen2().getTotal_boxes());
            editor.putString("empty_box", user.getScreen2().getEmpty_box());
            editor.putString("empty_box_custom_image", user.getScreen2().getEmpty_box_custom_image());
        } else if (screenNo.equals("screen3")) {
            if (user.getScreen3().getOrientation()!=null){
                editor.putString("orientation", user.getScreen3().getOrientation());
            }

        } else if (screenNo.equals("screen4")) {
            if (user.getScreen4().getOrientation() != null){
                editor.putString("orientation", user.getScreen4().getOrientation());
            }

        } else if (screenNo.equals("screen5")) {
            if (user.getScreen5().getOrientation() != null){
                editor.putString("orientation", user.getScreen5().getOrientation());
            }
        }
        editor.apply();
    }





    public static boolean isShowHeader(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getBoolean("show_header", true);
    }


    public static boolean isLoggedin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getBoolean("isLoggedin", false);
    }

    public static void setisLoggedin(Context context, Boolean isLoggedin) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedin", isLoggedin);
        editor.apply();
    }


    public static String getEmptyBox(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getString("empty_box", "");
    }

    public static String getEmptyBoxesCustomImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getString("empty_box_custom_image", "");
    }

    public static int getNumberOfBoxes(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getInt("noOfBoxes", 0);
    }

    public static void setNumberOfBoxes(Context context, int noofboxes) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("noOfBoxes", noofboxes);
        editor.apply();
    }


    public static void setGamesListInLocal(Context context, ArrayList<Game> list, String key) {

        SharedPreferences prefs = context.getSharedPreferences("UnreadMessages", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }


    public static ArrayList<Game> getGamesListFromLocal(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("UnreadMessages", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = "";
        json = prefs.getString(key, " ");
        Type type = new TypeToken<ArrayList<Game>>() {
        }.getType();
        return gson.fromJson(json, type);
    }




    public static String getOrientation(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getString("orientation", "");
    }

}
