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
import java.util.HashMap;


public class ScrapeCaliforniaWebsite extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        try {
            // Replace with the URL of the website you want to scrape
            String url = "https://www.calottery.com/draw-games";

            Document doc = Jsoup.connect(url).get();

            String className = "list-inline draw-cards--winning-numbers";

            // Select all elements with the specified class name
            Elements elements = doc.getElementsByClass(className);

            StringBuilder data = new StringBuilder();

            // Iterate through the selected elements and print their text
            for (Element element : elements) {
                Log.d("WebScraping", element.text());
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
            Log.d("ScrapedData", scrapedData);
            String[] gameArrays = scrapedData.split("\n");

            if (gameArrays.length == 7){
                String powerballData = gameArrays[0];
                String megaMillionsData = gameArrays[1];
                String superlotto = gameArrays[2];
                String fantasy5 = gameArrays[3];
                String cash3dayData = gameArrays[4];
                String cash3eveningData = gameArrays[5];
                String cash4dayData = gameArrays[6];


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection("settings");
                DocumentReference documentRef = collectionRef.document("winningNumbersCalifornia");

                documentRef.set(new HashMap<String, Object>() {{

                            if (powerballData.length() > 0  ) {
                                put("powerballArray", powerballData);
                            }
                            if (megaMillionsData.length() > 0  ) {
                                put("megaMillionsArray", megaMillionsData);
                            }
                            if (superlotto.length() > 0  ) {
                                put("superlottoArray", superlotto);
                            }
                            if (fantasy5.length() > 0  ) {
                                put("fantasy5Array", fantasy5);
                            }
                            if (cash3dayData.length() > 0  ) {
                                put("daily3dayArray", cash3dayData);
                            }
                            if (cash3eveningData.length() > 0  ) {
                                put("daily3eveningArray", cash3eveningData);
                            }
                            if (cash4dayData.length() > 0  ) {
                                put("daily4Array", cash4dayData);
                            }


                        }})
                        .addOnSuccessListener(aVoid -> {
                            Log.d("sucess", "California updated winings successfully: ");
                        })
                        .addOnFailureListener(e -> {
                            Log.d("failed", "onPostExecute: " + e);
                        });



            }

        } else {
            // Handle the case where scraping failed
            Log.d("Scraping Error", "Failed to scrape data from California website.");
        }
    }
}

