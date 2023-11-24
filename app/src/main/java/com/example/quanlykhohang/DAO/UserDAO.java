package com.example.quanlykhohang.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykhohang.Database.DbHelper;
import com.example.quanlykhohang.Model.User;

import java.util.ArrayList;

public class UserDAO {
    DbHelper dbHelper;
    public static final String TABLE_NAME = "User";

    public static final String COLUMN_ID_USER = "id_User";

    public static final String COLUMN_PASSWORD = "password";

    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ROLE = "role";
    public UserDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    public boolean insertData(User obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_USER, obj.getUser());
        contentValues.put(COLUMN_PASSWORD, obj.getPassword());
        contentValues.put(COLUMN_FULLNAME, obj.getFullname());
        contentValues.put(COLUMN_EMAIL, obj.getEmail());
        contentValues.put(COLUMN_ROLE, obj.getRole());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return check != -1;
    }

    public boolean delete(User obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getUser()};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID_USER + "= ?", dk);
        return check != -1;
    }

    public boolean update(User obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getUser()};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, obj.getPassword());
        contentValues.put(COLUMN_FULLNAME, obj.getFullname());
        contentValues.put(COLUMN_ROLE, obj.getRole());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID_USER + "= ?", dk);
        return check != -1;
    }

    public boolean updatePass(User obj) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getUser()};
        contentValues.put(COLUMN_PASSWORD, obj.getPassword());
        contentValues.put(COLUMN_FULLNAME, obj.getFullname());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID_USER + "= ?", dk);
        return check != -1;
    }

    public ArrayList<User> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String idUser = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_USER));
                String matKhau = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                String fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULLNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                int role = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)));
                list.add(new User(idUser, matKhau, fullName,email, role));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<User> SelectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public User SelectID(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID_USER + " = ?";
        ArrayList<User> list = getAll(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean checkLogin(String username, String password, String role) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM User WHERE " + COLUMN_ID_USER + "=? AND " + COLUMN_PASSWORD + "=? AND " + COLUMN_ROLE + " = ?";
        String[] selectionArgs = new String[]{username, password, role};

        // Convert role to integer
        int intRole = Integer.parseInt(role);

        // Add the integer role to the selectionArgs
        selectionArgs[2] = String.valueOf(intRole);

        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        boolean result = cursor.getCount() > 0;
        return result;
    }

}
