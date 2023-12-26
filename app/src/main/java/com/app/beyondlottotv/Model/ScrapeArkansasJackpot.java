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

import java.util.HashMap;

public class ScrapeArkansasJackpot extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        String url = "https://www.myarkansaslottery.com/";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements jackpot = doc.getElementsByClass("jackpot");
            StringBuilder data = new StringBuilder();

            for (Element element : jackpot) {
                // Extract the content of each span with the specified classes
                String dollarSign = element.select(".amount-dollar-sign").text();
                String amountNumber = element.select(".amount-number").text();
                String amountWord = element.select(".amount-word").text();
                // Concatenate the extracted values
                String result = dollarSign + amountNumber + " "+amountWord;

                Log.d("TAG", "Jackpotresult: " + result);
                data.append(result).append("\n");
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
            Log.d("ScrapedData", scrapedData);
            String[] gameArrays = scrapedData.split("\n");

            if (gameArrays.length == 6){
                HashMap<String,String> hashMap = new HashMap<>();

                String powerballData = gameArrays[0];
                String megaMillionsData = gameArrays[1];
                String lottoData = gameArrays[2];
                String naturalstateData = gameArrays[3];
                String luckyforlifeData = gameArrays[4];
                String arData = gameArrays[5];

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection("settings");
                DocumentReference documentRef = collectionRef.document("arkansasglobal");

                documentRef.update(new HashMap<String, Object>() {{
                            if (powerballData.length() > 0  && !powerballData.equals(" ")) {
                                put("powerball", powerballData);
                            }
                            if (megaMillionsData.length() > 0  && !megaMillionsData.equals(" ")) {
                                put("megamillion", megaMillionsData);
                            }
                            if (lottoData.length() > 0  && !lottoData.equals(" ")) {
                                put("lotto", lottoData);
                            }
                            if (naturalstateData.length() > 0  && !naturalstateData.equals(" ")) {
                                put("naturalstate", naturalstateData);
                            }
                            if (luckyforlifeData.length() > 0  && !luckyforlifeData.equals(" ")) {
                                put("luckyforlife", luckyforlifeData);
                            }
                            if (arData.length() > 0  && !arData.equals(" ")) {
                                put("ar", arData);
                            }
                        }})
                        .addOnSuccessListener(aVoid -> {
                            Log.d("sucess", "Arkansas updated Jackpot successfully: ");
                        })
                        .addOnFailureListener(e -> {
                            Log.d("failed", "onPostExecute: " + e);
                        });



            }

        } else {
            // Handle the case where scraping failed
            Log.d("Scraping Error", "Failed to scrape data from the Arkansas website.");
        }
    }
}
