package com.immad.sabahat.buzzmovetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import beans.Place;

public class CustomAdapter extends ArrayAdapter<Place> {

    private ArrayList<Place> places;
    Context ctx;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtAddress;
        ImageView icon;
    }

    public CustomAdapter(ArrayList<Place> places, Context context) {
        super(context, R.layout.list_item, places);
        this.places = places;
        this.ctx=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Place place = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtAddress = (TextView) convertView.findViewById(R.id.address);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        /*Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;*/

        viewHolder.txtName.setText(place.getName());
        viewHolder.txtType.setText(place.getTypes());
        viewHolder.txtAddress.setText(place.getVicinity());

        Glide.with(ctx)
                .load(place.getIcon())
                .placeholder(R.drawable.common_full_open_on_phone)
                .override(120,120)
                .into(viewHolder.icon);
        // Return the completed view to render on screen
        return convertView;
    }

}
