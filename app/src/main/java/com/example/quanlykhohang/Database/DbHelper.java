package com.example.quanlykhohang.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuanLyKhoHang";
    private static final int DATABASE_VERSION = 1; // Change the version to reflect the current version

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUser = "CREATE TABLE User (" +
                "id_User INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "fullname TEXT NOT NULL," +
                "email TEXT NOT NULL)";
        db.execSQL(createTableUser);

        String createTableTheLoai = "CREATE TABLE TheLoai (" +
                "id_theLoai INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name TEXT NOT NULL)";
        db.execSQL(createTableTheLoai);

        String createTableSanPham = "CREATE TABLE SanPham (" +
                "id_sanPham INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id_theLoai INTEGER NOT NULL," +
                "tenSanPham TEXT NOT NULL," +
                "soLuong INTEGER NOT NULL," +
                "moTa TEXT NOT NULL," +
                "FOREIGN KEY(id_theLoai) REFERENCES TheLoai(id_theLoai))";
        db.execSQL(createTableSanPham);

        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "id_hoaDon INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id_User INTEGER NOT NULL," +
                "soHoaDon INTEGER NOT NULL," +
                "loaiHoaDon TEXT NOT NULL," +
                "ngayThang TEXT NOT NULL," +
                "id_sanPham INTEGER NOT NULL," +
                "FOREIGN KEY(id_sanPham) REFERENCES SanPham(id_sanPham))";
        db.execSQL(createTableHoaDon);

        String createTableChiTietHoaDon = "CREATE TABLE ChiTietHoaDon (" +
                "id_chiTietHoaDon INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "soHoaDon INTEGER NOT NULL," +
                "soLuong INTEGER NOT NULL," +
                "donGia REAL NOT NULL," +
                "FOREIGN KEY(soHoaDon) REFERENCES HoaDon(id_hoaDon))";
        db.execSQL(createTableChiTietHoaDon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS User");
            db.execSQL("DROP TABLE IF EXISTS TheLoai");
            db.execSQL("DROP TABLE IF EXISTS SanPham");
            db.execSQL("DROP TABLE IF EXISTS HoaDon");
            db.execSQL("DROP TABLE IF EXISTS ChiTietHoaDon");
            onCreate(db); // Recreate the tables after dropping them
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
