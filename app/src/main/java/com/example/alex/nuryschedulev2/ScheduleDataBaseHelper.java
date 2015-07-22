package com.example.alex.nuryschedulev2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alex on 11.06.15.
 */
public class ScheduleDataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "schedule.sqlite";
    private static final int VERSION = 1;
    private static final String TABLE_SCHEDULE = "schedule";
    private static final String COLUMN_SXHEDULE_DAY = "day";
    private static final String COLUMN_SXHEDULE_TIME = "time";
    private static final String COLUMN_SXHEDULE_PLACE = "place";
    private static final String COLUMN_SXHEDULE_TYPELESSON = "type lesson";

    public ScheduleDataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating tavle  "Schedule"
        db.execSQL("create table Schedule (" + "_id integer primary key autoincrement, start_date integer)");
        // Создание таблицы "location"
        db.execSQL("create table location (" +
                " timestamp integer, latitude real, longitude real, altitude real," +
                " provider varchar(100), run_id integer references run(_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Здесь реализуются изменения схемы и преобразования данных
        // при обновлении схемы
    }

    public long insertRun() {
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_RUN_START_DATE, run.getStartDate().getTime());
        return getWritableDatabase().insert(TABLE_SCHEDULE, null, cv);
    }
}
