package com.emprendesoft.madridshops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.navigator.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main__shops_button) Button shopsButton;
    @BindView(R.id.activity_main__activities_button) Button activitiesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        shopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getCanonicalName(), "Hello");

//                Intent i = new Intent(MainActivity.this, ShopListActivity.class);
//                startActivity(i);
                Navigator.navigateFromMainActivityToShopListActivity(MainActivity.this);
            }
        });

        activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getCanonicalName(), "Hello Activities");
            }
        });
    }
}
