package com.immad.sabahat.buzzmovetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import beans.Place;

public class DescriptionActivity extends AppCompatActivity {

    ImageView icon;
    TextView name, address;
    Place place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        icon = findViewById(R.id.icon);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);

        Place place = (Place) getIntent().getSerializableExtra("place");

        Glide.with(this)
                .load(place.getIcon())
                .placeholder(R.drawable.common_full_open_on_phone)
                .override(120,120)
                .into(icon);

        name.setText(place.getName());
        address.setText(place.getFormatted_address().equals("") ? place.getVicinity() :
        place.getFormatted_address());
    }
}
