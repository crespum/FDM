package com.caracocha.fdm;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A fragment representing a single Event detail screen.
 * This fragment is either contained in a {@link EventListActivity}
 */
public class EventDetailFragment extends Fragment {
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
        //ImageView ivHeader = (ImageView) rootView.findViewById(R.id.fragment_event_detail_header);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.fragment_event_detail_title);
        tvTitle.setText(event.sTitle);

        TextView tvPlace = (TextView) rootView.findViewById(R.id.fragment_event_detail_place);
        tvPlace.setText(event.sPlace);
        CardView cvMap = (CardView) rootView.findViewById(R.id.fragment_event_detail_map);

        TextView tvTime = (TextView) rootView.findViewById(R.id.fragment_event_detail_time);
        tvTime.setText(event.sStartTime);
        TextView tvDate = (TextView) rootView.findViewById(R.id.fragment_event_detail_date);
        tvDate.setText(event.sDate);
        CardView cvWeb = (CardView) rootView.findViewById(R.id.fragment_event_detail_web);

        TextView tvPrice = (TextView) rootView.findViewById(R.id.fragment_event_detail_price);

        TextView tvDetails = (TextView) rootView.findViewById(R.id.fragment_event_detail_details);
        tvDetails.setText(event.sDescription);

        return rootView;
    }
}
