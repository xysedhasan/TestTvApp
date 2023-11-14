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

public class ScrapeFloridaJackpot extends AsyncTask<Void, Void, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(Void... params) {

        List<String> urls = Arrays.asList(
                "https://flalottery.com/powerball",
                "https://flalottery.com/megaMillions",
                "https://flalottery.com/lotto",
                "https://flalottery.com/jackpotTriplePlay"
        );

        ArrayList<String> games = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);

            try {
                Document doc = Jsoup.connect(url).get();
                Elements winningNumberBalls;
                winningNumberBalls = doc.getElementsByClass("gameJackpot");

                for (int j = 0; j < winningNumberBalls.size(); j++) {
                    games.add(winningNumberBalls.get(j).text());
                    Log.d("floridajackpot", "f: " + "i" + i + "j" + j + ">" + winningNumberBalls.get(j).text());
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
            String lottoData = gameArrays.get(2);
//            String cash4life = gameArrays.get(3);
            String jackpot = gameArrays.get(3);

//            String cashpop = gameArrays.get(5);
//            String fantasy5 = gameArrays.get(6);
//            String pick5 = gameArrays.get(7);
//            String pick4 = gameArrays.get(8);
//            String pick3 = gameArrays.get(9);
//            String pick2 = gameArrays.get(10);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionRef = db.collection("settings");
            DocumentReference documentRef = collectionRef.document("foridaglobal");

            documentRef.set(new HashMap<String, Object>() {{
                        if (powerballData.length() > 0 && !powerballData.equals(" ")) {
                            put("powerball", powerballData);
                        }
                        if (megaMillionsData.length() > 0 && !megaMillionsData.equals(" ")) {
                            put("megamillions", megaMillionsData);
                        }
                        if (lottoData.length() > 0 && !lottoData.equals(" ")) {
                            put("lotto", lottoData);
                        }
//                        if (cash4life.length() > 0 && !cash4life.equals(" ")) {
//                            put("cash4life", cash4life);
//                        }
                        if (jackpot.length() > 0 && !jackpot.equals(" ")) {
                            put("jackpot", jackpot);
                        }
                    }})
                    .addOnSuccessListener(aVoid -> {
                        Log.d("sucess", "Florida updated Jackpot successfully: ");
                    })
                    .addOnFailureListener(e -> {
                        Log.d("failed", "onPostExecute: " + e);
                    });

        } catch (Exception e) {

        }
    }
}

