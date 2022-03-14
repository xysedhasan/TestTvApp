package com.example.testtvapp.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testtvapp.MainActivity;
import com.example.testtvapp.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    // ArrayList<imageModel> arrayList;
    ArrayList<Game> arrayList;
    private static final String TAG = "GridAdapter";
    int sizeoflyt;

    public GridAdapter(Context context, ArrayList<Game> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public GridAdapter(Context context, ArrayList<Game> arrayList, int sizeoflyt) {
        this.context = context;
        this.arrayList = arrayList;
        this.sizeoflyt = sizeoflyt;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lotery, parent, false);
        }
        ImageView imageView,priceimg;
        TextView imgNumber;

        imgNumber = (TextView) convertView.findViewById(R.id.imagenumber);
        imageView = (ImageView) convertView.findViewById(R.id.item_image);
        priceimg = (ImageView) convertView.findViewById(R.id.tiketpriceimg);
        int height = context.getResources().getDisplayMetrics().heightPixels;
        Log.d(TAG, "getViewsizeoflyt: "+sizeoflyt);

        if (Prefrences.getOrientation(context) == 0){
            if (Prefrences.getNumberOfBoxes(context) == 50) {
                imageView.getLayoutParams().height = sizeoflyt / 5;
            } else if (Prefrences.getNumberOfBoxes(context) == 32) {
                imageView.getLayoutParams().height = sizeoflyt / 4;
            } else {
                imageView.getLayoutParams().height = sizeoflyt / 3;
            }

        }else {
            if (Prefrences.getNumberOfBoxes(context) == 50) {
                imageView.getLayoutParams().height = sizeoflyt / 10 ;
            } else if (Prefrences.getNumberOfBoxes(context) == 32) {
                imageView.getLayoutParams().height = sizeoflyt / 8;
            } else {
                imageView.getLayoutParams().height = sizeoflyt / 6;
            }

        }


        imgNumber.setText(String.valueOf(position + 1));
        if (arrayList.get(position).getImage_url() != null && !arrayList.get(position).getImage_url().contains("null")) {
            Glide.with(context).load(arrayList.get(position).getImage_url()).into(imageView);
        }else {
            if (Prefrences.getEmptyBox(context).equals("Custom")){
                Glide.with(context).load(Prefrences.getEmptyBoxesCustomImage(context)).into(imageView);
            }else if (Prefrences.getEmptyBox(context).equals("Coming Soon")){
                Glide.with(context).load(R.drawable.comingsoon).into(imageView);
            }
        }
        if (arrayList.get(position).getTicket_value() != null && !arrayList.get(position).getTicket_value().equals("")){
            if (arrayList.get(position).getTicket_value().equals("$1")){
                Glide.with(context).load(R.drawable.dollarone).into(priceimg);
            }else if (arrayList.get(position).getTicket_value().equals("$2")){
                Glide.with(context).load(R.drawable.dollartwo).into(priceimg);
            }else if (arrayList.get(position).getTicket_value().equals("$3")){
                Glide.with(context).load(R.drawable.dollartheree).into(priceimg);
            }else if (arrayList.get(position).getTicket_value().equals("$5")){
                Glide.with(context).load(R.drawable.dollarfive).into(priceimg);
            }else if (arrayList.get(position).getTicket_value().equals("$10")){
                Glide.with(context).load(R.drawable.dollarten).into(priceimg);
            }else if (arrayList.get(position).getTicket_value().equals("$20")){
                Glide.with(context).load(R.drawable.dollartwenty).into(priceimg);
            }else if (arrayList.get(position).getTicket_value().equals("$30")){
                Glide.with(context).load(R.drawable.dollarthirty).into(priceimg);
            }else if (arrayList.get(position).getTicket_value().equals("$50")){
                Glide.with(context).load(R.drawable.dollarfifty).into(priceimg);
            }else {
                priceimg.setVisibility(View.GONE);
            }
        }else {
            priceimg.setVisibility(View.GONE);
        }


        return convertView;
    }
}
