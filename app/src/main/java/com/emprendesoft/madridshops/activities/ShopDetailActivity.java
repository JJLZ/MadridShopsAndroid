package com.emprendesoft.madridshops.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.shops.model.Shop;
import com.emprendesoft.madridshops.util.Constants;
import com.emprendesoft.madridshops.util.StaticMapImage;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDetailActivity extends AppCompatActivity {

    @BindView(R.id.activity_shop_detail__shop_address) TextView address;
    @BindView(R.id.activity_shop_detail__shop_description) TextView description;
    @BindView(R.id.activity_shop_detail__shop_image) ImageView shopImage;
    @BindView(R.id.activity_shop_detail__shop_map) ImageView mapImage;
    @BindView(R.id.activity_shop_detail__shop_name) TextView name;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            Shop shop = (Shop) intent.getSerializableExtra(Constants.INTENT_SHOP_DETAIL);

            name.setText(shop.getName());
            address.setText(shop.getAddress());

            //-- Spanish or English --
            if (Locale.getDefault().getLanguage().equals("es"))
            {
                description.setText(shop.getDescriptionES());
            }
            else
            {
                description.setText(shop.getDescriptionEN());
            }
            //--

            Picasso.with(this)
                    .load(shop.getImageUrl())
                    .placeholder(R.drawable.shop_placeholder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(shopImage);

            String staticMarUrl = StaticMapImage.getMapImageUrl(shop);
            Picasso.with(this).load(staticMarUrl).placeholder(R.drawable.map_placeholder).networkPolicy(NetworkPolicy.OFFLINE).into(mapImage);
        }
    }
}






























