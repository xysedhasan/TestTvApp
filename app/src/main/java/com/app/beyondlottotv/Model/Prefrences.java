package com.app.beyondlottotv.Model;

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
        editor.putString("region", user.getRegion());
        editor.putString("id", user.getId());
        editor.putBoolean("isLoggedin", user.isLogin_status());


//        if (screenNo.equals("screen1")) {
//            editor.putString("empty_box_custom_image", user.getScreen1().getEmpty_box_custom_image());
//        } else if (screenNo.equals("screen2")) {
//            editor.putBoolean("show_header", user.getScreen2().isShow_header());
//            editor.putString("empty_box_custom_image", user.getScreen2().getEmpty_box_custom_image());
//        } else if (screenNo.equals("screen3")) {
//            if (user.getScreen3().getOrientation() != null) {
//                editor.putString("orientation", user.getScreen3().getOrientation());
//            }
//        } else if (screenNo.equals("screen4")) {
//            if (user.getScreen4().getOrientation() != null) {
//                editor.putString("orientation", user.getScreen4().getOrientation());
//            }
//
//        } else if (screenNo.equals("screen5")) {
//            if (user.getScreen5().getOrientation() != null) {
//                editor.putString("orientation", user.getScreen5().getOrientation());
//            }
//        }
        editor.apply();
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

    public static void setGamesListInLocal(Context context, ArrayList<Game> list, String key) {

        SharedPreferences prefs = context.getSharedPreferences("localgameslist", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }
    public static void clearGamesListInLocal(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("localgameslist", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.apply();
    }


    public static int getTotalBoxesScreen2(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getInt("total_boxesscreen2", 0);
    }

    public static void setTotalBoxesScreen2(Context context, int verified) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("total_boxesscreen2", verified);
        editor.apply();
    }

    public static int getTotalBoxesScreen3(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getInt("total_boxesscreen3", 0);
    }

    public static void setTotalBoxesScreen3(Context context, int verified) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("total_boxesscreen3", verified);
        editor.apply();
    }


    public static int getTotalBoxesScreen1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getInt("total_boxesscreen1", 0);
    }

    public static void setTotalBoxesScreen1(Context context, int verified) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("total_boxesscreen1", verified);
        editor.apply();
    }

    public static String getRegion(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userDetail", MODE_PRIVATE);
        return prefs.getString("region","Texas");
    }

    public static ArrayList<Game> getGamesListFromLocal(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("localgameslist", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = "";
        json = prefs.getString(key, " ");
        Type type = new TypeToken<ArrayList<Game>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


}
