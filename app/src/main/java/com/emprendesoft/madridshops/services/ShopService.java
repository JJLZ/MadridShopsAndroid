package com.emprendesoft.madridshops.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

public class ShopService extends IntentService {

    public static void startRunningService(Context context) {
        Intent intent = ShopService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 1000*5, pendingIntent);
    }

    private static Intent newIntent(Context context) {

        return new Intent(context, ShopService.class);
    }

    public static void stopRunningService(Context context) {
        Intent intent = ShopService.newIntent(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    // designed
    public ShopService() {
        this("Default");
    }

    // convenience
    public ShopService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d("Service", "Hello hello");
    }
}
