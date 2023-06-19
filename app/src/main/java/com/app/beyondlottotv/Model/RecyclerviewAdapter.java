package com.app.beyondlottotv.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.beyondlottotv.R;

import java.util.ArrayList;

public class RecyclerviewAdapter extends  RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
    private ArrayList<String> tickets = new ArrayList<>();
    Context mContext;

    public RecyclerviewAdapter(ArrayList<String> tickets, Context mContext) {
        this.tickets = tickets;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lotery, parent, false);
        RecyclerviewAdapter.ViewHolder holder = new RecyclerviewAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
