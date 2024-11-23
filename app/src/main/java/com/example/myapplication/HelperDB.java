package com.example.myapplication;
import android.content.Context; // ייבוא של Context
import android.database.sqlite.SQLiteDatabase; // ייבוא של SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper; // ייבוא של SQLiteOpenHelper
import androidx.annotation.Nullable;




public class HelperDB extends SQLiteOpenHelper {

    // שם קובץ בסיס הנתונים
    public static final String DB_FILE = "lesson_in_chemistry.db";

    // שם הטבלה
    public static final String USERS_TABLE = "Users";

    // שמות העמודות בטבלה
    public static final String USER_NAME = "UserName";
    public static final String USER_PWD = "UserPassword";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_PHONE = "UserPhone";

    // בנאי
    public HelperDB(@Nullable Context context) {
        super(context, DB_FILE, null, 1);

    }

    @Override
    //יוצרת טבלאות
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(buildUserTable());
        // Create the users table with the specified columns and data types
        String createTable = "CREATE TABLE " + USERS_TABLE + " (" +
                USER_NAME + " TEXT, " +
                USER_EMAIL + " TEXT PRIMARY KEY, " +
                USER_PWD + " TEXT, " +
                USER_PHONE + " TEXT)";
        db.execSQL(createTable);

    }

    @Override
    //משדרגת את בסיס הנתונים
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the older table if it exists and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(db);
    }



    public String buildUserTable() {

        String st = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE;

        st += "(" + USER_NAME + " TEXT, ";

        st += USER_PWD + " TEXT, ";

        st += USER_EMAIL + " TEXT, ";

        st += USER_PHONE + " TEXT);";

        return st;
    }
}