package com.example.triptourguide.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triptourguide.R;

import java.util.Collection;

public class ItemPrepRecycleAdapter extends RecyclerView.Adapter {

    Collection<String> ItemCollection;

    public ItemPrepRecycleAdapter(Collection<String> itemCollection) {
        ItemCollection = itemCollection;
    }

    public class ItemPrepRecycleViewHolder extends RecyclerView.ViewHolder {
        public ItemPrepRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_provider_row, null);

        TextView supplyname = view.findViewById(R.id.item_provider_item_name);
        return new ItemPrepRecycleViewHolder(supplyname);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextView) holder.itemView).setText((String) ItemCollection.toArray()[position]);
    }

    @Override
    public int getItemCount() {
        return ItemCollection.size();
    }
}
