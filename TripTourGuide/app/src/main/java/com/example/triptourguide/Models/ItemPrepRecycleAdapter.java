package com.example.triptourguide.Models;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triptourguide.DBopenHelper;
import com.example.triptourguide.MainActivity;
import com.example.triptourguide.R;


import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ItemPrepRecycleAdapter extends RecyclerView.Adapter {


    private TreeSet<String> prePrepItems = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    private TreeSet<String> postPrepItems = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    private Context _context;
    private DBopenHelper dbHelper;
    private int _tripId;

    public ItemPrepRecycleAdapter(Set<String> itemCollection, Context context) {
        _context = context;
        dbHelper = new DBopenHelper(_context);
        DBopenHelper dbHelper = new DBopenHelper(_context);
        _tripId = dbHelper.getTripId(MainActivity.GetCurrentTripName());
        if (!dbHelper.checkIfItemExist(MainActivity.GetCurrentTripName())) {
            dbHelper.initializeItemPrep(MainActivity.GetCurrentTripName(), itemCollection);
            prePrepItems.addAll(itemCollection);
        } else {
            Map<String, Boolean> itemPrepMap = dbHelper.getItemStateFromDb(MainActivity.GetCurrentTripName());
            for (String item : itemCollection) {
                if (itemPrepMap.containsKey(item) && itemPrepMap.get(item)) {
                    postPrepItems.add(item);
                } else {
                    prePrepItems.add(item);
                }
            }
        }

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
            View view = inflater.inflate(R.layout.item_provider_row, parent, false);


        return new ItemPrepRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextView)holder.itemView.findViewById(R.id.item_provider_item_name)).setText(getItemOnIndex(position));
        if (position >= prePrepItems.size()) {
            holder.itemView.setBackground(_context.getDrawable(R.drawable.gradient_bg_green));
            ((TextView) holder.itemView.findViewById(R.id.if_prep_text)).setText("준비됨");
        } else {
            holder.itemView.setBackground(_context.getDrawable(R.drawable.gradient_bg_red));
            ((TextView) holder.itemView.findViewById(R.id.if_prep_text)).setText("");
        }
    }

    @Override
    public int getItemCount() {
        return prePrepItems.size() + postPrepItems.size();
    }

    public String getItemOnIndex(int ind) {
        if(ind >= prePrepItems.size())
            return postPrepItems.toArray(new String[postPrepItems.size()])[ind - prePrepItems.size()];
        return prePrepItems.toArray(new String[prePrepItems.size()])[ind];
    }

    public void removeItemOnIndex(int ind) {
        if(ind >= prePrepItems.size()) {
            String value = postPrepItems.toArray(new String[postPrepItems.size()])[ind - prePrepItems.size()];
            postPrepItems.remove(value);
            prePrepItems.add(value);
            dbHelper.updateItem(_tripId, value, false);

        } else {
            String value = prePrepItems.toArray(new String[prePrepItems.size()])[ind];
            prePrepItems.remove(value);
            postPrepItems.add(value);
            dbHelper.updateItem(_tripId, value, true);
        }
        notifyDataSetChanged();
    }

}
