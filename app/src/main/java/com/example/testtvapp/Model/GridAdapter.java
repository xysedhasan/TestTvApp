package com.example.testtvapp.Model;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.testtvapp.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    // ArrayList<imageModel> arrayList;
    ArrayList<Game> arrayList;
    private static final String TAG = "GridAdapter";
    int sizeoflyt;
    ArrayList<Boolean> animateArray = new ArrayList<>();
    String orientation;
    int boxes;

    public GridAdapter(Context context, ArrayList<Game> arrayList, int sizeoflyt, ArrayList<Boolean> animateArray, String orientation, int boxes) {
        this.context = context;
        this.arrayList = arrayList;
        this.sizeoflyt = sizeoflyt;
        this.animateArray = animateArray;
        this.orientation = orientation;
        this.boxes = boxes;
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
        RelativeLayout parentlyt;

        parentlyt = (RelativeLayout) convertView.findViewById(R.id.parent);
        imgNumber = (TextView) convertView.findViewById(R.id.imagenumber);
        imageView = (ImageView) convertView.findViewById(R.id.item_image);
        priceimg = (ImageView) convertView.findViewById(R.id.tiketpriceimg);
        int height = context.getResources().getDisplayMetrics().heightPixels;
        Log.d(TAG, "getViewsizeoflyt: "+sizeoflyt);

        if (orientation.equals("landscape")){
            if (Prefrences.getNumberOfBoxes(context) == 100) {
                imageView.getLayoutParams().height = sizeoflyt / 10;
            }else if (Prefrences.getNumberOfBoxes(context) == 80) {
                imageView.getLayoutParams().height = sizeoflyt / 8;
            }else if (Prefrences.getNumberOfBoxes(context) == 65) {
                imageView.getLayoutParams().height = sizeoflyt / 5;
            }else if (Prefrences.getNumberOfBoxes(context) == 50) {
                imageView.getLayoutParams().height = sizeoflyt / 5;
            } else if (Prefrences.getNumberOfBoxes(context) == 32) {
                imageView.getLayoutParams().height = sizeoflyt / 4;
            } else {
                imageView.getLayoutParams().height = sizeoflyt / 3;
            }

        }else {
            if (Prefrences.getNumberOfBoxes(context) == 100) {
                imageView.getLayoutParams().height = sizeoflyt / 10 ;
            }else if (Prefrences.getNumberOfBoxes(context) == 80) {
                imageView.getLayoutParams().height = sizeoflyt / 10 ;
            }else if (Prefrences.getNumberOfBoxes(context) == 65) {
                imageView.getLayoutParams().height = sizeoflyt / 13 ;
            }else if (Prefrences.getNumberOfBoxes(context) == 50) {
                imageView.getLayoutParams().height = sizeoflyt / 10 ;
            } else if (Prefrences.getNumberOfBoxes(context) == 32) {
                imageView.getLayoutParams().height = sizeoflyt / 8;
            } else {
                imageView.getLayoutParams().height = sizeoflyt / 6;
            }

        }



        if (position < animateArray.size()){
            if (animateArray.get(position).booleanValue()){
                //  parentlyt.setBackgroundResource(R.drawable.gradient_list);
                // parentlyt.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                parentlyt.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
//                AnimationDrawable animationDrawable = (AnimationDrawable) parentlyt.getBackground();
//                animationDrawable.setEnterFadeDuration(1000);
//                animationDrawable.setExitFadeDuration(1000);
//                animationDrawable.start();

                imageView.setPadding(5,5,5,5);
            }else {
                parentlyt.setBackgroundResource(0);
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
