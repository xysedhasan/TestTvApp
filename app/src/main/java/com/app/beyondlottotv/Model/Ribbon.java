package com.app.beyondlottotv.Model;

import android.content.Context;
import android.graphics.Color;
import androidx.core.view.ViewCompat;
import com.facebook.shimmer.Shimmer;
import com.skydoves.androidribbon.RibbonView;
import com.skydoves.androidribbon.ShimmerRibbonView;


public class Ribbon {
    public static RibbonView ribbonView(Context context, String text) {
        RibbonView rb = new RibbonView.Builder(context).setText((CharSequence) text).setTextColor(ViewCompat.MEASURED_STATE_MASK).setTextSize(12.0f).setRibbonRotation(-40).setPaddingLeft(30.0f).setPaddingRight(30.0f).setTextStyle(1).setRibbonBackgroundColor(getColorWithAlpha(-1, 0.5f)).build();
        return rb;
    }

    private static int getColorWithAlpha(int color, float ratio) {
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    public static RibbonView ribbonViewFor(Context context, String text) {
        RibbonView rb = new RibbonView.Builder(context).setText((CharSequence) text).setTextColor(ViewCompat.MEASURED_STATE_MASK).setTextSize(12.0f).setRibbonRotation(8).setRibbonRotation(-45).setPaddingLeft(15.0f).setPaddingRight(15.0f).setTextStyle(1).setRibbonBackgroundColor(0).build();
        return rb;
    }

    public static ShimmerRibbonView ribbonViewAnimate(Context context, String text) {
        return new ShimmerRibbonView.Builder(context).setRibbonView(ribbonViewFor(context, text)).setShimmer(returnShimmer(context, text)).build();
    }

    public static Shimmer returnShimmer(Context context, String text) {
        return new Shimmer.AlphaHighlightBuilder().setBaseAlpha(1.0f).setHighlightAlpha(0.5f).setRepeatDelay(10L).setDuration(10L).setDirection(2).build();
    }
}