package com.emprendesoft.madridshops.domain.activities.network.jsonparser;

import com.emprendesoft.madridshops.domain.activities.network.entities.ActivitiesResponseEntity;
import com.emprendesoft.madridshops.domain.activities.network.entities.ActivityEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class ActivitiesJsonParser {

    public List<ActivityEntity> parse(String response) {

        List<ActivityEntity> activityEntities = null;

        try {
            Gson gson = new GsonBuilder().create();

            Reader reader = new StringReader(response);
            ActivitiesResponseEntity activitiesResponseEntity = gson.fromJson(reader, ActivitiesResponseEntity.class);
            activityEntities = activitiesResponseEntity.getResult();
        } catch (Exception e) {
            e.printStackTrace();;
        }

        return activityEntities;
    }
}
