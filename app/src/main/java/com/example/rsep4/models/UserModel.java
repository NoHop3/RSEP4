package com.example.rsep4.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserModel {

    @PrimaryKey
    @NonNull
    private String _id;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    @Ignore
    public UserModel(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    @Ignore
    public UserModel(String username, String password, String email)
    {
        this(username, password);
        this.email = email;
    }

    public UserModel(String _id, String username, String password, String email, String firstName, String lastName) {
        this(username, password, email);
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
