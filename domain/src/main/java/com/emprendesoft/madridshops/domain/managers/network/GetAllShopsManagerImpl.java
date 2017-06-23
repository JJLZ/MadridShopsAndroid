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

import java.lang.ref.WeakReference;

public class GetAllShopsManagerImpl implements NetworkManager {

    WeakReference<Context> weakContext;

    public GetAllShopsManagerImpl(Context context) {

        weakContext = new WeakReference<Context>(context);
    }

    @Override
    public void getShopsFromServer(@NonNull GetAllShopsManagerCompletion completion, @Nullable ManagerErrorCompletion errorCompletion) {

        String url = weakContext.get().getString(R.string.shops_url);

        RequestQueue queue = Volley.newRequestQueue(weakContext.get());

        StringRequest request = new StringRequest(

                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSON", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON", error.toString());
                    }
                }
        );

        queue.add(request);
    }
}
