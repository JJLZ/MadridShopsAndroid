package com.emprendesoft.madridshops.domain.shops.managers.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.emprendesoft.madridshops.domain.shops.model.Shop;

import java.util.ArrayList;
import java.util.List;

import static com.emprendesoft.madridshops.domain.shops.managers.db.DBConstants.*;

public class ShopDAO implements DAOReadable<Shop>, DAOWritable<Shop> {

    private static final long EMPTY_SHOP = 0;
    private SQLiteDatabase dbReadConnection;
    private SQLiteDatabase dbWriteConnection;

    public ShopDAO(DBHelper dbHelper) {
        dbReadConnection = dbHelper.getReadableDatabase();
        dbWriteConnection = dbHelper.getWritableDatabase();
    }

    public ShopDAO(Context context) {
        this(new DBHelper(context));
    }

    @Override
    public @Nullable List<Shop> query(String where, String[] whereArgs, String orderBy) {

        Cursor c = dbReadConnection.query(TABLE_SHOP,
                ALL_COLUMNS,    // columns I want to obtain
                where,          // where
                whereArgs,      // where args
                orderBy,        // order by
                null,           // group
                null);          // having

        if (c == null || c.getCount() == 0) {

            return null;
        }

        List<Shop> shopList = new ArrayList<>();

        while (c.moveToNext()) {

            long id = c.getLong(c.getColumnIndex(KEY_SHOP_ID));
            String name = c.getString(c.getColumnIndex(KEY_SHOP_NAME));
            String address = c.getString(c.getColumnIndex(KEY_SHOP_ADDRESS));
            String descriptionES = c.getString(c.getColumnIndex(KEY_SHOP_DESCRIPTION_ES));
            String descriptionEN = c.getString(c.getColumnIndex(KEY_SHOP_DESCRIPTION_EN));
            String imageUrl = c.getString(c.getColumnIndex(KEY_SHOP_IMAGE_URL));
            String logoImageUrl = c.getString(c.getColumnIndex(KEY_SHOP_LOGO_IMAGE_URL));
            String url = c.getString(c.getColumnIndex(KEY_SHOP_URL));
            float latitude = c.getFloat(c.getColumnIndex(KEY_SHOP_LATITUDE));
            float longitude = c.getFloat(c.getColumnIndex(KEY_SHOP_LONGITUDE));

            Shop shop = Shop.of(id, name)
                    .setAddress(address)
                    .setDescriptionES(descriptionES)
                    .setDescriptionEN(descriptionEN)
                    .setImageUrl(imageUrl)
                    .setLogoUrl(logoImageUrl)
                    .setUrl(url)
                    .setLatitude(latitude)
                    .setLongitude(longitude);

            shopList.add(shop);
        }

        return shopList;
    }

    @Override
    public @Nullable Shop query(long id) {

        String idAsString = String.format("%d", id);
        List<Shop> shops = query(KEY_SHOP_ID + " = ?", new String[]{idAsString}, KEY_SHOP_ID);

        if (shops == null || shops.size() == 0) {

            return null;
        }

        return shops.get(0);
    }

    @Override
    public List<Shop> query() {

        return query(null, null, KEY_SHOP_ID);
    }

    @Override
    public long insert(@NonNull Shop element) {

        if (element == null) {
            return EMPTY_SHOP;
        }

        dbWriteConnection.beginTransaction();
        long id = DBHelper.INVALID_ID;
        try {
            id = dbWriteConnection.insert(TABLE_SHOP, null, getContentValues(element));
            element.setId(id);

            dbWriteConnection.setTransactionSuccessful();
        } finally {
            dbWriteConnection.endTransaction();
        }

        return id;
    }

    private ContentValues getContentValues(Shop shop) {

        final ContentValues contentValues = new ContentValues();

        if (shop == null) {
            return contentValues;
        }

        contentValues.put(KEY_SHOP_NAME, shop.getName());
        contentValues.put(KEY_SHOP_ADDRESS, shop.getAddress());
        contentValues.put(KEY_SHOP_DESCRIPTION_ES, shop.getDescriptionES());
        contentValues.put(KEY_SHOP_DESCRIPTION_EN, shop.getDescriptionEN());
        contentValues.put(KEY_SHOP_IMAGE_URL, shop.getImageUrl());
        contentValues.put(KEY_SHOP_LOGO_IMAGE_URL, shop.getLogoUrl());
        contentValues.put(KEY_SHOP_URL, shop.getUrl());
        contentValues.put(KEY_SHOP_LATITUDE, shop.getLatitude());
        contentValues.put(KEY_SHOP_LONGITUDE, shop.getLongitude());


        return contentValues;
    }

    @Override
    public long update(long id, Shop element) {
        return 0;
    }

    @Override
    public long delete(long id) {

        return delete(KEY_SHOP_ID + " = ?", "" + id);
    }

    @Override
    public long delete(Shop element) {

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
            deleteRegs = dbWriteConnection.delete(TABLE_SHOP, where, whereClause);

            dbWriteConnection.setTransactionSuccessful();
        } finally {
            dbWriteConnection.endTransaction();
        }

        return deleteRegs;
    }

    @Override
    public int numRecords() {

        List<Shop> shopList = query();

        return shopList == null ? 0 : shopList.size();
    }

}






























