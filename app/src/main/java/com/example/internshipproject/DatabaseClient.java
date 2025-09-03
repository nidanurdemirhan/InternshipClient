package com.example.internshipproject;

import android.content.Context;
import androidx.room.Room;

public class DatabaseClient {
    private static DatabaseClient mInstance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "suppliers.db")
                .allowMainThreadQueries()
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}

