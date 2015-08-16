package com.caracocha.fdm;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by xabi on 6/21/15.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String DEBUG_TAG = "ItemViewHolder";
    public onItemClickListener ifItemClick;
    // Contains the position inside alEvents
    public int iPos;

    // EVENT item
    protected ImageView ivEvent;
    protected TextView tvTitle;
    protected TextView tvTime;
    protected TextView tvPlace;

    // DAY item
    protected TextView tvDay;

    // INFO item
    protected TextView tvInfo;

    // AD item
    protected TextView tvAd;

    public ItemViewHolder(View itemView, int iViewType, onItemClickListener ifItemClick) {
        super(itemView);
        this.ifItemClick = ifItemClick;
        switch (iViewType) {
            case Item.iEVENT:
                itemView.setOnClickListener(this);
                ivEvent = (ImageView) itemView.findViewById(R.id.item_event_image);
                tvTitle = (TextView) itemView.findViewById(R.id.item_event_title);
                tvTime = (TextView) itemView.findViewById(R.id.item_event_time);
                tvPlace = (TextView) itemView.findViewById(R.id.item_event_place);
                break;
            case Item.iDAY:
                tvDay = (TextView) itemView.findViewById(R.id.item_day_day);
                break;
            case Item.iMONTH:
                tvDay = (TextView) itemView.findViewById(R.id.item_month_month);
                break;
            case Item.iINFO:
                itemView.setOnClickListener(this);
                tvInfo = (TextView) itemView.findViewById(R.id.item_info_text);
                break;
            case Item.iAD:
                itemView.setOnClickListener(this);
                tvAd = (TextView) itemView.findViewById(R.id.item_ad_text);
                break;
            default:
                Log.d(DEBUG_TAG, "Wrong iViewType (" + iViewType + ")");
        }
    }

    @Override
    public void onClick(View view) {
        ifItemClick.onItemClick(iPos);
    }

    public interface onItemClickListener {
        void onItemClick(int iPos);
    }
}