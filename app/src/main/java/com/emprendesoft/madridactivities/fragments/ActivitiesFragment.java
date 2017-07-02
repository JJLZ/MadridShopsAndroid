package com.emprendesoft.madridactivities.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emprendesoft.madridshops.R;

public class ActivitiesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_activities, container, false);

        RecyclerView activitiesRycyclerView = (RecyclerView) root.findViewById(R.id.fragment_activities__recycler_view);
        activitiesRycyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

}
