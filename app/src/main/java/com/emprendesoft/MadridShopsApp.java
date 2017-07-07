package com.emprendesoft;

import android.support.multidex.MultiDexApplication;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class MadridShopsApp extends MultiDexApplication {

    public static final String APP_NAME = MadridShopsApp.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();

        //--now callout --
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
//        built.setIndicatorsEnabled(true);
//        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

//        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
//        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
        //--


        //--now Service Test 1 --
//        Intent intent = new Intent(getApplicationContext(), ShopService.class);
//        startService(intent);
//        startService(intent);
        //--

        //--now Service Test 2 --
//        Intent intent = new Intent(getApplicationContext(), ShopService.class);
//
//        PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 0, intent, 0);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 1000 * 5, pendingIntent);
        //--

        //--now Service Test 3 --
//        ShopService.startRunningService(this);
        //--

        //--now Service Test 4 --
//        Resources resources = getResources();
//        Intent intent = new Intent(this, ShopListActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
//
//        Notification notification = new NotificationCompat.Builder(this)
//                .setTicker("Ticker")    // Set the "ticker" text which is sent to accessibility services.
//                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
//                .setContentTitle("You're spyed now")
//                .setContentText("Notification Text")
//                .setContentIntent(pi)
//                .setAutoCancel(true)
//                .build();
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        int notificationId = 0;
//        notificationManager.notify(notificationId, notification);
        //--

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        // low memory: dump something
    }
}
