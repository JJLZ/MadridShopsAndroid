package com.emprendesoft.madridshops.domain.activities.network.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivitiesResponseEntity {

    @SerializedName("result") private List<ActivityEntity> result;

    public List<ActivityEntity> getResult() {
        return result;
    }
}
