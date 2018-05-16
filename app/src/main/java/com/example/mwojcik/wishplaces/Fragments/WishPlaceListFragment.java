package com.example.mwojcik.wishplaces.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mwojcik.wishplaces.Activity.WishPlaceDetailActivity;
import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.Utils.WishPlaceListAdapter;
import com.example.mwojcik.wishplaces.dao.WishPlaceDao;
import com.example.mwojcik.wishplaces.dto.WishPlace;

import java.util.ArrayList;

/**
 * An activity representing a list of WishPlaces. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link WishPlaceDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class WishPlaceListFragment extends Fragment {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ListView placesListView;
    private WishPlaceListAdapter adapter;
    private ArrayList<WishPlace> placesList;


    public WishPlaceListFragment(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.activity_wishplace_list, container, false);

        if (view.findViewById(R.id.wishplace_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        //TODO: nie robic tego tu we fragmencie tylko przekazac juz do fragmentu gotowa liste

        placesListView = (ListView) view.findViewById(R.id.wishplace_list);
        placesList = new ArrayList<>();
        WishPlaceDao wishPlaceDao = new WishPlaceDao(getContext());
        wishPlaceDao.open();
        placesList = (ArrayList<WishPlace>) wishPlaceDao.getAllFavyPlaces();
        wishPlaceDao.close();

        adapter = new WishPlaceListAdapter(placesList, getContext());
        placesListView.setAdapter(adapter);

        final ArrayList<WishPlace> finalPlacesList = placesList;
        placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WishPlace wishPlace = finalPlacesList.get(i);
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(WishPlaceDetailFragment.ARG_ITEM_ID, wishPlace.getId());
                    WishPlaceDetailFragment fragment = new WishPlaceDetailFragment();
                    fragment.setArguments(arguments);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.wishplace_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, WishPlaceDetailActivity.class);
                    intent.putExtra(WishPlaceDetailFragment.ARG_ITEM_ID, wishPlace.getId());

                    context.startActivity(intent);
                }
            }
        });
      return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        WishPlaceDao wishPlaceDao = new WishPlaceDao(getContext());
        wishPlaceDao.open();
        placesList = (ArrayList<WishPlace>) wishPlaceDao.getAllFavyPlaces();
        wishPlaceDao.close();
        adapter.getPlacesList().clear();
        adapter.getPlacesList().addAll(placesList);
        adapter.notifyDataSetChanged();
    }

}
