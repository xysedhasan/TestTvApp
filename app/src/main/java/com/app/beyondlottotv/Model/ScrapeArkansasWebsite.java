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


public class ScrapeArkansasWebsite extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        String url = "https://www.myarkansaslottery.com/";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements drawGameNumbers = doc.getElementsByClass("draw-game__numbers");
            Elements jackpot = doc.getElementsByClass("jackpot");

            StringBuilder data = new StringBuilder();
            for (Element element : drawGameNumbers) {
                data.append(element.text()).append("\n");
            }


            for (Element element : jackpot) {
                // Extract the content of each span with the specified classes
                String dollarSign = element.select(".amount-dollar-sign").text();
                String amountNumber = element.select(".amount-number").text();
                String amountWord = element.select(".amount-word").text();
                // Concatenate the extracted values
                String result = dollarSign + amountNumber + amountWord;

                Log.d("TAG", "Jackpotresult: " + result);
            }

            for (Element element : jackpot) {
                Log.d("TAG", "djackpotground: +element"+element);

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

            if (gameArrays.length == 9){
                HashMap<String,String> hashMap = new HashMap<>();

                String powerballData = gameArrays[0];
                String megaMillionsData = gameArrays[1];
                String lottoData = gameArrays[2];
                String naturalstateData = gameArrays[3];
                String luckyforlifeData = gameArrays[4];
                String cash3dayData = gameArrays[5];
                String cash3eveningData = gameArrays[6];
                String cash4dayData = gameArrays[7];
                String cash4eveningData = gameArrays[8];


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection("settings");
                DocumentReference documentRef = collectionRef.document("winningNumbersArkansas");

                documentRef.update(new HashMap<String, Object>() {{

                            if (powerballData.length() > 0  ) {
                                put("powerballArray", powerballData);
                            }
                            if (megaMillionsData.length() > 0  ) {
                                put("megaMillionsArray", megaMillionsData);
                            }
                            if (lottoData.length() > 0  ) {
                                put("lottoArray", lottoData);
                            }
                            if (naturalstateData.length() > 0  ) {
                                put("naturalstateArray", naturalstateData);
                            }
                            if (luckyforlifeData.length() > 0  ) {
                                put("luckyforlifeArray", luckyforlifeData);
                            }
                            if (cash3dayData.length() > 0  ) {
                                put("cash3dayArray", cash3dayData);
                            }
                            if (cash3eveningData.length() > 0  ) {
                                put("cash3eveningArray", cash3eveningData);
                            }
                            if (cash4dayData.length() > 0  ) {
                                put("cash4dayArray", cash4dayData);
                            }
                            if (cash4eveningData.length() > 0  ) {
                                put("cash4eveningArray", cash4eveningData);
                            }


                        }})
                        .addOnSuccessListener(aVoid -> {
                            Log.d("sucess", "Arkansas updated winings successfully: ");
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
