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


public class ScrapeIdahoWebsite extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        try {
            List<Elements> ulElementsList = new ArrayList<>();
            List<String> urls = Arrays.asList(
                    "https://www.idaholottery.com/games/draw/powerball",
                    "https://www.idaholottery.com/games/draw/mega-millions",
                    "https://www.idaholottery.com/games/draw/lotto-america",
                    "https://www.idaholottery.com/games/draw/lucky-for-life",
                    "https://www.idaholottery.com/games/draw/idaho-cash",
                    "https://www.idaholottery.com/games/draw/5-star-draw",
                    "https://www.idaholottery.com/games/draw/weekly-grand"
            );
            List<String> classes = Arrays.asList(
                    "list-drawgame list-numbers list-numbers--bordered Powerball",
                    "list-drawgame list-numbers list-numbers--bordered MegaMillions",
                    "list-drawgame list-numbers list-numbers--bordered Lotto America",
                    "list-drawgame list-numbers list-numbers--bordered Lucky for Life",
                    "list-drawgame list-numbers list-numbers--bordered Idaho Cash",
                    "list-drawgame list-numbers list-numbers--bordered 5 Star Draw",
                    "list-drawgame list-numbers list-numbers--bordered Weekly Grand"
            );

            for (int i = 0; i < urls.size(); i++) {
                String url = urls.get(i);
                String ulClassName = classes.get(i);

                // Connect to the website and retrieve the HTML document
                Document doc = Jsoup.connect(url).get();

                // Specify the class name of the elements you want to scrape
                String divClassName = "gamepage--winningnumbers";

                // Select the div containing the ul element
                Element divElement = doc.getElementsByClass(divClassName).first();

                // Select the ul element
                Elements ulElement = divElement.getElementsByClass(ulClassName);

                ulElementsList.add(ulElement);
            }
            StringBuilder data = new StringBuilder();

            for (Elements element : ulElementsList) {
                Log.d("IdsdScraper", element.text());
                data.append(element.text()).append("\n");
            }
             return data.toString().trim();
        } catch (Exception e) {
            Log.d("TAG", "doInBackgrounderror: "+e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String scrapedData) {
        if (scrapedData != null) {
            String[] gameArrays = scrapedData.split("\n");

            if (gameArrays.length == 7){
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
                DocumentReference documentRef = collectionRef.document("winningNumbersIdaho");

                documentRef.update(new HashMap<String, Object>() {{

                            if (powerballData.length() > 0  ) {
                                put("powerballArray", powerballData);
                            }
                            if (megaMillionsData.length() > 0  ) {
                                put("megaMillionsArray", megaMillionsData);
                            }
                            if (lottoAmericaData.length() > 0  ) {
                                put("lottoAmericaArray", lottoAmericaData);
                            }
                            if (luckyforlifeData.length() > 0  ) {
                                put("luckyforlifeArray", luckyforlifeData);
                            }
                            if (idhaoCash.length() > 0  ) {
                                put("idhaoCashArray", idhaoCash);
                            }
                            if (fivestartdraw.length() > 0  ) {
                                put("fivestartdrawArray", fivestartdraw);
                            }
                            if (weeklygrandData.length() > 0  ) {
                                put("weeklygrandArray", weeklygrandData);
                            }
                        }})
                        .addOnSuccessListener(aVoid -> {
                            Log.d("sucess", "Idaho winings updated successfully: ");
                        })
                        .addOnFailureListener(e -> {
                            Log.d("failed", "onPostExecute: " + e);
                        });
            }

        } else {
            // Handle the case where scraping failed
            Log.d("Scraping Error", "Failed to scrape data from Iadho website.");
        }
    }
}

