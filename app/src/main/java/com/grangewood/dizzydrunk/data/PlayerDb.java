package com.grangewood.dizzydrunk.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by vquig on 07/10/15.
 */
public class PlayerDb {
    PlayerDataDbHelper dbHelper;

    public PlayerDb(Context context) {
        dbHelper = new PlayerDataDbHelper(context);
    }

    public void create(Player p){
        ContentValues values = new ContentValues();
        values.put(PlayerData.COLUMN_NAME, p.getName());
        values.put(PlayerData.COLUMN_IMAGE_LOCATION, p.getImageLocation());
        values.put(PlayerData.COLUMN_COLOR, p.getColor());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        p.setId(db.insert(PlayerData.TABLE_NAME, null, values));
    }

    public ArrayList<Player> read() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PlayerData._ID,
                PlayerData.COLUMN_NAME,
                PlayerData.COLUMN_IMAGE_LOCATION,
                PlayerData.COLUMN_COLOR
        };

        String sortOrder =
                PlayerData._ID + " ASC";

        Cursor c = db.query(
                PlayerData.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                             // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();

        ArrayList<Player> list = new ArrayList<>();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Player p = new Player();
            p.setId(c.getInt(c.getColumnIndex(PlayerData._ID)));
            p.setName(c.getString(c.getColumnIndex(PlayerData.COLUMN_NAME)));
            p.setImageLocation(c.getString(c.getColumnIndex(PlayerData.COLUMN_IMAGE_LOCATION)));
            p.setColor(c.getInt(c.getColumnIndex(PlayerData.COLUMN_COLOR)));

            list.add(p);
        }

        return list;
    }

    public void deleteRecord(Player p) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowCount = db.delete(PlayerData.TABLE_NAME,
                PlayerData._ID + "=" + p.getId(),
                null);

        Log.d("No of record deleted", String.valueOf(rowCount));
    }
}
