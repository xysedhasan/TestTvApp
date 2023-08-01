package com.app.beyondlottotv.Model;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes6.dex */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private boolean includeEdge;
    private int spacingPx;
    private int spanCount;

    public SpacingItemDecoration(int spanCount, int spacingPx, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacingPx = spacingPx;
        this.includeEdge = includeEdge;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int i = this.spanCount;
        int column = position % i;
        if (this.includeEdge) {
            int i2 = this.spacingPx;
            outRect.left = i2 - ((column * i2) / i);
            outRect.right = ((column + 1) * this.spacingPx) / this.spanCount;
            if (position < this.spanCount) {
                outRect.top = this.spacingPx;
            }
            outRect.bottom = this.spacingPx;
            return;
        }
        outRect.left = (this.spacingPx * column) / i;
        int i3 = this.spacingPx;
        outRect.right = i3 - (((column + 1) * i3) / this.spanCount);
        if (position >= this.spanCount) {
            outRect.top = this.spacingPx;
        }
    }
}