package com.app.beyondlottotv.Model;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.app.beyondlottotv.R;

public class GridAdapter extends BaseAdapter {
    Context context;
    String screenNo;
    Screen1 screen1;
    private static final String TAG = "GridAdapter";
    int sizeoflyt;

    String orientation;
    int boxes;
    String empty_box, imageurl;


    public GridAdapter(Context context, String screenNo, Screen1 screen1, int sizeoflyt, String orientation, int boxes, String empty_box, String imageurl) {
        this.context = context;
        this.screenNo = screenNo;
        this.screen1 = screen1;
        this.sizeoflyt = sizeoflyt;
        this.orientation = orientation;
        this.boxes = boxes;
        this.empty_box = empty_box;
        this.imageurl = imageurl;
    }


    @Override
    public int getCount() {
        return screen1.total_boxes;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        ImageView imageView;
    }

    private void animateView(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000); // Animation duration in milliseconds
        view.startAnimation(alphaAnimation);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lotery, parent, false);
        }
        ImageView imageView, priceimg, soldImg;
        TextView imgNumber, tickenumber;
        RelativeLayout parentlyt;

        parentlyt = (RelativeLayout) convertView.findViewById(R.id.parent);
        imgNumber = (TextView) convertView.findViewById(R.id.imagenumber);
        imageView = (ImageView) convertView.findViewById(R.id.item_image);
        tickenumber = (TextView) convertView.findViewById(R.id.ticketNumber);
        soldImg = (ImageView) convertView.findViewById(R.id.soldimg);
        priceimg = (ImageView) convertView.findViewById(R.id.tiketpriceimg);

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
                tickenumber.setText("T" + String.valueOf(tnumber + 1));
            } else {
                tickenumber.setVisibility(View.GONE);
            }

            String packsize = screen1.getBox_settings().get(position).getPack_size();
            if (packsize != null && !packsize.equals("")) {
                if (tnumber > Integer.parseInt(packsize)) {
                    soldImg.setVisibility(View.VISIBLE);
                    tickenumber.setVisibility(View.GONE);
                } else {
                    soldImg.setVisibility(View.GONE);
                }
            } else {
                soldImg.setVisibility(View.GONE);
            }
        }


        if (orientation.equals("landscape")) {
            if (boxes == 100) {
                imageView.getLayoutParams().height = sizeoflyt / 10;

                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(imgNumber, 12, 24, 20);
                        setTicketProperties(tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 12, 24, 20);
                        setTicketProperties(tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 12, 24, 20);
                        setTicketProperties(tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 12, 24, 20);
                        setTicketProperties(tickenumber, 12, 24, 20);
                    }
                }
            } else if (boxes == 80) {
                imageView.getLayoutParams().height = sizeoflyt / 8;
                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(imgNumber, 13, 22, 18);
                        setTicketProperties(tickenumber, 13, 22, 18);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 13, 26, 18);
                        setTicketProperties(tickenumber, 13, 26, 18);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 13, 26, 18);
                        setTicketProperties(tickenumber, 13, 26, 18);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 13, 26, 18);
                        setTicketProperties(tickenumber, 13, 26, 18);
                    }
                }

            } else if (boxes == 65) {
                imageView.getLayoutParams().height = sizeoflyt / 5;
                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(imgNumber, 13, 26, 20);
                        setTicketProperties(tickenumber, 13, 26, 20);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 13, 26, 20);
                        setTicketProperties(tickenumber, 13, 26, 20);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 13, 26, 20);
                        setTicketProperties(tickenumber, 13, 26, 20);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 13, 26, 20);
                        setTicketProperties(tickenumber, 13, 26, 20);
                    }
                }
            } else if (boxes == 50) {
                imageView.getLayoutParams().height = sizeoflyt / 5;
                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(imgNumber, 15, 30, 20);
                        setTicketProperties(tickenumber, 15, 30, 20);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 15, 30, 20);
                        setTicketProperties(tickenumber, 15, 30, 20);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 15, 30, 20);
                        setTicketProperties(tickenumber, 15, 30, 20);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 15, 30, 20);
                        setTicketProperties(tickenumber, 15, 30, 20);
                    }
                }
            } else if (boxes == 32) {
                imageView.getLayoutParams().height = sizeoflyt / 4;

                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(imgNumber, 17, 34, 24);
                        setTicketProperties(tickenumber, 17, 34, 24);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 17, 34, 24);
                        setTicketProperties(tickenumber, 17, 34, 24);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 17, 34, 24);
                        setTicketProperties(tickenumber, 17, 34, 24);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 17, 34, 24);
                        setTicketProperties(tickenumber, 17, 34, 24);
                    }
                }
            } else if (boxes == 18) {
                imageView.getLayoutParams().height = sizeoflyt / 3;
                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(imgNumber, 19, 38, 28);
                        setTicketProperties(tickenumber, 19, 38, 28);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 19, 38, 28);
                        setTicketProperties(tickenumber, 19, 38, 28);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 19, 38, 28);
                        setTicketProperties(tickenumber, 19, 38, 28);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 19, 38, 28);
                        setTicketProperties(tickenumber, 19, 38, 28);
                    }
                }
            }
        } else if (orientation.equals("portrait")) {
            if (boxes == 100) {
                imageView.getLayoutParams().height = sizeoflyt / 10;

                if (screenNo.equals("screen1")) {
                    if (position + 1 > 99) {
                        setBoxNumberProperties(imgNumber, 12, 24, 20);
                        setTicketProperties(tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen2")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 12, 24, 20);
                        setTicketProperties(tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen3")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 12, 24, 20);
                        setTicketProperties(tickenumber, 12, 24, 20);
                    }
                } else if (screenNo.equals("screen4")) {
                    if ((Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1) > 99) {
                        setBoxNumberProperties(imgNumber, 12, 24, 20);
                        setTicketProperties(tickenumber, 12, 24, 20);
                    }
                }
            } else if (boxes == 80) {
                imageView.getLayoutParams().height = sizeoflyt / 10;
            } else if (boxes == 65) {
                imageView.getLayoutParams().height = sizeoflyt / 13;
            } else if (boxes == 50) {
                imageView.getLayoutParams().height = sizeoflyt / 10;
            } else if (boxes == 32) {
                imageView.getLayoutParams().height = sizeoflyt / 8;
            } else if (boxes == 18) {
                imageView.getLayoutParams().height = sizeoflyt / 6;
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


        if (screenNo.equals("screen1")) {
            imgNumber.setText(String.valueOf(position + 1));
        } else if (screenNo.equals("screen2")) {
            imgNumber.setText(String.valueOf(Prefrences.getTotalBoxesScreen1(context) + position + 1));
        } else if (screenNo.equals("screen3")) {
            imgNumber.setText(String.valueOf(Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + position + 1));
        } else if (screenNo.equals("screen4")) {
            imgNumber.setText(String.valueOf(Prefrences.getTotalBoxesScreen1(context) + Prefrences.getTotalBoxesScreen2(context) + Prefrences.getTotalBoxesScreen3(context) + position + 1));
        }

        if (screenNo.equals("screen1")) {
            if (screen1.getBox_settings().get(position) != null) {
                if (screen1.getBox_settings().get(position).getGame_image() != null && !screen1.getBox_settings().get(position).getGame_image().contains("null")) {
                    if (context != null) {
                        Glide.with(context).load(screen1.getBox_settings().get(position).getGame_image()).into(imageView);
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
            } else {
                if (empty_box != null) {
                    if (empty_box.equals("Custom")) {
                        Glide.with(context).load(imageurl).into(imageView);
                    } else if (empty_box.equals("Coming Soon")) {
                        Glide.with(context).load(R.drawable.comingsoon).into(imageView);
                    }
                }

            }

        }

        if (screen1.getBox_settings().get(position) != null) {
            if (screen1.getBox_settings().get(position).getTicket_value() != null && !screen1.getBox_settings().get(position).getTicket_value().equals("")) {
                if (screen1.getBox_settings().get(position).getTicket_value().equals("$1")) {
                    Glide.with(context).load(R.drawable.dollarone).into(priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$2")) {
                    Glide.with(context).load(R.drawable.dollartwo).into(priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$3")) {
                    Glide.with(context).load(R.drawable.dollartheree).into(priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$5")) {
                    Glide.with(context).load(R.drawable.dollarfive).into(priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$10")) {
                    Glide.with(context).load(R.drawable.dollarten).into(priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$20")) {
                    Glide.with(context).load(R.drawable.dollartwenty).into(priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$30")) {
                    Glide.with(context).load(R.drawable.dollarthirty).into(priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$50")) {
                    Glide.with(context).load(R.drawable.dollarfifty).into(priceimg);
                } else if (screen1.getBox_settings().get(position).getTicket_value().equals("$100")) {
                    Glide.with(context).load(R.drawable.dollarhundred).into(priceimg);
                } else {
                    priceimg.setVisibility(View.GONE);
                }
            } else {
                priceimg.setVisibility(View.GONE);
            }
        } else {
            priceimg.setVisibility(View.GONE);
            // Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return convertView;
    }

    private void setTicketProperties(TextView tickenumber, int ticketsize, int ticketwidth, int ticketheight) {
        float tickettextSize = ticketsize;
        tickenumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, tickettextSize);
//        int ticketwidthInDp = ticketwidth;
//        int ticketheightInDp = ticketheight;
//        float ticketscale = context.getResources().getDisplayMetrics().density;
//        int ticketwidthInPixels = (int) (ticketwidthInDp * ticketscale + 0.5f);
//        int ticketheightInPixels = (int) (ticketheightInDp * ticketscale + 0.5f);
//        RelativeLayout.LayoutParams ticketlayoutParams = new RelativeLayout.LayoutParams(ticketwidthInPixels, ticketheightInPixels);
//        tickenumber.setLayoutParams(ticketlayoutParams);

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
