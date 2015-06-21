package com.caracocha.fdm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by xabi on 6/21/15.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    protected ImageView ivEvent;
    protected TextView tvTitle;
    protected TextView tvTime;
    protected TextView tvPlace;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ivEvent = (ImageView) itemView.findViewById(R.id.item_Image);
        tvTitle = (TextView) itemView.findViewById(R.id.item_Title);
        tvTime = (TextView) itemView.findViewById(R.id.item_Time);
        tvPlace = (TextView) itemView.findViewById(R.id.item_Place);
    }
}