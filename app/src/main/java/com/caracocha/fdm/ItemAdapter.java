package com.caracocha.fdm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by xabi on 6/21/15.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private static final String DEBUG_TAG = "ItemAdapter";

    Context context;
    ArrayList<Item> alItems;

    public ItemAdapter(ArrayList alItems, Context context) {
        this.alItems = alItems;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.event_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        Item item = alItems.get(i);
        Log.d(DEBUG_TAG,"Items: " + alItems.size() + " " + i + " " + item.sTitle + " " + item.sCategory);
        itemViewHolder.tvTitle.setText(item.sTitle);
        itemViewHolder.tvTime.setText(item.sStartTime);
        itemViewHolder.tvPlace.setText(item.sPlace);
        itemViewHolder.ivEvent.setImageResource(context.getResources().getIdentifier(item.sCategory.toLowerCase(), "drawable", context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return alItems.size();
    }

    public void addOrUpdateItem(Item q) {
        int pos = alItems.indexOf(q);
        if (pos >= 0) {
            updateItem(q, pos);
        } else {
            addItem(q);
        }
    }

    private void updateItem(Item q, int pos) {
        alItems.remove(pos);
        notifyItemRemoved(pos);
        addItem(q);
    }

    private void addItem(Item q) {
        alItems.add(q);
        notifyItemInserted(alItems.size() - 1);
    }

}