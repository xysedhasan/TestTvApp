package com.app.beyondlottotv.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.beyondlottotv.R;
import com.skydoves.androidribbon.RibbonLayout;
import com.skydoves.androidribbon.RibbonView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class AdapterGridCaller extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context ctx;
    private float hight;
    public int lastposition;
    private OnItemClickListener mOnItemClickListener;
    private OnItemClickListener mlListener;
    private OnLoadMoreListener onLoadMoreListener;
    public RelativeLayout relativeLayout;
    public int totlaItem;
    private float wigth;


    public interface OnItemClickListener {
//        void onItemClick(View view, People people, int i);
    }

    /* loaded from: classes6.dex */
    public interface OnLoadMoreListener {
        void onLoadMore(int i);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView brief;
        public RelativeLayout constraintLayout;
        public ImageView image;
        public ImageView imageNew;
        public ImageView imageNew1;
        public View lyt_parent;
        public TextView name;
        public RibbonLayout ribbonLayout;

        public OriginalViewHolder(View v, OnItemClickListener listener) {
            super(v);
            this.image = (ImageView) v.findViewById(R.id.image);
            this.brief = (TextView) v.findViewById(R.id.brief);
            this.lyt_parent = v.findViewById(R.id.lyt_parent);
            this.ribbonLayout = (RibbonLayout) v.findViewById(R.id.ribbonLayout01);
            this.imageNew = (ImageView) v.findViewById(R.id.imgNew);
        }

        public void clearAnimation() {
            this.lyt_parent.clearAnimation();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_caller, parent, false);
        RecyclerView.ViewHolder vh = new OriginalViewHolder(v, this.mlListener);
        return vh;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        People obj = this.items.get(position);
//        if (holder instanceof OriginalViewHolder) {
//            OriginalViewHolder view = (OriginalViewHolder) holder;
//            view.brief.setText(obj.email);
//            Context context = this.ctx;
//            RibbonView rb = Ribbon.ribbonView(context, "$ " + Integer.parseInt(obj.name));
//            Ribbon.ribbonViewAnimate(this.ctx, "NEW");
//            if (!obj.IsNoRecode) {
//                Picasso.with(this.ctx).load(obj.imgUrl).fit().into(view.image);
//                view.ribbonLayout.setRibbonHeader(rb);
//                view.ribbonLayout.setRibbonBottomAlign(3);
//            } else if (obj.IsExtraBox) {
//                view.image.setImageResource(R.drawable.blackrectangle);
//                view.brief.setText("");
//                view.brief.setBackgroundResource(17170445);
//            } else {
//                view.image.setImageResource(R.drawable.comingsoon);
//            }
//            ViewGroup.LayoutParams lp = view.lyt_parent.getLayoutParams();
//            lp.height = (int) this.hight;
//            lp.width = (int) this.wigth;
//            view.lyt_parent.setLayoutParams(lp);
//            if (obj.Animate) {
//                view.image.setPadding(5, 5, 5, 5);
//                Animation a = AnimationUtils.loadAnimation(this.ctx, R.anim.item_animation);
//                a.setStartOffset(5L);
//                holder.itemView.setAnimation(a);
//                a.start();
//                view.imageNew.setVisibility(View.VISIBLE);
//                return;
//            }
//            view.image.setBackgroundColor(context.getResources().getColor(R.color.black));
//            view.imageNew.setVisibility(View.VISIBLE);
//        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
//        return this.items.size();
        return 6;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        ((OriginalViewHolder) holder).clearAnimation();
    }
}