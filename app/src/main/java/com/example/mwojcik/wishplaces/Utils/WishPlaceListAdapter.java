package com.example.mwojcik.wishplaces.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.dto.WishPlace;

import java.util.ArrayList;

public class WishPlaceListAdapter extends ArrayAdapter<WishPlace> {
    private ArrayList<WishPlace> placesList;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        TextView txtSummary;
    }

    public WishPlaceListAdapter(ArrayList<WishPlace> data, Context context){
        super(context, R.layout.places_row_item, data);
        this.placesList = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WishPlace favyPlace = getItem(i);
        ViewHolder viewHolder;

        final View result;

        if (view == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.places_row_item, viewGroup, false);
            viewHolder.txtName = (TextView) view.findViewById(R.id.place_name);
            viewHolder.txtSummary = (TextView) view.findViewById(R.id.place_summary);

            result=view;

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            result=view;
        }

        lastPosition = i;

        viewHolder.txtName.setText(favyPlace.getName());
        viewHolder.txtSummary.setText(favyPlace.getSummary());

        //zwracamy gotowy widok na ekran
        return view;
    }


}
