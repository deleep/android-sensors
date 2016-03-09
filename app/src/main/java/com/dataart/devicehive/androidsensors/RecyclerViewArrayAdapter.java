package com.dataart.devicehive.androidsensors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewArrayAdapter extends RecyclerView.Adapter<RecyclerViewArrayAdapter.RecyclerViewHolder> {


    ArrayList<String> list;

    public RecyclerViewArrayAdapter(ArrayList<String> list, Context context) {
        this.list = list;
    }

    @Override
    public RecyclerViewArrayAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_row, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewArrayAdapter.RecyclerViewHolder holder, int position) {
//        holder.image.setImageResource(R.drawable.planetimage);
        holder.text.setText(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        //        protected ImageView image;
        protected TextView text;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
//            image = (ImageView) itemView.findViewById(R.id.image_id);
            text = (TextView) itemView.findViewById(R.id.text_id);
        }
    }
}