package com.hikki.masakapanih.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.hikki.masakapanih.model.DbResepModel;


import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM resep where judul=:judulku")
    List<DbResepModel> getAll(String judulku);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insertAll(DbResepModel users);

    @Delete
    int delete(DbResepModel user);

    @Query("DELETE FROM resep WHERE judul = :judul")
    int deleteById(String judul);

    @Query("SELECT * FROM resep")
    List<DbResepModel> getData();
}
