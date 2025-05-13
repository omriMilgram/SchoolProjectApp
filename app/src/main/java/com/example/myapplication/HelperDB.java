package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * {@code HelperDB} is a helper class to manage SQLite database creation and version management.
 * It provides methods to insert users, fetch individual users by email, and retrieve all users.
 * <p>
 * The database contains a single table named {@code Users}, with columns for name, email, password, and phone.
 * </p>
 */
public class HelperDB extends SQLiteOpenHelper {

    // Constants for database file and table schema
    public static final String DB_FILE = "userManagement.db";   // SQLite database file name
    public static final String USERS_TABLE = "Users";           // Name of the table
    public static final String USER_NAME = "UserName";          // Column for user name
    public static final String USER_PWD = "UserPassword";       // Column for user password
    public static final String USER_EMAIL = "UserEmail";        // Column for user email (Primary Key)
    public static final String USER_PHONE = "UserPhone";        // Column for user phone number
    public static final int DATABASE_VERSION = 1;               // Initial database version

    private final Context context;  // Used to show Toasts

    /**
     * Constructor for the {@code HelperDB} class.
     *
     * @param context Application context for accessing resources and showing messages.
     */
    public HelperDB(@Nullable Context context) {
        super(context, DB_FILE, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * Called when the database is created for the first time.
     * This method creates the {@code Users} table.
     *
     * @param db The SQLite database instance.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USERS_TABLE + " (" +
                USER_NAME + " TEXT, " +
                USER_EMAIL + " TEXT PRIMARY KEY, " +
                USER_PWD + " TEXT, " +
                USER_PHONE + " TEXT)";
        db.execSQL(createTable);
    }

    /**
     * Called when the database needs to be upgraded.
     * This method drops the old table and creates a new one.
     *
     * @param db         The SQLite database instance.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(db);
    }

    /**
     * Inserts a new user into the {@code Users} table.
     *
     * @param userName     The user's full name.
     * @param userEmail    The user's email address.
     * @param userPassword The user's password (stored as plain text – should be encrypted in production).
     * @param userPhone    The user's phone number.
     */
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

        db.close();
    }

    /**
     * Retrieves a user's details from the database by their email address.
     *
     * @param email The email address of the user.
     * @return A {@link UserDetails} object containing the user’s data, or {@code null} if not found.
     */
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

    /**
     * Retrieves all user data from the database.
     * This method is primarily useful for debugging or logging purposes.
     */
    public void fetchData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Indices for the desired columns
                int nameIndex = cursor.getColumnIndex(USER_NAME);
                int emailIndex = cursor.getColumnIndex(USER_EMAIL);
                int phoneIndex = cursor.getColumnIndex(USER_PHONE);

                // In production, log or use the values as needed
                // For example:
                // String name = cursor.getString(nameIndex);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
}
