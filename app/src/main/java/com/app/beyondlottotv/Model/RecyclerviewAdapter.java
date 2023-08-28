package com.app.beyondlottotv.Model;

import android.animation.ValueAnimator;
import android.app.MediaRouteButton;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.beyondlottotv.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerviewAdapter extends  RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
    Context context;
    Boolean subscribeInventory;
    String screenNo;
    Screen1 screen1;
    private static final String TAG = "GridAdapter";
    int sizeoflyt;

    String orientation;
    int boxes;
    String empty_box, imageurl;

    public RecyclerviewAdapter(Context context, Boolean subscribeInventory, String screenNo, Screen1 screen1, int sizeoflyt, String orientation, int boxes, String empty_box, String imageurl) {
        this.context = context;
        this.subscribeInventory = subscribeInventory;
        this.screenNo = screenNo;
        this.screen1 = screen1;
        this.sizeoflyt = sizeoflyt;
        this.orientation = orientation;
        this.boxes = boxes;
        this.empty_box = empty_box;
        this.imageurl = imageurl;
    }
    @NonNull
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_lotery, parent, false);
        RecyclerviewAdapter.ViewHolder holder = new RecyclerviewAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder holder, int position) {

        if (subscribeInventory) {
            holder.tickenumber.setVisibility(View.VISIBLE);
        }

        if (orientation.equals("landscape")) {
            if (boxes == 100) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 10;

                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(holder.imgNumber, 12, 24, 20);
                        setTicketProperties(holder.tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 12, 24, 20);
                        setTicketProperties(holder.tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 12, 24, 20);
                        setTicketProperties(holder.tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 12, 24, 20);
                        setTicketProperties(holder.tickenumber, 12, 24, 20);
                    }
                }
            } else if (boxes == 80) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 8;
                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(holder.imgNumber, 13, 22, 18);
                        setTicketProperties(holder.tickenumber, 13, 22, 18);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 13, 26, 18);
                        setTicketProperties(holder.tickenumber, 13, 26, 18);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 13, 26, 18);
                        setTicketProperties(holder.tickenumber, 13, 26, 18);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 13, 26, 18);
                        setTicketProperties(holder.tickenumber, 13, 26, 18);
                    }
                }

            } else if (boxes == 65) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 5;
                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(holder.imgNumber, 13, 26, 20);
                        setTicketProperties(holder.tickenumber, 13, 26, 20);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 13, 26, 20);
                        setTicketProperties(holder.tickenumber, 13, 26, 20);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 13, 26, 20);
                        setTicketProperties(holder.tickenumber, 13, 26, 20);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 13, 26, 20);
                        setTicketProperties(holder.tickenumber, 13, 26, 20);
                    }
                }
            } else if (boxes == 50) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 5;
                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(holder.imgNumber, 15, 30, 20);
                        setTicketProperties(holder.tickenumber, 15, 30, 20);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 15, 30, 20);
                        setTicketProperties(holder.tickenumber, 15, 30, 20);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 15, 30, 20);
                        setTicketProperties(holder.tickenumber, 15, 30, 20);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 15, 30, 20);
                        setTicketProperties(holder.tickenumber, 15, 30, 20);
                    }
                }
            } else if (boxes == 32) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 4;

                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(holder.imgNumber, 17, 34, 24);
                        setTicketProperties(holder.tickenumber, 17, 34, 24);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 17, 34, 24);
                        setTicketProperties(holder.tickenumber, 17, 34, 24);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 17, 34, 24);
                        setTicketProperties(holder.tickenumber, 17, 34, 24);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 17, 34, 24);
                        setTicketProperties(holder.tickenumber, 17, 34, 24);
                    }
                }
            } else if (boxes == 18) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 3;
                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(holder.imgNumber, 19, 38, 28);
                        setTicketProperties(holder.tickenumber, 19, 38, 28);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 19, 38, 28);
                        setTicketProperties(holder.tickenumber, 19, 38, 28);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 19, 38, 28);
                        setTicketProperties(holder.tickenumber, 19, 38, 28);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 19, 38, 28);
                        setTicketProperties(holder.tickenumber, 19, 38, 28);
                    }
                }
            }
        } else if (orientation.equals("portrait")) {
            if (boxes == 100) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 10;

                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(holder.imgNumber, 12, 24, 20);
                        setTicketProperties(holder.tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 12, 24, 20);
                        setTicketProperties(holder.tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 12, 24, 20);
                        setTicketProperties(holder.tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(holder.imgNumber, 12, 24, 20);
                        setTicketProperties(holder.tickenumber, 12, 24, 20);
                    }
                }
            } else if (boxes == 80) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 10;
            } else if (boxes == 65) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 13;
            } else if (boxes == 50) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 10;
            } else if (boxes == 32) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 8;
            } else if (boxes == 18) {
                holder.imageView.getLayoutParams().height = sizeoflyt / 6;
            }
        }


        if (position < screen1.total_boxes) {

            if (screen1.getBox_settings().get(position).animation) {
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
                        holder.parentlyt.setBackgroundColor(runColor);
                    }
                });

                anim.setRepeatCount(Animation.INFINITE);
                anim.start();


                holder.imageView.setPadding(5, 5, 5, 5);
            } else {
                holder.parentlyt.setBackgroundResource(0);
            }
        }

        if (screenNo.equals("screen1")) {
            holder.imgNumber.setText(String.valueOf(position + 1));
        } else if (screenNo.equals("screen2")) {
            holder.imgNumber.setText(String.valueOf(Prefrences.getTotalBoxesScreen1(context) + position + 1));
        } else if (screenNo.equals("screen3")) {
            holder.imgNumber.setText(String.valueOf(Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1));
        } else if (screenNo.equals("screen4")) {
            holder.imgNumber.setText(String.valueOf(Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1));
        }


        if (screen1.getBox_settings().get(position) != null) {
            if (screen1.getBox_settings().get(position).getGame_image() != null && !screen1.getBox_settings().get(position).getGame_image().contains("null") && !screen1.getBox_settings().get(position).getGame_image().equals("")) {
                if (context != null) {
                    Picasso.get().load(screen1.getBox_settings().get(position).getGame_image()).into(holder.imageView);
//                    Glide.with(context).load(screen1.getBox_settings().get(position).getGame_image()).into(imageView);
                }
            } else {
                if (empty_box != null) {
                    if (empty_box.equals("Custom")) {
                        Glide.with(context).load(imageurl).into(holder.imageView);
                    } else if (empty_box.equals("Coming Soon")) {
                        Glide.with(context).load(R.drawable.comingsoon).into(holder.imageView);
                    }
                }
            }
        } else {
            if (empty_box != null) {
                if (empty_box.equals("Custom")) {
                    Glide.with(context).load(imageurl).into(holder.imageView);
                } else if (empty_box.equals("Coming Soon")) {
                    Glide.with(context).load(R.drawable.comingsoon).into(holder.imageView);
                }
            }
        }



        int tnumber = 0;
        if (screen1.getBox_settings().get(position) != null) {
            if (screen1.getBox_settings().get(position).getTicket_no() != null) {
                String strnumber = screen1.getBox_settings().get(position).getTicket_no();
                String tiNumber = strnumber;
                if (strnumber.length() > 2) {
                    tiNumber = strnumber.substring(strnumber.length() - 3);
                }
                if (tiNumber.equals("")) {
                    tiNumber = "0";
                }

                tnumber = Integer.parseInt(tiNumber);
                holder.tickenumber.setText("T" + String.valueOf(tnumber + 1));

            } else {
                holder.tickenumber.setVisibility(View.GONE);
            }


            String packsize = screen1.getBox_settings().get(position).getPack_size();
            if (packsize != null && !packsize.equals("")) {
                if (tnumber + 1 > Integer.parseInt(packsize)) {
                    if (subscribeInventory) {
                        holder.soldImg.setVisibility(View.VISIBLE);
                    }
                    holder.tickenumber.setVisibility(View.GONE);
                } else {
                    holder.soldImg.setVisibility(View.GONE);
                }
            } else {
                holder.soldImg.setVisibility(View.GONE);
            }
        }


        if (screen1.getBox_settings().get(position) != null) {
            if (screen1.getBox_settings().get(position).getTicket_value() != null && !screen1.getBox_settings().get(position).getTicket_value().equals("")) {
                if (screen1.getBox_settings().get(position).getTicket_value().equals("$1")) {
                    Glide.with(context).load(R.drawable.dollarone).into(holder.priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$2")) {
                    Glide.with(context).load(R.drawable.dollartwo).into(holder.priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$3")) {
                    Glide.with(context).load(R.drawable.dollartheree).into(holder.priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$5")) {
                    Glide.with(context).load(R.drawable.dollarfive).into(holder.priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$10")) {
                    Glide.with(context).load(R.drawable.dollarten).into(holder.priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$20")) {
                    Glide.with(context).load(R.drawable.dollartwenty).into(holder.priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$30")) {
                    Glide.with(context).load(R.drawable.dollarthirty).into(holder.priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$50")) {
                    Glide.with(context).load(R.drawable.dollarfifty).into(holder.priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$100")) {
                    Glide.with(context).load(R.drawable.dollarhundred).into(holder.priceimg);
                } else {
                    holder.priceimg.setVisibility(View.GONE);
                }
            } else {
                holder.priceimg.setVisibility(View.GONE);
            }
        } else {
            holder.priceimg.setVisibility(View.GONE);
            // Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return screen1.getTotal_boxes();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, priceimg, soldImg;
        TextView imgNumber, tickenumber;
        ConstraintLayout parentlyt;

        public ViewHolder(@NonNull View convertView) {
            super(convertView);
            parentlyt = (ConstraintLayout) convertView.findViewById(R.id.parent);
            imgNumber = (TextView) convertView.findViewById(R.id.imagenumber);
            imageView = (ImageView) convertView.findViewById(R.id.item_image);
            tickenumber = (TextView) convertView.findViewById(R.id.ticketNumber);
            soldImg = (ImageView) convertView.findViewById(R.id.soldimg);
            priceimg = (ImageView) convertView.findViewById(R.id.tiketpriceimg);

        }
    }


    private void setTicketProperties(TextView tickenumber, int ticketsize, int ticketwidth, int ticketheight) {
        float tickettextSize = ticketsize;
        tickenumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, tickettextSize);
    }

    private void setBoxNumberProperties(TextView imgNumber, int textSize, int widthInDp, int heightInDp) {
        imgNumber.setBackgroundResource(R.drawable.rectangle);
        imgNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        float scale = context.getResources().getDisplayMetrics().density;
        int widthInPixels = (int) (widthInDp * scale + 0.5f);
        int heightInPixels = (int) (heightInDp * scale + 0.5f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthInPixels, heightInPixels);
        imgNumber.setLayoutParams(layoutParams);
    }
}
