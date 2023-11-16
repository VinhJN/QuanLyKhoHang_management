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
                "id_User INTERGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "fullname TEXT NOT NULL," +
                "email TEXT NOT NULL)";
        db.execSQL(createTableUser);
        String createTableTheLoai = "CREATE TABLE TheLoai (" +
                "id_theLoai INTERGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name TEXT NOT NULL)";
        db.execSQL(createTableTheLoai);
        String createTableSanPham = "CREATE TABLE SanPham (" +
                "id_sanPham INTERGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id_theLoai INTERGER NOT NULL," +
                "tenSanPham TEXT NOT NULL," +
                "soLuong INTERGER NOT NULL," +
                "moTa TEXT NOT NULL," +
                "FOREIGN KEY(id_theLoai) REFERENCES TheLoai(id_theLoai))";
        db.execSQL(createTableSanPham);
        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "id_hoaDon INTERGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id_User INTERGER NOT NULL," +
                "soHoaDon INTERGER NOT NULL," +
                "loaiHoaDon TEXT NOT NULL," +
                "ngayThang TEXT NOT NULL," +
                "id_sanPham INTERGER NOT NULL," +
                "FOREIGN KEY(id_sanPham) REFERENCES SanPham(id_sanPham))";
        db.execSQL(createTableHoaDon);
        String createTableChiTietHoaDon = "CREATE TABLE ChiTietHoaDon (" +
                "id_chiTietHoaDon PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "soHoaDon INTERGER NOT NULL," +
                "soLuong INTERGER NOT NULL," +
                "donGia REAL NOT NULL," +
                "FOREIGN KEY(soHoaDon) REFERENCES HoaDon(soHoaDon))";
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
