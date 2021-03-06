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

    private ArrayList<Item> alItems;
    private Context context;
    private ItemViewHolder.onItemClickListener ifItemClick;

    public ItemAdapter(ArrayList alItems, Context context, ItemViewHolder.onItemClickListener ifItemClick) {
        this.alItems = alItems;
        this.context = context;
        this.ifItemClick = ifItemClick;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int iViewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = null;
        switch (iViewType) {
            case Item.iEVENT:
                view = inflater.inflate(R.layout.item_event, viewGroup, false);
                break;
            case Item.iDAY:
                view = inflater.inflate(R.layout.item_day, viewGroup, false);
                break;
            case Item.iMONTH:
                view = inflater.inflate(R.layout.item_month, viewGroup, false);
                break;
            case Item.iINFO:
                view = inflater.inflate(R.layout.item_info, viewGroup, false);
                break;
            case Item.iAD:
                view = inflater.inflate(R.layout.item_ad, viewGroup, false);
                break;
            default:
                Log.e(DEBUG_TAG, "The received view type was wrong: " + iViewType);
                break;
        }
        return new ItemViewHolder(view, iViewType, ifItemClick);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        Item item = alItems.get(i);
        itemViewHolder.iPos = i;
        switch (getItemViewType(i)) {
            case Item.iEVENT:
                itemViewHolder.tvTitle.setText(item.sTitle);
                itemViewHolder.tvTime.setText(item.sStartTime);
                itemViewHolder.tvPlace.setText(item.sPlace);
                int imgCategory = context.getResources().getIdentifier(item.sCategory.toLowerCase(),
                        "drawable", context.getPackageName());
                if (imgCategory != 0)
                    itemViewHolder.ivEvent.setImageResource(imgCategory);
                break;
            case Item.iDAY:
                itemViewHolder.tvDay.setText(item.sTitle);
                break;
            case Item.iMONTH:
                itemViewHolder.tvDay.setText(item.sTitle);
                break;
            case Item.iINFO:
                itemViewHolder.tvInfo.setText(item.sTitle);
                break;
            case Item.iAD:
                itemViewHolder.tvAd.setText(item.sTitle);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        String sType = alItems.get(position).sType;
        if (sType.equals(Item.EVENT)) {
            return Item.iEVENT;
        } else if (sType.equals(Item.DAY)) {
            return Item.iDAY;
        } else if (sType.equals(Item.MONTH)) {
            return Item.iMONTH;
        } else if (sType.equals(Item.INFO)) {
            return Item.iINFO;
        } else if (sType.equals(Item.AD)) {
            return Item.iAD;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return alItems.size();
    }
}