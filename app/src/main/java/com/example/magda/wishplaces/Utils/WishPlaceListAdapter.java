package com.example.magda.wishplaces.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.magda.wishplaces.R;
import com.example.magda.wishplaces.dto.WishPlace;

import java.util.ArrayList;
import java.util.List;

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

        return view;
    }

    @Override
    public int getCount() {
        return placesList.size();
    }

    @Nullable
    @Override
    public WishPlace getItem(int position) {
        return placesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<WishPlace> getPlacesList(){
        return this.placesList;
    }
}
