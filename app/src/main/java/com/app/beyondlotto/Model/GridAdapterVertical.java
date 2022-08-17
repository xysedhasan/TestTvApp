package com.app.beyondlotto.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.app.beyondlotto.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class GridAdapterVertical extends BaseAdapter {
    Context context;
    // ArrayList<imageModel> arrayList;
    ArrayList<Game> arrayList;
    private static final String TAG = "GridAdapter";
    int sizeoflyt;
    ArrayList<Boolean> animateArray = new ArrayList<>();
    String orientation;
    int boxes;
    String empty_box, imageurl;


    public GridAdapterVertical(Context context, ArrayList<Game> arrayList, int sizeoflyt, ArrayList<Boolean> animateArray, String orientation, int boxes, String empty_box, String imageurl) {
        this.context = context;
        this.arrayList = arrayList;
        this.sizeoflyt = sizeoflyt;
        this.animateArray = animateArray;
        this.orientation = orientation;
        this.boxes = boxes;
        this.empty_box = empty_box;
        this.imageurl = imageurl;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lotery_vertical, parent, false);
        }
        ImageView imageView, priceimg;
        TextView imgNumber;
        RelativeLayout parentlyt;

        parentlyt = (RelativeLayout) convertView.findViewById(R.id.parent);
        imgNumber = (TextView) convertView.findViewById(R.id.imagenumber);
        imageView = (ImageView) convertView.findViewById(R.id.item_image);
        priceimg = (ImageView) convertView.findViewById(R.id.tiketpriceimg);
        int height = context.getResources().getDisplayMetrics().heightPixels;
        Log.d(TAG, "getViewsizeoflyt: " + sizeoflyt);

        if (!orientation.equals("vertical")) {
            if (boxes == 100) {
                imageView.getLayoutParams().height = sizeoflyt / 10;
            } else if (boxes == 80) {
                imageView.getLayoutParams().height = sizeoflyt / 8;
            } else if (boxes == 65) {
                imageView.getLayoutParams().height = sizeoflyt / 5;
            } else if (boxes == 50) {
                imageView.getLayoutParams().height = sizeoflyt / 5;
            } else if (boxes == 32) {
                imageView.getLayoutParams().height = sizeoflyt / 4;
            } else {
                imageView.getLayoutParams().height = sizeoflyt / 3;
            }

        } else {
            if (boxes == 100) {
                imageView.getLayoutParams().height = sizeoflyt / 10;
            } else if (boxes == 80) {
                imageView.getLayoutParams().height = sizeoflyt / 10;
            } else if (boxes == 65) {
                imageView.getLayoutParams().height = sizeoflyt / 13;
            } else if (boxes == 50) {
                imageView.getLayoutParams().height = sizeoflyt / 10;
            } else if (boxes == 32) {
                imageView.getLayoutParams().height = sizeoflyt / 8;
            } else {
                imageView.getLayoutParams().height = sizeoflyt / 6;
            }

        }


        if (position < animateArray.size()) {
            if (animateArray.get(position).booleanValue()) {
                //  parentlyt.setBackgroundResource(R.drawable.gradient_list);
                // parentlyt.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                parentlyt.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
//                AnimationDrawable animationDrawable = (AnimationDrawable) parentlyt.getBackground();
//                animationDrawable.setEnterFadeDuration(1000);
//                animationDrawable.setExitFadeDuration(1000);
//                animationDrawable.start();

                imageView.setPadding(5, 5, 5, 5);
            } else {
                parentlyt.setBackgroundResource(0);
            }
        }


        imgNumber.setText(String.valueOf(position + 1));
        if (arrayList.get(position).getImage_url() != null && !arrayList.get(position).getImage_url().contains("null")) {
            if (context != null) {

               // Glide.with(context).load(arrayList.get(position).getImage_url()).into(imageView);

                Matrix mat = new Matrix();
                 new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bitmap bMap = BitmapFactory.decodeStream((InputStream) new URL(arrayList.get(position).getImage_url()).getContent());
                            mat.postRotate(-90);
                            Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
                            imageView.setImageBitmap(bMapRotate);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        } catch (Exception ex) {
                            Log.d(TAG, "runex: " + ex);
                            ex.printStackTrace();
                        }
                    }
                }).start();


            }

        } else {
            if (empty_box != null) {
                if (empty_box.equals("Custom")) {

                    //Glide.with(context).load(imageurl).into(imageView);
                    Matrix mat = new Matrix();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Bitmap bMap = BitmapFactory.decodeStream((InputStream) new URL(imageurl).getContent());
                                mat.postRotate(-90);
                                Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
                                imageView.setImageBitmap(bMapRotate);
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception ex) {
                                Log.d(TAG, "runex: " + ex);
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                } else if (empty_box.equals("Coming Soon")) {
                    Matrix mat = new Matrix();


                    Bitmap bMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.comingsoon);
                    mat.postRotate(-90);
                    Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
                    imageView.setImageBitmap(bMapRotate);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);


                    //    Glide.with(context).load(R.drawable.comingsoon).into(imageView);

                }
            }

        }
        if (arrayList.get(position).getTicket_value() != null && !arrayList.get(position).getTicket_value().equals("")) {
            if (arrayList.get(position).getTicket_value().equals("$1")) {
                Glide.with(context).load(R.drawable.dollarone).into(priceimg);
            } else if (arrayList.get(position).getTicket_value().equals("$2")) {
                Glide.with(context).load(R.drawable.dollartwo).into(priceimg);
            } else if (arrayList.get(position).getTicket_value().equals("$3")) {
                Glide.with(context).load(R.drawable.dollartheree).into(priceimg);
            } else if (arrayList.get(position).getTicket_value().equals("$5")) {
                Glide.with(context).load(R.drawable.dollarfive).into(priceimg);
            } else if (arrayList.get(position).getTicket_value().equals("$10")) {
                Glide.with(context).load(R.drawable.dollarten).into(priceimg);
            } else if (arrayList.get(position).getTicket_value().equals("$20")) {
                Glide.with(context).load(R.drawable.dollartwenty).into(priceimg);
            } else if (arrayList.get(position).getTicket_value().equals("$30")) {
                Glide.with(context).load(R.drawable.dollarthirty).into(priceimg);
            } else if (arrayList.get(position).getTicket_value().equals("$50")) {
                Glide.with(context).load(R.drawable.dollarfifty).into(priceimg);
            } else if (arrayList.get(position).getTicket_value().equals("$100")) {
                Glide.with(context).load(R.drawable.dollarhundred).into(priceimg);
            } else {
                priceimg.setVisibility(View.GONE);
            }
        } else {
            priceimg.setVisibility(View.GONE);
        }


        return convertView;
    }


}
