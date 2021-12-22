package com.hikki.masakapanih.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.hikki.masakapanih.model.DbResepModel;

@Database(entities = {DbResepModel.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract Dao dao();
}
