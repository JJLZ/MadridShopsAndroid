package com.emprendesoft.madridactivities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.activities.model.Activity;
import com.emprendesoft.madridshops.util.Constants;
import com.emprendesoft.madridshops.util.StaticMapImage;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityDetailActivity extends AppCompatActivity {

    @BindView(R.id.activity_detail_activity__activity_address) TextView address;
    @BindView(R.id.activity_detail_activity__activity_description) TextView description;
    @BindView(R.id.activity_detail_activity__activity_image) ImageView activityImage;
    @BindView(R.id.activity_detail_activity__activity_map) ImageView mapImage;
    @BindView(R.id.activity_detail_activity__activity_name) TextView name;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            Activity activity = (Activity) intent.getSerializableExtra(Constants.INTENT_ACTIVITY_DETAIL);

            name.setText(activity.getName());
            address.setText(activity.getAddress());
            description.setText(activity.getDescription().trim());

            Picasso.with(this)
                    .load(activity.getImageUrl())
                    .placeholder(R.drawable.activity_placeholder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(activityImage);

            String staticMarUrl = StaticMapImage.getMapImageUrlFromActivity(activity);
            Picasso.with(this).load(staticMarUrl).placeholder(R.drawable.map_placeholder).networkPolicy(NetworkPolicy.OFFLINE).into(mapImage);
        }
    }
}






























