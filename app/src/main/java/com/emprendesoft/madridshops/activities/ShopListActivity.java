package com.emprendesoft.madridshops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractor;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorCompletion;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorFakeImp;
import com.emprendesoft.madridshops.domain.interactors.InteractorErrorCompletion;
import com.emprendesoft.madridshops.domain.model.Shops;

public class ShopListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        // obtain shop list
        GetAllShopsInteractor getAllShopsInteractor = new GetAllShopsInteractorFakeImp();
        getAllShopsInteractor.execute(
                new GetAllShopsInteractorCompletion() {
                    @Override
                    public void completion(Shops shops) {

                        for (int i = 0; i < shops.size() ; i++) {
                            System.out.println(shops.get(i).getName());
                        }
                    }
                },
                new InteractorErrorCompletion() {
                    @Override
                    public void onError(String errorDescription) {

                    }
                }
        );
    }
}