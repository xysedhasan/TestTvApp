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

public class ScrapeWebsiteTask extends AsyncTask<Void, Void, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(Void... params) {

        List<String> urls = Arrays.asList(
                "https://www.texaslottery.com/export/sites/lottery/Games/Powerball/index.html",
                "https://www.texaslottery.com/export/sites/lottery/Games/Mega_Millions/index.html",
                "https://www.texaslottery.com/export/sites/lottery/Games/Lotto_Texas/index.html",
                "https://www.texaslottery.com/export/sites/lottery/Games/Texas_Two_Step/index.html",
                "https://www.texaslottery.com/export/sites/lottery/Games/Cash_Five/index.html",
                "https://www.texaslottery.com/export/sites/lottery/Games/All_or_Nothing/Winning_Numbers/details.html_304194979.html",//morning
                "https://www.texaslottery.com/export/sites/lottery/Games/All_or_Nothing/Winning_Numbers/details.html_1818764619.html",//day
                "https://www.texaslottery.com/export/sites/lottery/Games/All_or_Nothing/Winning_Numbers/details.html_1374108449.html",//evening
                "https://www.texaslottery.com/export/sites/lottery/Games/All_or_Nothing/Winning_Numbers/details.html_228350193.html",//night
                "https://www.texaslottery.com/export/sites/lottery/Games/Pick_3/index.html",
                "https://www.texaslottery.com/export/sites/lottery/Games/Daily_4/index.html"
        );

        ArrayList<String> games = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);

            try {
                Document doc = Jsoup.connect(url).get();
                Elements winningNumberBalls;
                winningNumberBalls = doc.getElementsByClass("winningNumberBalls");

                for (int j = 0; j < winningNumberBalls.size(); j++) {
                    games.add(winningNumberBalls.get(j).text());
                    Log.d("gamenumbers", "doInBackground: " + "i" + i + "j" + j + ">" + winningNumberBalls.get(j).text());
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

            String pick3DataMorning = gameArrays.get(9) + " " + gameArrays.get(10).substring(gameArrays.get(10).length() - 1);
            String pick3DataDay = gameArrays.get(11) + " " + gameArrays.get(12).substring(gameArrays.get(12).length() - 1);
            String pick3DataEvening = gameArrays.get(13) + " " + gameArrays.get(14).substring(gameArrays.get(14).length() - 1);
            String pick3DataNight = gameArrays.get(15) + " " + gameArrays.get(16).substring(gameArrays.get(16).length() - 1);

            String daily4DataMorning = gameArrays.get(17) + " " + gameArrays.get(18).substring(gameArrays.get(18).length() - 1);
            String daily4DataDay = gameArrays.get(19) + " " + gameArrays.get(20).substring(gameArrays.get(20).length() - 1);
            String daily4DataEvening = gameArrays.get(21) + " " + gameArrays.get(22).substring(gameArrays.get(22).length() - 1);
            String daily4DataNight = gameArrays.get(23) + " " + gameArrays.get(24).substring(gameArrays.get(24).length() - 1);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionRef = db.collection("settings");
            DocumentReference documentRef = collectionRef.document("winningNumberBalls");

            documentRef.update(new HashMap<String, Object>() {{

                        if (powerballData.length() > 0 && !(powerballData.matches(".*[a-zA-Z].*"))) {
                            put("powerballArray", powerballData);
                        }
                        if (megaMillionsData.length() > 0 && !(megaMillionsData.matches(".*[a-zA-Z].*"))) {
                            put("megaMillionsArray", megaMillionsData);
                        }
                        if (lottoTexasData.length() > 0 && !(lottoTexasData.matches(".*[a-zA-Z].*"))) {
                            put("lottoTexasArray", lottoTexasData);
                        }
                        if (texasTwoStepData.length() > 0 && !(texasTwoStepData.matches(".*[a-zA-Z].*"))) {
                            put("texasTwoStepArray", texasTwoStepData);
                        }
                        if (cashFiveData.length() > 0 && !(cashFiveData.matches(".*[a-zA-Z].*"))) {
                            put("cashFiveArray", cashFiveData);
                        }
                        if (allOrNothingDataMorning.length() > 0 && !(allOrNothingDataMorning.matches(".*[a-zA-Z].*"))) {
                            put("allOrNothingMorningArray", allOrNothingDataMorning);
                        }
                        if (allOrNothingDataDay.length() > 0 && !(allOrNothingDataDay.matches(".*[a-zA-Z].*"))) {
                            put("allOrNothingDayArray", allOrNothingDataDay);
                        }
                        if (allOrNothingDataEvening.length() > 0 && !(allOrNothingDataEvening.matches(".*[a-zA-Z].*"))) {
                            put("allOrNothingEveningArray", allOrNothingDataEvening);
                        }
                        if (allOrNothingDataNight.length() > 0 && !(allOrNothingDataNight.matches(".*[a-zA-Z].*"))) {
                            put("allOrNothingNightArray", allOrNothingDataNight);
                        }
                        if (pick3DataMorning.length() > 0 && !(pick3DataMorning.matches(".*[a-zA-Z].*"))) {
                            put("pick3MorningArray", pick3DataMorning);
                        }
                        if (pick3DataDay.length() > 0 && !(pick3DataDay.matches(".*[a-zA-Z].*"))) {
                            put("pick3DayArray", pick3DataDay);
                        }
                        if (pick3DataEvening.length() > 0 && !(pick3DataEvening.matches(".*[a-zA-Z].*"))) {
                            put("pick3EveningArray", pick3DataEvening);
                        }
                        if (pick3DataNight.length() > 0 && !(pick3DataNight.matches(".*[a-zA-Z].*"))) {
                            put("pick3NightArray", pick3DataNight);
                        }
                        if (daily4DataMorning.length() > 0 && !(daily4DataMorning.matches(".*[a-zA-Z].*"))) {
                            put("daily4Morning", daily4DataMorning);
                        }
                        if (daily4DataDay.length() > 0 && !(daily4DataDay.matches(".*[a-zA-Z].*"))) {
                            put("daily4DayArray", daily4DataDay);
                        }
                        if (daily4DataEvening.length() > 0 && !(daily4DataEvening.matches(".*[a-zA-Z].*"))) {
                            put("daily4EveningArray", daily4DataEvening);
                        }
                        if (daily4DataNight.length() > 0 && !(daily4DataNight.matches(".*[a-zA-Z].*"))) {
                            put("daily4NightArray", daily4DataNight);
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
