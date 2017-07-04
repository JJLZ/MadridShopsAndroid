package com.emprendesoft.madridshops.domain.activities.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.emprendesoft.domain.R;
import com.emprendesoft.madridshops.domain.activities.network.entities.ActivityEntity;
import com.emprendesoft.madridshops.domain.activities.network.jsonparser.ActivitiesJsonParser;
import com.emprendesoft.madridshops.domain.shops.managers.network.ManagerErrorCompletion;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetAllActivitiesManagerImpl implements ANetworkManager {

    WeakReference<Context> weakContext;

    public GetAllActivitiesManagerImpl(Context context) {

        weakContext = new WeakReference<>(context);
    }

    @Override
    public void getActivitiesFromServer(@NonNull final GetAllActivitiesManagerCompletion completion, @Nullable final ManagerErrorCompletion errorCompletion) {

        String url = weakContext.get().getString(R.string.activities_url);

        RequestQueue queue = Volley.newRequestQueue(weakContext.get());

        StringRequest request = new StringRequest(

                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ActivitiesJsonParser parser = new ActivitiesJsonParser();
                        List<ActivityEntity> entities = parser.parse(response);

                        if (completion != null) {
                            completion.completion(entities);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (errorCompletion != null) {
                            errorCompletion.onError(error.getMessage());
                        }
                    }
                }
        );

        queue.add(request);
    }
}






























