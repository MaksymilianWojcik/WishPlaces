package com.example.mwojcik.wishplaces.Fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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


public class WishPlaceListFragment extends Fragment {

    private boolean mTwoPane;
    private ListView placesListView;
    private WishPlaceListAdapter adapter;
    private ArrayList<WishPlace> placesList;

    private WishPlaceDetailFragment fragment;

    //Wymagany we fragmentach pusty konstruktor
    public WishPlaceListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.activity_wishplace_list, container, false);

        if (view.findViewById(R.id.wishplace_detail_container) != null) {
            mTwoPane = true;
        }

        placesListView = (ListView) view.findViewById(R.id.wishplace_list);
        placesList = new ArrayList<>();
        WishPlaceDao wishPlaceDao = new WishPlaceDao(getContext());
        wishPlaceDao.open();
        placesList = (ArrayList<WishPlace>) wishPlaceDao.getAllFavyPlaces();
        wishPlaceDao.close();

        adapter = new WishPlaceListAdapter(placesList, getContext());
        placesListView.setAdapter(adapter);

        final ArrayList<WishPlace> finalPlacesList = placesList;

        //Obsluga klikniecia miejsca na liscie
        placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WishPlace wishPlace = finalPlacesList.get(i);
                //Sprawdzamy, czy jestesmy na tablecie czy na mniejszym ekranie
                if (mTwoPane) {
                    //Jezeli jestesmy na tablecie, to ladujemy odpowoedni fragment
                    Bundle arguments = new Bundle();
                    arguments.putInt(WishPlaceDetailFragment.ARG_ITEM_ID, wishPlace.getId());
                    fragment = new WishPlaceDetailFragment();
                    fragment.setArguments(arguments);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.wishplace_detail_container, fragment)
                            .commit();
                } else {
                    //Jezeli nie na tablecie, to przechodzimy do nowego Activity
                    Context context = view.getContext();
                    Intent intent = new Intent(context, WishPlaceDetailActivity.class);
                    intent.putExtra(WishPlaceDetailFragment.ARG_ITEM_ID, wishPlace.getId());
                    context.startActivity(intent);
                }
            }
        });

        //Obsluga dlugiego klikniecia w miejsce na liscie
        placesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.place_delete_dialog_title);
                builder.setMessage(R.string.place_delete_dialog_info);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WishPlaceDao wishPlaceDao = new WishPlaceDao(getContext());
                        wishPlaceDao.open();
                        //remove
                        wishPlaceDao.removeWishPlace(placesList.get(position));
                        wishPlaceDao.close();
                        placesList.remove(position);
                        adapter.getPlacesList().clear();
                        adapter.getPlacesList().addAll(placesList);
                        adapter.notifyDataSetChanged();

                        if(mTwoPane){
                            if (fragment != null) {
                                getFragmentManager().beginTransaction().remove(fragment).commit();
                            }
                            Log.d("MapFragmentCheck", "mTwo pane is true");
                        } else {
                            Log.d("MapFragmentCheck", "mTwo pane is false");
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(23)
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                builder.show();
                return true;
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
