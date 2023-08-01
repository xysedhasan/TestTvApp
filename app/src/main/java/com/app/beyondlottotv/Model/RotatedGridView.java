package com.app.beyondlottotv.Model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class RotatedGridView extends GridView {

    public RotatedGridView(Context context) {
        super(context);
        init();
    }

    public RotatedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotatedGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setRotation(90); // Rotate the entire GridView by 90 degrees

    }
}

