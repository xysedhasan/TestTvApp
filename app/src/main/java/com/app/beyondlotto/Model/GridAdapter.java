package com.app.beyondlotto.Model;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.app.beyondlotto.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    // ArrayList<imageModel> arrayList;
    String screenNo;
    ArrayList<Game> arrayList;
    private static final String TAG = "GridAdapter";
    int sizeoflyt;
    ArrayList<Boolean> animateArray = new ArrayList<>();
    String orientation;
    int boxes;
    String empty_box, imageurl;

    public GridAdapter(Context context, String screenNo, ArrayList<Game> arrayList, int sizeoflyt, ArrayList<Boolean> animateArray, String orientation, int boxes, String empty_box, String imageurl) {
        this.context = context;
        this.screenNo = screenNo;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lotery, parent, false);
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

//         if (orientation.equals("landscape")){
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
        } else if (boxes == 18) {
            imageView.getLayoutParams().height = sizeoflyt / 3;
        }
//        }else {
//             if (boxes == 100) {
//                 imageView.getLayoutParams().height = sizeoflyt / 10;
//             } else if (boxes == 80) {
//                 imageView.getLayoutParams().height = sizeoflyt / 10;
//             } else if (boxes == 65) {
//                 imageView.getLayoutParams().height = sizeoflyt / 13;
//             } else if (boxes == 50) {
//                 imageView.getLayoutParams().height = sizeoflyt / 10;
//             } else if (boxes == 32) {
//                 imageView.getLayoutParams().height = sizeoflyt / 8;
//             } else if (boxes == 18) {
//                 imageView.getLayoutParams().height = sizeoflyt / 6;
//             }
//         }
        if (position < animateArray.size()) {
            if (animateArray.get(position).booleanValue()) {
                //code to animate background
                //  parentlyt.setBackgroundResource(R.drawable.gradient_list);
                // parentlyt.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                //        parentlyt.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
//                AnimationDrawable animationDrawable = (AnimationDrawable) parentlyt.getBackground();
//                animationDrawable.setEnterFadeDuration(1000);
//                animationDrawable.setExitFadeDuration(1000);
//                animationDrawable.start();


                ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
                anim.setDuration(2000);

                float[] hsv;
                // int runColor;
                int hue = 0;
                hsv = new float[3]; // Transition color
                hsv[1] = 1;
                hsv[2] = 1;
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        hsv[0] = 360 * animation.getAnimatedFraction();
                        int runColor = Color.HSVToColor(hsv);
                        parentlyt.setBackgroundColor(runColor);
                    }
                });

                anim.setRepeatCount(Animation.INFINITE);
                anim.start();


                imageView.setPadding(5, 5, 5, 5);
            } else {
                parentlyt.setBackgroundResource(0);
            }
        }


        if (screenNo.equals("2")) {
            imgNumber.setText(String.valueOf(Prefrences.getTotalBoxesScreen1(context) + position + 1));
        } else {
            imgNumber.setText(String.valueOf(position + 1));
        }

        if (arrayList.get(position) != null) {
            if (arrayList.get(position).getImage_url() != null && !arrayList.get(position).getImage_url().contains("null")) {
                if (context != null) {
                    Glide.with(context).load(arrayList.get(position).getImage_url()).into(imageView);
                }

            }
        } else {
            if (empty_box != null) {
                if (empty_box.equals("Custom")) {
                    Glide.with(context).load(imageurl).into(imageView);
                } else if (empty_box.equals("Coming Soon")) {
                    Glide.with(context).load(R.drawable.comingsoon).into(imageView);
                }
            }

        }
        if (arrayList.get(position) != null){
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
        }else {
            priceimg.setVisibility(View.GONE);
           // Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }



        return convertView;
    }
}
