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
import java.util.HashMap;

public class ScrapeCaliforniaJackpot extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        try {
            // Replace with the URL of the website you want to scrape
            String url = "https://www.calottery.com/draw-games";

            Document doc = Jsoup.connect(url).get();

            String className = "draw-cards--lottery-amount";

            // Select all elements with the specified class name
            Elements elements = doc.getElementsByClass(className);

            StringBuilder data = new StringBuilder();

            // Iterate through the selected elements and print their text
            for (Element element : elements) {
                Log.d("WebjackpotScraping", element.text());
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
            Log.d("ScrapedcalData", scrapedData);
            String[] gameArrays = scrapedData.split("\n");

            Log.d("TAG", "lengthe: "+gameArrays.length);
            if (gameArrays.length == 9) {

                String powerballData = gameArrays[0];
                String megaMillionsData = gameArrays[1];
                String superlotto = gameArrays[2];
                String fantasy5 = gameArrays[3];
                String cash3dayData = gameArrays[4];
                String cash4dayData = gameArrays[5];
                String dailyderbyData = gameArrays[6];
                String hotspotData = gameArrays[7];

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection("settings");
                DocumentReference documentRef = collectionRef.document("californiaglobal");

                documentRef.set(new HashMap<String, Object>() {{
                            if (powerballData.length() > 0 && !powerballData.equals(" ")) {
                                put("powerball", powerballData);
                            }
                            if (megaMillionsData.length() > 0 && !megaMillionsData.equals(" ")) {
                                put("megamillion", megaMillionsData);
                            }
                            if (superlotto.length() > 0 && !superlotto.equals(" ")) {
                                put("superlottoArray", superlotto);
                            }
                            if (fantasy5.length() > 0 && !fantasy5.equals(" ")) {
                                put("fantasy5", fantasy5);
                            }
                            if (cash3dayData.length() > 0 && !cash3dayData.equals(" ")) {
                                put("daily3", cash3dayData);
                            }
                            if (dailyderbyData.length() > 0 && !dailyderbyData.equals(" ")) {
                                put("dailyderby", dailyderbyData);
                            }
                            if (cash4dayData.length() > 0 && !cash4dayData.equals(" ")) {
                                put("daily4", cash4dayData);
                            }
                            if (hotspotData.length() > 0 && !hotspotData.equals(" ")) {
                                put("hotspot", hotspotData);
                            }
                        }})
                        .addOnSuccessListener(aVoid -> {
                            Log.d("sucess", "California Jackpot updated successfully: ");
                        })
                        .addOnFailureListener(e -> {
                            Log.d("failed", "onPostExecute: " + e);
                        });


            }

        } else {
            // Handle the case where scraping failed
            Log.d("Scraping Error", "Failed to scrape california jackpot data from the website.");
        }
    }
}

