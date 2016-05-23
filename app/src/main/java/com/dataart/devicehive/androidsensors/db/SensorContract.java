package com.dataart.devicehive.androidsensors.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class SensorContract {


    public static final String CONTENT_AUTHORITY = "com.dataart.devicehive.androidsensors";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ENTRY = "sensors";
    public static final String PATH_DATA = "sensors_data";
    public static final String PATH_SYNC = "sensors_data_sync";

    public SensorContract() {
    }


    public static abstract class SensorEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRY).build();

        public static Uri buildSensorUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "sensors";

        public static final String COLUMN_NAME_SENSOR_NAME = "sensor_name";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_IS_LISTENED = "is_listened";


    }

    public static abstract class SensorsData implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DATA).build();

        public static Uri buildSensorDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

//        public static Uri buildSensorDataUriWithStartDate(l)

        public static final String TABLE_NAME = "sensors_data";

        public static final String COLUMN_NAME_SENSOR_ID = "sensor_id";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";


    }

    public static abstract class SensorsDataSync implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SYNC).build();

        public static final String TABLE_NAME = "sensors_data_sync";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";

    }

}


