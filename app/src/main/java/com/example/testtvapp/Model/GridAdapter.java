package com.example.testtvapp.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.testtvapp.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
   // ArrayList<imageModel> arrayList;
    ArrayList<Game> arrayList;
    private static final String TAG = "GridAdapter";

    public GridAdapter(Context context, ArrayList<Game> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lotery, parent, false);
        }
        ImageView imageView;

        imageView = (ImageView) convertView.findViewById(R.id.item_image);
        int height= context.getResources().getDisplayMetrics().heightPixels;
        imageView.getLayoutParams().height = height/5;


        if (arrayList.get(position).getImage_url() != null && !arrayList.get(position).getImage_url().contains("null")){
            Glide.with(context).load(arrayList.get(position).getImage_url()).into(imageView);
        }

      //  imageView.setImageResource(arrayList.get(position).getImage_url());
        return convertView;
    }
}
