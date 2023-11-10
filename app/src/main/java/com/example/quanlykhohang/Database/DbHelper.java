package com.example.quanlykhohang.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuanLyKhoHang";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUser = "CREATE TABLE User (" +
                "id_User INTERGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "fullname TEXT NOT NULL," +
                "email TEXT NOT NULL)";
        db.execSQL(createTableUser);
        String createTableTheLoai = "CREATE TABLE TheLoai (" +
                "id_theLoai INTERGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL)";
        db.execSQL(createTableTheLoai);
        String createTableSanPham = "CREATE TABLE SanPham (" +
                "id_sanPham INTERGER PRIMARY KEY AUTOINCREMENT," +
                "id_theLoai INTERGER," +
                "tenSanPham TEXT," +
                "soLuong INTERGER," +
                "moTa TEXT," +
                "FOREIGN KEY(id_theLoai) REFERENCES TheLoai(id_theLoai))";
        db.execSQL(createTableSanPham);
        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "id_hoaDon INTERGER PRIMARY KEY AUTOINCREMENT," +
                "id_User INTERGER," +
                "soHoaDon INTERGER," +
                ""
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
