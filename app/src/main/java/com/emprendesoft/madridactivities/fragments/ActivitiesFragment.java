package com.emprendesoft.madridactivities.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emprendesoft.madridactivities.adapters.ActivitiesAdapter;
import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.activities.model.Activities;
import com.emprendesoft.madridshops.domain.activities.model.Activity;
import com.emprendesoft.madridshops.views.OnElementClick;

public class ActivitiesFragment extends Fragment
{

    DividerItemDecoration mDividerItemDecoration;
    private OnElementClick<Activity> mListener;
    private RecyclerView activityRecyclerView;
    private ActivitiesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_activities, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar();

        activityRecyclerView = (RecyclerView) root.findViewById(R.id.fragment_activities__recycler_view);
        activityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDividerItemDecoration = new DividerItemDecoration(activityRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        activityRecyclerView.addItemDecoration(mDividerItemDecoration);

        return root;
    }

    public void setActivities(Activities activities)
    {

        mAdapter = new ActivitiesAdapter(activities, getActivity());
        activityRecyclerView.setAdapter(mAdapter);

        mAdapter.setONClickListener(new OnElementClick<Activity>()
        {
            @Override
            public void clickedOn(@NonNull Activity activity, int position)
            {

                if (mListener != null)
                {
                    ActivitiesFragment.this.mListener.clickedOn(activity, position);
                }
            }
        });
    }

    public void setOnElementClickListener(OnElementClick<Activity> listener)
    {

        mListener = listener;
    }

}






























