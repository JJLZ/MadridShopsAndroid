package com.emprendesoft.madridshops.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.model.Shop;

public class ShopListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        Shop.of(1, "Mi tienda").setAddress("fdfdffdf");
    }
}
