package com.emprendesoft.madridshops.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.emprendesoft.madridshops.domain.Util.ClearCache;


public class ClearCacheService extends IntentService
{
    private static Context mContext;

    public ClearCacheService(String name)
    {
        super(name);
    }

    public ClearCacheService()
    {
        this("Default");
    }

    private static Intent newIntent(Context context)
    {
        return new Intent(context, ClearCacheService.class);
    }

    public static void scheduleCacheCleaning(Context context, int sec)
    {
        mContext = context;

        Intent intent = ClearCacheService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + (1000 * sec), pendingIntent);
    }

    public static void cancelScheduledCacheCleaning(Context context)
    {
        Intent intent = ClearCacheService.newIntent(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        ClearCache.clearAllCache(mContext);
    }

}
