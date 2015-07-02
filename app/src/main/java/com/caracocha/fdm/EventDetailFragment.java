package com.caracocha.fdm;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment representing a single Event detail screen.
 * This fragment is either contained in a {@link EventListActivity}
 */
public class EventDetailFragment extends Fragment implements View.OnClickListener {
    private static final String DEBUG_TAG = "EventDetailFragment";

    private Item event;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        event = bundle.getParcelable("event");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_detail, container, false);
        CardView cvShare = (CardView) rootView.findViewById(R.id.fragment_event_detail_share);

        cvShare.setOnClickListener(this);
        int imgHeaderID = getActivity().getResources().getIdentifier("header_" + event.sCategory.toLowerCase(), "drawable", getActivity().getPackageName());
        Log.d(DEBUG_TAG, "Searching for " + "header_" + event.sCategory.toLowerCase() + ".jpg -- Res: " + imgHeaderID);
        if (imgHeaderID != 0) {
            ImageView ivHeader = (ImageView) rootView.findViewById(R.id.fragment_event_detail_header);
            ivHeader.setImageResource(imgHeaderID);
        }
        TextView tvTitle = (TextView) rootView.findViewById(R.id.fragment_event_detail_title);
        tvTitle.setText(event.sTitle);

        TextView tvPlace = (TextView) rootView.findViewById(R.id.fragment_event_detail_place);
        tvPlace.setText(event.sPlace);
        CardView cvMap = (CardView) rootView.findViewById(R.id.fragment_event_detail_map);
        if(event.sLatitude != null && event.sLongitude != null) {
            cvMap.setOnClickListener(this);
        } else {
            cvMap.setVisibility(View.GONE);
        }

        TextView tvTime = (TextView) rootView.findViewById(R.id.fragment_event_detail_time);
        tvTime.setText(event.sStartTime);
        TextView tvDate = (TextView) rootView.findViewById(R.id.fragment_event_detail_date);
        tvDate.setText(event.sDate);
        CardView cvWeb = (CardView) rootView.findViewById(R.id.fragment_event_detail_web);
        if (event.sURL != null) {
            cvWeb.setOnClickListener(this);
        } else {
            cvWeb.setVisibility(View.GONE);
        }

        if (event.sPrice != null) {
            TextView tvPrice = (TextView) rootView.findViewById(R.id.fragment_event_detail_price);
            tvPrice.setText(event.sPrice);
        }

        TextView tvDetails = (TextView) rootView.findViewById(R.id.fragment_event_detail_details);
        if (event.sDescription != null) {
            tvDetails.setText(event.sDescription);
        } else {
            tvDetails.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.fragment_event_detail_map:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse((new StringBuilder("geo:0,0?q=")).append(event.sLatitude).append(",")
                                .append(event.sLongitude).append("(").append(event.sPlace).append(")").toString()));
                startActivity(intent);
                break;
            case R.id.fragment_event_detail_web:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.sURL));
                startActivity(intent);
                break;
            case R.id.fragment_event_detail_share:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, event.sTitle + " @ " + event.sPlace + " (" + event.sDate + ")" + " #fdmapp");
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.ab_share)));
                break;
        }
    }
}
