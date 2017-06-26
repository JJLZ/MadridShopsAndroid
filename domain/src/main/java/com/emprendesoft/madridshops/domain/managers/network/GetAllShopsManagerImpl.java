package com.emprendesoft.madridshops.domain.managers.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.emprendesoft.domain.R;
import com.emprendesoft.madridshops.domain.managers.network.entities.ShopEntity;
import com.emprendesoft.madridshops.domain.managers.network.jsonparser.ShopsJsonParser;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetAllShopsManagerImpl implements NetworkManager {

    WeakReference<Context> weakContext;

    public GetAllShopsManagerImpl(Context context) {

        weakContext = new WeakReference<Context>(context);
    }

    @Override
    public void getShopsFromServer(@NonNull final GetAllShopsManagerCompletion completion, @Nullable final ManagerErrorCompletion errorCompletion) {

        String url = weakContext.get().getString(R.string.shops_url);

        RequestQueue queue = Volley.newRequestQueue(weakContext.get());

        StringRequest request = new StringRequest(

                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSON", response);
//                        Log.d("JSON", Thread.currentThread().getName());

                        ShopsJsonParser parser = new ShopsJsonParser();
                        List<ShopEntity> entities = parser.parse(response);

                        if (completion != null) {
                            completion.completion(entities);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON", error.toString());
                        if (errorCompletion != null) {
                            errorCompletion.onError(error.getMessage());
                        }
                    }
                }
        );

        queue.add(request);
    }
}






























