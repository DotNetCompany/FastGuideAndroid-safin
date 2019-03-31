package com.fast.guide.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fast.guide.R;
import java.util.List;
import com.fast.guide.Module.RecyclerViewItem;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    private List<RecyclerViewItem> Data;
    private Context context;
    private SearchRecyclerViewAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public SearchRecyclerViewAdapter(Context context, List<RecyclerViewItem> data) {
        this.context = context;
        this.Data = data;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.search_recyclerview_row, null);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = this.Data.get(position).getName();
        Glide.with(context).load(this.Data.get(position).getImage()).into( holder.imageview);
        holder.nameTextView.setText(name);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return this.Data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        ImageView imageview;
        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.searchinfotext);
            imageview=itemView.findViewById(R.id.searchimageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public RecyclerViewItem getItem(int id) {
        return Data.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(SearchRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}
