package com.app.beyondlottotv.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ScrapeFloridaWebsiteTask extends AsyncTask<Void, Void, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(Void... params) {

        List<String> urls = Arrays.asList(
                "https://flalottery.com/powerball",
                "https://flalottery.com/megaMillions",
                "https://flalottery.com/lotto",
                "https://flalottery.com/cash4Life",
                "https://flalottery.com/jackpotTriplePlay",
                "https://flalottery.com/cashPop",
                "https://flalottery.com/fantasy5",
                "https://flalottery.com/pick5",
                "https://flalottery.com/pick4",
                "https://flalottery.com/pick3",
                "https://flalottery.com/pick2"
        );

        List<String> classes = Arrays.asList(
                "gamePageBalls",
                "gamePageBalls",
                "gamePageBalls",
                "gamePageBalls",
                "gamePageBalls",
                "gamePageNumbers",
                "gamePageBalls",
                "Winning Numbers:",
                "Winning Numbers:",
                "Winning Numbers:",
                "Winning Numbers:"
        );
        ArrayList<String> games = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);

            try {
                Document doc = Jsoup.connect(url).get();
                Elements winningNumberBalls;
                winningNumberBalls = doc.getElementsByClass(classes.get(i));

                for (int j = 0; j < winningNumberBalls.size(); j++) {
                    games.add(winningNumberBalls.get(j).text());
                    Log.d("gamenumbersflorida", ": " + "i" + i + "j" + j + ">" + winningNumberBalls.get(j).text());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return games;
    }

    @Override
    protected void onPostExecute(ArrayList<String> gameArrays) {

        Log.d("TAG", "floridaSize: " + gameArrays.size());
        try {
            String powerballData = gameArrays.get(0);
            String megaMillionsData = gameArrays.get(2);
            String lottoData = gameArrays.get(3);
            String cash4life = gameArrays.get(5);
            String jackpot = gameArrays.get(6);
            String cashpop = gameArrays.get(7);
            String fantasy5 = gameArrays.get(12);
            String pick5 = gameArrays.get(7);
            String pick4 = gameArrays.get(8);
            String pick3 = gameArrays.get(9);
            String pick2 = gameArrays.get(10);


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionRef = db.collection("settings");
            DocumentReference documentRef = collectionRef.document("winningNumberFlorida");

            documentRef.set(new HashMap<String, Object>() {{
                        if (powerballData.length() > 0 && !powerballData.equals(" ")) {
                            String result = powerballData.replaceAll("x\\d+ Jackpot Winner\\(s\\) \\d+ in: [A-Za-z]+", "")
                                    // Replace all "-" with a space
                                    .replaceAll("-", " ");
                            put("powerball", result.trim());
                        }
                        if (megaMillionsData.length() > 0 && !megaMillionsData.equals(" ")) {
                            String result = megaMillionsData.replaceAll("-x\\d+ Rollover", "")
                                    // Replace all "-" with a space
                                    .replaceAll("-", " ");
                            put("megamillions", result.trim());
                        }
                        if (lottoData.length() > 0 && !lottoData.equals(" ")) {
                            String result = lottoData.replaceAll("Rollover", "")
                                    .replaceAll("-", " ");
                            put("lotto", result.trim());
                        }
                        if (cash4life.length() > 0 && !cash4life.equals(" ")) {
                            String result = cash4life.replaceAll("-", " ");
                            put("cash4life", result.trim());
                        }
                        if (jackpot.length() > 0 && !jackpot.equals(" ")) {
                            String result = jackpot.replaceAll("Rollover", "")
                                    .replaceAll("-", " ");
                            put("jackpot", result.trim());
                        }
                        if (cashpop.length() > 0 && !cashpop.equals(" ")) {
                            String lastChar = String.valueOf(cashpop.charAt(cashpop.length() - 1));
                            put("cashpop", lastChar);
                        }
                        if (fantasy5.length() > 0 && !fantasy5.equals(" ")) {
                            String result = fantasy5.replaceAll("Rolldown", "")
                                    .replaceAll("-", " ");
                            put("fantasy5", result.trim());
                        }
                        if (pick5.length() > 0 && !pick5.equals(" ")) {
                            put("pick5", pick5);
                        }
                        if (pick4.length() > 0 && !pick4.equals(" ")) {
                            put("pick4", pick4);
                        }
                        if (pick3.length() > 0 && !pick3.equals(" ")) {
                            put("pick3", pick3);
                        }
                        if (pick2.length() > 0 && !pick2.equals(" ")) {
                            put("pick2", pick2);
                        }
                    }})
                    .addOnSuccessListener(aVoid -> {
                        Log.d("sucess", "florida updated winings successfully: ");
                    })
                    .addOnFailureListener(e -> {
                        Log.d("failed", "onPostExecute: " + e);
                    });
        } catch (Exception e) {
            Log.d("exception", "onPostExecute: " + e);
        }
    }
}
