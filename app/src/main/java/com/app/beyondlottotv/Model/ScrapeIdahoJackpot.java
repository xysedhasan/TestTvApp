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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ScrapeIdahoJackpot extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        String url = "https://www.idaholottery.com/";

        try {
            Document doc = Jsoup.connect(url).get();
//            Elements jackpot = doc.getElementsByClass("jackpot__content");
//            StringBuilder data = new StringBuilder();
//
//            for (Element element : jackpot) {
//                Log.d("idhaojackpot", element.text());
//                data.append(element.text()).append("\n");
//            }

            Elements jackpot = doc.getElementsByClass("jackpot__content");
            StringBuilder data = new StringBuilder();

            for (Element element : jackpot) {
                if (!element.hasClass("slider__annuitized")) {
                    Log.d("idahoJackpot", element.text());
                    data.append(element.text()).append("\n");
                }
            }
            return data.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(String scrapedData) {
        if (scrapedData != null) {
            String[] gameArrays = scrapedData.split("\n");

            if (gameArrays.length == 14){
                HashMap<String,String> hashMap = new HashMap<>();
                String powerballData = gameArrays[0];
                String megaMillionsData = gameArrays[1];
                String lottoAmericaData = gameArrays[2];
                String luckyforlifeData = gameArrays[3];
                String idhaoCash = gameArrays[4];
                String fivestartdraw = gameArrays[5];
                String weeklygrandData = gameArrays[6];

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection("settings");
                DocumentReference documentRef = collectionRef.document("idahoglobal");
                documentRef.update(new HashMap<String, Object>() {{
                            if (powerballData.length() > 0  && powerballData.contains("annuitized amount")) {
                                put("powerball", powerballData.replace("annuitized amount", "").trim());
                            }
                            if (megaMillionsData.length() > 0  && megaMillionsData.contains("annuitized amount")) {
                                put("megamillion", megaMillionsData.replace("annuitized amount", "").trim());
                            }
                            if (lottoAmericaData.length() > 0 && megaMillionsData.contains("annuitized amount") ) {
                                put("lottoAmerica", lottoAmericaData.replace("annuitized amount", "").trim());
                            }
                            if (luckyforlifeData.length() > 0 && megaMillionsData.contains("a DAY for LIFE")  ) {
                                put("luckyforlife", luckyforlifeData.replace("a DAY for LIFE", "").trim());
                            }
                            if (idhaoCash.length() > 0 && megaMillionsData.contains("thousand") ) {
                                put("idahocash", idhaoCash.replace("thousand", "k").trim());
                            }
                            if (fivestartdraw.length() > 0  && megaMillionsData.contains("thousand")) {
                                put("fivestar", fivestartdraw.replace("thousand", "k").trim());
                            }
                            if (weeklygrandData.length() > 0  && megaMillionsData.contains("a week for a year")) {
                                put("weeklygrand", weeklygrandData.replace("a week for a year", "").trim());
                            }
                        }})
                        .addOnSuccessListener(aVoid -> {
                            Log.d("sucess", "Idaho Jackpot updated successfully: ");
                        })
                        .addOnFailureListener(e -> {
                            Log.d("failed", "onPostExecute: " + e);
                        });
            }

        } else {
            // Handle the case where scraping failed
            Log.d("Scraping Error", "Failed to scrape data from Iadho Jackpot.");
        }
    }
}

