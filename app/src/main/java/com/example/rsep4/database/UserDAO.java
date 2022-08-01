package com.example.rsep4.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rsep4.models.UserModel;

@Dao
public interface UserDAO {
    @Insert
    void insert(UserModel UserModel);

    @Update
    void update(UserModel UserModel);

    @Delete
    void delete(UserModel UserModel);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT * FROM user_table WHERE user_table.username = :username")
    LiveData<UserModel> getUserFromDb(String username);
}
