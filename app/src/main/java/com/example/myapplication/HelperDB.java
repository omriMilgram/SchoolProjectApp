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

    // Database file and table details
    public static final String DB_FILE = "userManagement.db";   // Database file name
    public static final String USERS_TABLE = "Users";           // Users table name
    public static final String USER_NAME = "UserName";          // Column name for user's name
    public static final String USER_PWD = "UserPassword";       // Column name for user's password
    public static final String USER_EMAIL = "UserEmail";       // Column name for user's email
    public static final String USER_PHONE = "UserPhone";       // Column name for user's phone number
    public static final int DATABASE_VERSION = 1;               // Database version
    private final Context context;

    // Constructor to initialize the context
    public HelperDB(@Nullable Context context) {
        super(context, DB_FILE, null, DATABASE_VERSION);
        this.context = context;
    }

    // Create the database and the Users table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USERS_TABLE + " (" +
                USER_NAME + " TEXT, " +
                USER_EMAIL + " TEXT PRIMARY KEY, " +
                USER_PWD + " TEXT, " +
                USER_PHONE + " TEXT)";
        db.execSQL(createTable);
    }

    // Upgrade the database (drop and recreate the table)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(db);
    }

    // Insert a new user into the Users table
    public void insertUser(String userName, String userEmail, String userPassword, String userPhone) {
        SQLiteDatabase db = this.getWritableDatabase();  // Get writable database connection
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);                   // Add user name to the values
        values.put(USER_EMAIL, userEmail);                 // Add user email to the values
        values.put(USER_PWD, userPassword);                // Add user password to the values
        values.put(USER_PHONE, userPhone);                 // Add user phone number to the values

        long result = db.insert(USERS_TABLE, null, values);  // Insert the user data into the database

        if (result == -1) {
            Toast.makeText(context, "Failed to register user", Toast.LENGTH_SHORT).show();  // Show failure message
        } else {
            Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show();  // Show success message
        }

        db.close();  // Close the database connection after the operation
    }

    // Retrieve user details by email
    @SuppressLint("Range")
    public UserDetails getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();  // Get readable database connection
        String[] columns = {USER_NAME, USER_PWD, USER_EMAIL, USER_PHONE};  // Columns to retrieve
        String selection = USER_EMAIL + " = ?";  // SQL WHERE clause for the email
        String[] selectionArgs = {email};  // Arguments for the WHERE clause

        Cursor cursor = db.query(USERS_TABLE, columns, selection, selectionArgs, null, null, null);  // Query the database

        UserDetails user = null;
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(USER_NAME));      // Get user name
            String pwd = cursor.getString(cursor.getColumnIndex(USER_PWD));        // Get user password
            String userEmail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));  // Get user email
            String phone = cursor.getString(cursor.getColumnIndex(USER_PHONE));     // Get user phone number
            user = new UserDetails(name, pwd, userEmail, phone);  // Create a UserDetails object
            cursor.close();  // Close the cursor after retrieving the data
        }

        return user;  // Return the user details
    }

    // Fetch all user data from the database
    public void fetchData() {
        SQLiteDatabase db = this.getReadableDatabase();  // Get readable database connection
        String query = "SELECT * FROM " + USERS_TABLE;  // SQL query to select all users
        Cursor cursor = db.rawQuery(query, null);  // Execute the query

        if (cursor.moveToFirst()) {
            do {
                // Check if the columns exist (non-negative index)
                int nameIndex = cursor.getColumnIndex(USER_NAME);   // Get index of user name column
                int emailIndex = cursor.getColumnIndex(USER_EMAIL); // Get index of user email column
                int phoneIndex = cursor.getColumnIndex(USER_PHONE); // Get index of user phone column

                // You can add logic here to process the fetched data
            } while (cursor.moveToNext());  // Move to the next row in the result set
        }
        cursor.close();  // Close the cursor
        db.close();  // Close the database connection
    }
}
