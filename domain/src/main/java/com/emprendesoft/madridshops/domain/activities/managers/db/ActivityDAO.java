package com.emprendesoft.madridshops.domain.activities.managers.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.emprendesoft.madridshops.domain.activities.model.Activity;
import com.emprendesoft.madridshops.domain.shops.managers.db.DAOReadable;
import com.emprendesoft.madridshops.domain.shops.managers.db.DAOWritable;
import com.emprendesoft.madridshops.domain.shops.managers.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.ALL_COLUMNS_ACTIVITY;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_ADDRESS;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_DESCRIPTION_EN;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_DESCRIPTION_ES;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_ID;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_IMAGE_URL;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_LATITUDE;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_LOGO_IMAGE_URL;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_LONGITUDE;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_NAME;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.KEY_ACTIVITY_URL;
import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.TABLE_ACTIVITY;

public class ActivityDAO implements DAOReadable<Activity>, DAOWritable<Activity>
{
    private static final long EMPTY_ACTIIVITY = 0;
    private SQLiteDatabase dbReadConnection;
    private SQLiteDatabase dbWriteConnection;

    public ActivityDAO(DBHelper dbHelper) {
        dbReadConnection = dbHelper.getReadableDatabase();
        dbWriteConnection = dbHelper.getWritableDatabase();
    }

    public ActivityDAO(Context context) {
        this(new DBHelper(context));
    }

    @Override
    public @Nullable Activity query(long id) {

        String idAsString = String.format("%d", id);
        List<Activity> activities = query(KEY_ACTIVITY_ID + " = ?", new String[]{idAsString}, KEY_ACTIVITY_ID);

        if (activities == null || activities.size() == 0) {

            return null;
        }

        return activities.get(0);
    }

    @Override
    public @Nullable List<Activity> query() {

        return query(null, null, KEY_ACTIVITY_ID);
    }

    @Override
    public @Nullable List<Activity> query(String where, String[] whereArgs, String orderBy) {

        Cursor c = dbReadConnection.query(TABLE_ACTIVITY,
                ALL_COLUMNS_ACTIVITY,    // columns I want to obtain
                where,          // where
                whereArgs,      // where args
                orderBy,        // order by
                null,           // group
                null);          // having

        if (c == null || c.getCount() == 0) {

            return null;
        }
        
        List<Activity> actvitiyList = new ArrayList<>();

        while (c.moveToNext()) {

            long id = c.getLong(c.getColumnIndex(KEY_ACTIVITY_ID));
            String name = c.getString(c.getColumnIndex(KEY_ACTIVITY_NAME));
            String address = c.getString(c.getColumnIndex(KEY_ACTIVITY_ADDRESS));
            String descriptionES = c.getString(c.getColumnIndex(KEY_ACTIVITY_DESCRIPTION_ES));
            String descriptionEN = c.getString(c.getColumnIndex(KEY_ACTIVITY_DESCRIPTION_EN));
            String imageUrl = c.getString(c.getColumnIndex(KEY_ACTIVITY_IMAGE_URL));
            String logoImageUrl = c.getString(c.getColumnIndex(KEY_ACTIVITY_LOGO_IMAGE_URL));
            String url = c.getString(c.getColumnIndex(KEY_ACTIVITY_URL));
            float latitude = c.getFloat(c.getColumnIndex(KEY_ACTIVITY_LATITUDE));
            float longitude = c.getFloat(c.getColumnIndex(KEY_ACTIVITY_LONGITUDE));

            Activity activity = Activity.of(id, name)
                    .setAddress(address)
                    .setDescriptionES(descriptionES)
                    .setDescriptionEN(descriptionEN)
                    .setImageUrl(imageUrl)
                    .setLogoUrl(logoImageUrl)
                    .setUrl(url)
                    .setLatitude(latitude)
                    .setLongitude(longitude);

            actvitiyList.add(activity);
        }

        return actvitiyList;
    }

    @Override
    public long insert(@NonNull Activity element) {

        if (element == null) {
            return EMPTY_ACTIIVITY;
        }

        dbWriteConnection.beginTransaction();
        long id = DBHelper.INVALID_ID;
        try {
            id = dbWriteConnection.insert(TABLE_ACTIVITY, null, getContentValues(element));
            element.setId(id);

            dbWriteConnection.setTransactionSuccessful();
        } finally {
            dbWriteConnection.endTransaction();
        }

        return id;
    }

    private ContentValues getContentValues(Activity activity) {

        final ContentValues contentValues = new ContentValues();

        if (activity == null) {
            return contentValues;
        }

        contentValues.put(KEY_ACTIVITY_NAME, activity.getName());
        contentValues.put(KEY_ACTIVITY_ADDRESS, activity.getAddress());
        contentValues.put(KEY_ACTIVITY_DESCRIPTION_ES, activity.getDescriptionES());
        contentValues.put(KEY_ACTIVITY_DESCRIPTION_EN, activity.getDescriptionEN());
        contentValues.put(KEY_ACTIVITY_IMAGE_URL, activity.getImageUrl());
        contentValues.put(KEY_ACTIVITY_LOGO_IMAGE_URL, activity.getLogoUrl());
        contentValues.put(KEY_ACTIVITY_URL, activity.getUrl());
        contentValues.put(KEY_ACTIVITY_LATITUDE, activity.getLatitude());
        contentValues.put(KEY_ACTIVITY_LONGITUDE, activity.getLongitude());

        return contentValues;
    }

    @Override
    public long update(long id, Activity element) {
        return 0;
    }

    @Override
    public long delete(long id) {

        return delete(KEY_ACTIVITY_ID + " = ?", "" + id);
    }

    @Override
    public long delete(Activity element) {

        return delete(element.getId());
    }

    @Override
    public void deleteAll() {

        delete(null, null);
    }

    @Override
    public long delete(String where, String... whereClause) {

        int deleteRegs = 0;
        dbWriteConnection.beginTransaction();

        try {
            deleteRegs = dbWriteConnection.delete(TABLE_ACTIVITY, where, whereClause);

            dbWriteConnection.setTransactionSuccessful();
        } finally {
            dbWriteConnection.endTransaction();
        }

        return deleteRegs;
    }

    @Override
    public int numRecords() {

        List<Activity> activityList = query();

        return activityList == null ? 0 : activityList.size();
    }
}






























