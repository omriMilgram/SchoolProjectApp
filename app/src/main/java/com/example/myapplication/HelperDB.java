package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class HelperDB extends SQLiteOpenHelper {

    public static final String DB_FILE = "userManagement.db";
    public static final String USERS_TABLE = "Users";
    public static final String USER_NAME = "UserName";
    public static final String USER_PWD = "UserPassword";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_PHONE = "UserPhone";
    public static final int DATABASE_VERSION = 1;
    private final Context context;

    public HelperDB(@Nullable Context context) {
        super(context, DB_FILE, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USERS_TABLE + " (" +
                USER_NAME + " TEXT, " +
                USER_EMAIL + " TEXT PRIMARY KEY, " +
                USER_PWD + " TEXT, " +
                USER_PHONE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(db);
    }

    public void insertUser(String userName, String userEmail, String userPassword, String userPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_EMAIL, userEmail);
        values.put(USER_PWD, userPassword);
        values.put(USER_PHONE, userPhone);

        long result = db.insert(USERS_TABLE, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed to register user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("Range")
    public UserDetails getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_NAME, USER_PWD, USER_EMAIL, USER_PHONE};
        String selection = USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(USERS_TABLE, columns, selection, selectionArgs, null, null, null);

        UserDetails user = null;
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(USER_NAME));
            String pwd = cursor.getString(cursor.getColumnIndex(USER_PWD));
            String userEmail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndex(USER_PHONE));
            user = new UserDetails(name, pwd, userEmail, phone);
            cursor.close();
        }

        return user;
    }
}