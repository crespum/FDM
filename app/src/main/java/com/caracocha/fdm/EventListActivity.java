package com.caracocha.fdm;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


/**
 * An activity representing a list of Events. This activity
 * has different presentations for handset and tablet-size devices.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link EventListFragment} and the item details
 * (if present) is a {@link EventDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link com.caracocha.fdm.EventListFragment.onItemReceivedListener} interface
 * to listen for item selections.
 */
public class EventListActivity extends AppCompatActivity
        implements EventListFragment.onItemReceivedListener {

    private static final String DEBUG_TAG = "EventListActivity";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        if (findViewById(R.id.event_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            //((EventListFragment) getSupportFragmentManager()
            //        .findFragmentById(R.id.event_list))
            //        .setActivateOnItemClick(true);
        } else {
            FragmentTransaction fragmenttransaction = getFragmentManager().beginTransaction();
            fragmenttransaction.add(R.id.activity_event_list_fragment, new EventListFragment());
            //fragmenttransaction.addToBackStack(null);
            fragmenttransaction.commit();
        }

    }

    /**
     * Callback method from {@link EventListFragment.onItemReceivedListener}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemReceived(Item item) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

        } else {
            if (item.sType.equals(Item.EVENT)) {
                Log.d(DEBUG_TAG, "Begin fragment transaction");
                Bundle bundle = new Bundle();
                bundle.putParcelable("event", item);
                Fragment fragment = new EventDetailFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmenttransaction = getFragmentManager().beginTransaction();
                fragmenttransaction.replace(R.id.activity_event_list_fragment, fragment);
                fragmenttransaction.addToBackStack(null);
                fragmenttransaction.commit();
            } else {
                if (item.sURL != null) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(item.sURL));
                    startActivity(i);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
    //https://stackoverflow.com/questions/29787250/fragment-back-stack-does-not-work-when-extending-appcompatactivity#
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_main_settings:
                FragmentTransaction fragmenttransaction = getFragmentManager().beginTransaction();
                fragmenttransaction.replace(R.id.activity_event_list_fragment, new Preferences());
                fragmenttransaction.addToBackStack(null);
                fragmenttransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
