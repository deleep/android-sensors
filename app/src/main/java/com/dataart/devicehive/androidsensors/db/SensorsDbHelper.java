package com.dataart.devicehive.androidsensors.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dataart.devicehive.androidsensors.db.SensorContract.SensorEntry;
import com.dataart.devicehive.androidsensors.db.SensorContract.SensorsData;
import com.dataart.devicehive.androidsensors.db.SensorContract.SensorsDataSync;

public class SensorsDbHelper extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "sensors.db";


    public SensorsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_SENSORS_TABLE = "CREATE TABLE " + SensorEntry.TABLE_NAME + " (" +
                SensorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SensorEntry.COLUMN_NAME_SENSOR_NAME + " TEXT UNIQUE NOT NULL, " +
                SensorEntry.COLUMN_NAME_TYPE + " TEXT NOT NULL," +
                SensorEntry.COLUMN_NAME_IS_LISTENED + " INTEGER NOT NULL );";

        final String CREATE_SENSORS_DATA_TABLE = "CREATE TABLE " + SensorsData.TABLE_NAME + " (" +
                SensorsData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SensorsData.COLUMN_NAME_TIMESTAMP + " TEXT UNIQUE NOT NULL, " +
                " FOREIGN KEY (" + SensorsData.COLUMN_NAME_SENSOR_ID + ") REFERENCES " +
                SensorEntry.TABLE_NAME + " (" + SensorEntry._ID + "), " +
                SensorsData.COLUMN_NAME_VALUE + " INTEGER NOT NULL );";

        final String CREATE_SENSORS_SYNC_TABLE = "CREATE TABLE " + SensorsDataSync.TABLE_NAME + " (" +
                SensorsDataSync._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SensorsDataSync.COLUMN_NAME_TIMESTAMP + " TEXT NOT NULL );";

        db.execSQL(CREATE_SENSORS_TABLE);
        db.execSQL(CREATE_SENSORS_DATA_TABLE);
        db.execSQL(CREATE_SENSORS_SYNC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SensorEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SensorsData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SensorsDataSync.TABLE_NAME);
        onCreate(db);
    }

}
