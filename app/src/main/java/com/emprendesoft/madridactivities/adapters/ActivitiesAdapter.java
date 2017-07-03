package com.emprendesoft.madridactivities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emprendesoft.madridactivities.views.ActivityRowViewHolder;
import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.model.Activities;
import com.emprendesoft.madridshops.domain.model.Activity;
import com.emprendesoft.madridshops.views.OnElementClick;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivityRowViewHolder> {

    private Activities mActivities;
    private LayoutInflater mInflater;
    private OnElementClick<Activity> mListener;

    public ActivitiesAdapter(final Activities activities, final Context context) {

        mActivities = activities;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.row_activity, parent, false);
        ActivityRowViewHolder viewHolder = new ActivityRowViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ActivityRowViewHolder activityRow, final int position) {

        final Activity activity = mActivities.get(position);
        activityRow.setActivity(activity);

        activityRow.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.clickedOn(activity, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        if (mActivities != null) {

            return (int) mActivities.size();
        }

        return 0;
    }

    public void setONClickListener(OnElementClick<Activity> listener) {
        mListener = listener;
    }
}






























