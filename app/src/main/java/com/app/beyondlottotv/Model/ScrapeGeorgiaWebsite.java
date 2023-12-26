package com.app.beyondlottotv.Model;


import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

import io.grpc.internal.AbstractClientStream;


public class ScrapeGeorgiaWebsite extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        String url = "https://www.lotteryusa.com/georgia/";

        try {
             Document doc = Jsoup.connect(url).get();

            String className = "c-result c-result-card__result-list ";
            Elements elements = doc.getElementsByClass(className);

            StringBuilder data = new StringBuilder();

            for (Element element : elements) {
                Log.d("TAG", "delemntckground: "+element);
                data.append(element.text()).append("\n");
            }

            return data.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String scrapedData) {
        if (scrapedData != null) {
            Log.d("ScrapedgeorgiaData", scrapedData);
            String[] gameArrays = scrapedData.split("\n");

            if (gameArrays.length == 18) {
                String cash3day = gameArrays[0];
                String cash3evening = gameArrays[1];
                String cash4day = gameArrays[3];
                String cash4evening = gameArrays[4];
                String cashpop = gameArrays[8];
                String keno = gameArrays[5];
                String megaMillionsData = gameArrays[16];
                String powerballData = gameArrays[15];
                String fantasy5 = gameArrays[13];
                String cash4life = gameArrays[17];
                String georgia5day = gameArrays[6];
                String georgia5evening = gameArrays[7];
                String jumbobucks = gameArrays[14];

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection("settings");
                DocumentReference documentRef = collectionRef.document("winningNumbersGeorgia");

                documentRef.update(new HashMap<String, Object>() {{
                            if (cash3day.length() > 0) {
                                put("cash3dayArray", cash3day);
                            }
                            if (cash3evening.length() > 0) {
                                put("cash3eveningArray", cash3evening);
                            }
                            if (cash4day.length() > 0) {
                                put("cash4dayArray", cash4day);
                            }
                            if (cash4evening.length() > 0) {
                                put("cash4eveningArray", cash4evening);
                            }
                            if (cashpop.length() > 0) {
                                put("cashpopDayArray", cashpop);
                            }
                            if (keno.length() > 0) {
                                put("cashpopEveningArray", keno);
                            }
                            if (megaMillionsData.length() > 0) {
                                put("megaMillionsArray", megaMillionsData.replace("MB Megaplier: x3", "").trim());

                            }
                            if (powerballData.length() > 0 && powerballData.contains("PB Power Play: x3")) {
                                put("powerballArray", powerballData.replace("PB Power Play: x3", "").trim());
                            }
                            if (fantasy5.length() > 0) {
                                put("fantasy5Array", fantasy5);
                            }
                            if (cash4life.length() > 0  && cash4life.contains("CB")) {
                                put("cash4lifeArray", cash4life.replace("CB", "").trim());
                            }
                            if (georgia5day.length() > 0) {
                                put("georgia5dayArray", georgia5day);
                            }
                            if (georgia5evening.length() > 0) {
                                put("georgia5eveningArray", georgia5evening);
                            }
                            if (jumbobucks.length() > 0) {
                                put("jumbobucksArray", jumbobucks);
                            }
                        }})
                        .addOnSuccessListener(aVoid -> {
                            Log.d("sucess", "Georgia updated winings successfully: ");
                        })
                        .addOnFailureListener(e -> {
                            Log.d("failed", "onPostExecute: " + e);
                        });
            }
        } else {
            // Handle the case where scraping failed
            Log.d("Scraping Error", "Failed to scrape data from Georgia website.");
        }
    }
}
