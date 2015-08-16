package com.caracocha.fdm;

import com.bumptech.glide.Glide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;


public class HeaderImageActivity extends Activity {

    private static final String DEBUG_TAG = "EventDetailFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_image);
        ImageViewTouch iv = (ImageViewTouch) this.findViewById(R.id.activity_header_image_image);
        iv.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Intent i = getIntent();
        Glide.with(this).load(i.getStringExtra("IMAGE"))
                //.placeholder(R.drawable.loading_spinner_md)
                .into(iv);
    }
}
