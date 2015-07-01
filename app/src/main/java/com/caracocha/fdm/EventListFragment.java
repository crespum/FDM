package com.caracocha.fdm;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import java.util.ArrayList;

/**
 * A list fragment representing a list of Events.
 */
public class EventListFragment extends Fragment implements
        JSONReader.onJSONDownloadListener, ItemViewHolder.onItemClickListener {

    private static final String DEBUG_TAG = "EventListFragment";

    public static final String JSON_LINK = "https://www.dropbox.com/s/ekflrytyix82z7g/Eventos.txt?dl=1";

    private RecyclerView rvEventList;
    private ItemAdapter iaEventList;
    private ArrayList<Item> alEvents;
    private LinearLayoutManager layoutManager;

    onItemReceivedListener ifItemReceived;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alEvents = new ArrayList(10);
        JSONReader jr = new JSONReader(getActivity(), this, EventListFragment.JSON_LINK, alEvents);
        jr.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);
        rvEventList = (RecyclerView) rootView.findViewById(R.id.event_list);
        rvEventList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvEventList.setLayoutManager(layoutManager);
        iaEventList = new ItemAdapter(alEvents, getActivity(), this);
        rvEventList.setAdapter(iaEventList);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof onItemReceivedListener)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        ifItemReceived = (onItemReceivedListener) activity;
    }

    /**
     * Interface for JSONReader (refreshes the view when the download finishes)
     */
    public void onJSONDownload() {
        Log.d(DEBUG_TAG, "JSON downloaded");
        iaEventList.notifyDataSetChanged();

    }

    /**
     * Interface for the ItemViewHolder class. After the fragment is notified that an item
     * has been selected, the activity is notified to start the a different fragment containing
     * the details.
     *
     * @param iPos index of {@link Item} alEvents corresponding to the clicked element
     */
    @Override
    public void onItemClick(int iPos) {
        ifItemReceived.onItemReceived(alEvents.get(iPos));
    }

    /**
     * Callback for when an item has been selected.
     */
    public interface onItemReceivedListener {
        void onItemReceived(Item item);
    }

}
