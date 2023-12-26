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


public class ScrapeGeorgiaJackpot extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = "https://www.lotteryusa.com/georgia/";
            Document doc = Jsoup.connect(url).get();
            String className = "c-result-card__prize-value";
            Elements elements = doc.getElementsByClass(className);
            StringBuilder data = new StringBuilder();

            for (Element element : elements) {
                Log.d("GeorgiajackpotScraping", element.text());
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
            Log.d("ScrapedgeorgiajpData", scrapedData);
            String[] gameArrays = scrapedData.split("\n");

            for (int i=0; i<gameArrays.length;i++){
                Log.d("TAG", "ongametExecute: "+i+gameArrays[i]);
            }
            if (gameArrays.length >= 35) {


                String cash3evening = gameArrays[1];
                String cash4day = gameArrays[3];
                String cash4evening = gameArrays[4];
                String cashpop = gameArrays[8];
                String keno = gameArrays[5];
                String megaMillionsData = gameArrays[33];
                String powerballData = gameArrays[31];
                String fantasy5 = gameArrays[27];
                String cash4life = gameArrays[17];
                String georgia5day = gameArrays[6];
                String georgia5evening = gameArrays[7];
                String jumbobucks = gameArrays[29];

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection("settings");
                DocumentReference documentRef = collectionRef.document("georgiaglobal");

                documentRef.update(new HashMap<String, Object>() {{
                            if (megaMillionsData.length() > 0) {
                                put("megamillions", megaMillionsData);
                            }
                            if (powerballData.length() > 0) {
                                put("powerball", powerballData);
                            }
                            if (fantasy5.length() > 0) {
                                put("fantasy5", fantasy5);
                            }
                            if (jumbobucks.length() > 0) {
                                put("jumbobucks", jumbobucks);
                            }
                        }})
                        .addOnSuccessListener(aVoid -> {
                            Log.d("sucess", "Georgia updated jackpt successfully: ");
                        })
                        .addOnFailureListener(e -> {
                            Log.d("failed", "onPostExecute: " + e);
                        });
            }
        } else {
            // Handle the case where scraping failed
            Log.d("Scraping Error", "Failed to scrape jackpot data from Georgia website.");
        }
    }
}
