package com.app.beyondlottotv.Model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.beyondlottotv.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.prefs.Preferences;

public class PortraitRecyclerviewAdapter extends RecyclerView.Adapter<PortraitRecyclerviewAdapter.ViewHolder> {
    Context context;

    String screenNo;
    Screen1 screen1;

    private static final String TAG = "GridAdapter";
    int sizeoflyt,screenwidth;

    String orientation;
    int boxes;
    String empty_box, imageurl;

    public PortraitRecyclerviewAdapter(Context context, String screenNo, Screen1 screen1, int sizeoflyt, String orientation, int boxes, String empty_box, String imageurl) {
        this.context = context;
        this.screenNo = screenNo;
        this.screen1 = screen1;
        this.sizeoflyt = sizeoflyt;
        this.orientation = orientation;
        this.boxes = boxes;
        this.empty_box = empty_box;
        this.imageurl = imageurl;
    }

    public PortraitRecyclerviewAdapter(Context context, String screenNo, Screen1 screen1, int sizeoflyt, int screenwidth, String orientation, int boxes, String empty_box, String imageurl) {
        this.context = context;
        this.screenNo = screenNo;
        this.screen1 = screen1;
        this.sizeoflyt = sizeoflyt;
        this.screenwidth = screenwidth;
        this.orientation = orientation;
        this.boxes = boxes;
        this.empty_box = empty_box;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portrait, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.boxNumber.setText(String.valueOf(position + 1));
        if (screen1.getBox_settings().size() > 0) {
            if (screen1.getBox_settings().get(position) != null) {
                if (screen1.getBox_settings().get(position).getGame_image() != null && !screen1.getBox_settings().get(position).getGame_image().contains("null")) {
                    if (context != null) {
                        Log.d(TAG, "onBindViewHolder432: " + screen1.getBox_settings().get(position).getGame_image());
                        Glide.with(context).load(screen1.getBox_settings().get(position).getGame_image()).into(holder.lotteryImage);
//                        Picasso.get().load(screen1.getBox_settings().get(position).getGame_image()).fit().into(holder.lotteryImage);
                    }
                } else {
                    Picasso.get().load(R.drawable.emptyimage).into(holder.lotteryImage);
                }
                int tnumber = 0;
                if (screen1.getBox_settings().get(position).getTicket_no() != null) {
                    String strnumber = screen1.getBox_settings().get(position).getTicket_no();

                    Log.d(TAG, "onBindViewHtesdfolder: "+screen1.getBox_settings().get(position));
                    String tiNumber = strnumber;
                    if (strnumber.length() > 2) {
                        tiNumber = strnumber.substring(strnumber.length() - 3);
                    }
                    if (tiNumber.equals("")) {
                        tiNumber = "0";
                    }
                    tnumber = Integer.parseInt(tiNumber);
                    holder.tvticketnumber.setText("T" + String.valueOf(tnumber + 1));
                    holder.tvticketnumber.setVisibility(View.VISIBLE);
                } else {
                    holder.tvticketnumber.setText("T" + "0");
                    holder.tvticketnumber.setVisibility(View.GONE);
                }

                String packsize = screen1.getBox_settings().get(position).getPack_size();
                if (packsize != null && !packsize.equals("")) {
                    if (tnumber > Integer.parseInt(packsize)) {
                        holder.soldImg.setVisibility(View.VISIBLE);
                        holder.tvticketnumber.setVisibility(View.GONE);
                    } else {
                        holder.soldImg.setVisibility(View.GONE);
                    }
                } else {
                    holder.soldImg.setVisibility(View.GONE);
                }
            } else {
                if (empty_box != null) {
                    if (empty_box.equals("Custom")) {
                        Glide.with(context).load(imageurl).into(holder.lotteryImage);
                    } else if (empty_box.equals("Coming Soon")) {
                        Glide.with(context).load(R.drawable.comingsoon).into(holder.lotteryImage);
                    }
                }
            }
        }

        if (boxes == 100) {
//            holder.lotteryImage.getLayoutParams().height = sizeoflyt / 10;
            ViewGroup.LayoutParams lp = holder.lotteryImage.getLayoutParams();
            lp.height =   ((sizeoflyt ) / 10) - 28 ;
            lp.width =   ((screenwidth) / 10) ;
            holder.parentLayout.setLayoutParams(lp);
        } else if (boxes == 80) {
            ViewGroup.LayoutParams lp = holder.lotteryImage.getLayoutParams();
            lp.height =   ((sizeoflyt ) / 8) - 28 ;
            lp.width =   ((screenwidth) / 10) ;
            holder.parentLayout.setLayoutParams(lp);
//            holder.lotteryImage.getLayoutParams().height = sizeoflyt / 10;
        } else if (boxes == 65) {
//            holder.lotteryImage.getLayoutParams().height = sizeoflyt / 13;
            ViewGroup.LayoutParams lp = holder.lotteryImage.getLayoutParams();
            lp.height =   ((sizeoflyt ) / 5)  ;
            lp.width =   ((screenwidth) / 13) ;
            holder.parentLayout.setLayoutParams(lp);

        } else if (boxes == 50) {
            ViewGroup.LayoutParams lp = holder.lotteryImage.getLayoutParams();
            lp.height =   ((sizeoflyt ) / 5) - 28 ;
            lp.width =   ((screenwidth) / 10) ;
            holder.parentLayout.setLayoutParams(lp);
        } else if (boxes == 32) {
            ViewGroup.LayoutParams lp = holder.lotteryImage.getLayoutParams();
            lp.height =   ((sizeoflyt ) / 4) - 28 ;
            lp.width =   ((screenwidth) / 8) ;
            holder.parentLayout.setLayoutParams(lp);
            holder.lotteryImage.getLayoutParams().height = sizeoflyt / 8;
        } else if (boxes == 18) {
            ViewGroup.LayoutParams lp = holder.lotteryImage.getLayoutParams();
            lp.height =   ((sizeoflyt ) / 3) - 28 ;
            lp.width =   ((screenwidth) / 6) ;
            holder.parentLayout.setLayoutParams(lp);
//            holder.lotteryImage.getLayoutParams().height = sizeoflyt / 6;
        }
    }

    @Override
    public int getItemCount() {
        return screen1.getTotal_boxes();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView barcode;
        ImageView soldImg;
        RelativeLayout parentLayout;
        TextView boxNumber, tvticketnumber;
        ImageView lotteryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            boxNumber = itemView.findViewById(R.id.imagenumber);
            lotteryImage = itemView.findViewById(R.id.item_image);
            tvticketnumber = itemView.findViewById(R.id.ticketNumber);
            soldImg = itemView.findViewById(R.id.soldimg);
        }
    }
}



