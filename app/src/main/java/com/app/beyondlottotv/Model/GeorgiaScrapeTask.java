package com.app.beyondlottotv.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GeorgiaScrapeTask extends AsyncTask<Void, Void, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(Void... params) {

        List<String> urls = Arrays.asList(
                "https://www.galottery.com/en-us/games/draw-games/cash-three.html",
                "https://www.galottery.com/en-us/games/draw-games/cash-four.html",
                "https://www.galottery.com/en-us/games/draw-games/cash-pop.html",
                "https://www.galottery.com/en-us/games/draw-games/keno.html",
                "https://www.galottery.com/en-us/games/draw-games/megamillions.html",
                "https://www.galottery.com/en-us/games/draw-games/powerball.html",
                "https://www.galottery.com/en-us/games/draw-games/fantasy-five.html",
                "https://www.galottery.com/en-us/games/draw-games/cash-for-life.html",
                "https://www.galottery.com/en-us/games/draw-games/georgia-five.html",
                "https://www.galottery.com/en-us/games/draw-games/jumbo-bucks-lotto.html"

        );

        ArrayList<String> games = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);

            try {
                Document doc = Jsoup.connect(url).get();
                Elements winningNumberBalls;
                winningNumberBalls = doc.getElementsByClass("lotto-numbers");

                for (int j = 0; j < winningNumberBalls.size(); j++) {
                    games.add(winningNumberBalls.get(j).text());
                    Log.d("georgiagamenumbers", "doInBackground: " + "i" + i + "j" + j + ">" + winningNumberBalls.get(j).text());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return games;
    }

    @Override
    protected void onPostExecute(ArrayList<String> gameArrays) {

        try {
            String powerballData = gameArrays.get(0);
            String megaMillionsData = gameArrays.get(1);
            String lottoTexasData = gameArrays.get(2);
            String texasTwoStepData = gameArrays.get(3);
            String cashFiveData = gameArrays.get(4);

            String allOrNothingDataMorning = gameArrays.get(5);
            String allOrNothingDataDay = gameArrays.get(6);
            String allOrNothingDataEvening = gameArrays.get(7);
            String allOrNothingDataNight = gameArrays.get(8);
            String jumbobucks = gameArrays.get(9);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionRef = db.collection("settings");
            DocumentReference documentRef = collectionRef.document("georgiawinningnumbers");

            documentRef.update(new HashMap<String, Object>() {{

                        if (powerballData.length() > 0 && !(powerballData.matches(".*[a-zA-Z].*"))) {
                            put("cash3", powerballData);
                        }
                        if (megaMillionsData.length() > 0 && !(megaMillionsData.matches(".*[a-zA-Z].*"))) {
                            put("cash4", megaMillionsData);
                        }
                        if (lottoTexasData.length() > 0 && !(lottoTexasData.matches(".*[a-zA-Z].*"))) {
                            put("cashpop", lottoTexasData);
                        }
                        if (texasTwoStepData.length() > 0 && !(texasTwoStepData.matches(".*[a-zA-Z].*"))) {
                            put("keno", texasTwoStepData);
                        }
                        if (cashFiveData.length() > 0 && !(cashFiveData.matches(".*[a-zA-Z].*"))) {
                            put("megamillions", cashFiveData);
                        }
                        if (allOrNothingDataMorning.length() > 0 && !(allOrNothingDataMorning.matches(".*[a-zA-Z].*"))) {
                            put("powerball", allOrNothingDataMorning);
                        }
                        if (allOrNothingDataDay.length() > 0 && !(allOrNothingDataDay.matches(".*[a-zA-Z].*"))) {
                            put("fantasy5", allOrNothingDataDay);
                        }
                        if (allOrNothingDataEvening.length() > 0 && !(allOrNothingDataEvening.matches(".*[a-zA-Z].*"))) {
                            put("cash4life", allOrNothingDataEvening);
                        }
                        if (allOrNothingDataNight.length() > 0 && !(allOrNothingDataNight.matches(".*[a-zA-Z].*"))) {
                            put("georgia5", allOrNothingDataNight);
                        }

                        if (jumbobucks.length() > 0 && !(jumbobucks.matches(".*[a-zA-Z].*"))) {
                            put("georgia5", jumbobucks);
                        }
                    }})
                    .addOnSuccessListener(aVoid -> {
                        // Data successfully written to Firestore
                        // Add any desired success handling here
                        Log.d("sucess", "updated winings successfully: ");
                    })
                    .addOnFailureListener(e -> {
                        Log.d("failed", "onPostExecute: " + e);
                    });
        } catch (Exception e) {
            Log.d("exception", "onPostExecute: " + e);
        }
    }

}

