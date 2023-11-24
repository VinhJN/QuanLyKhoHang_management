package com.example.quanlykhohang.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykhohang.Database.DbHelper;
import com.example.quanlykhohang.Model.SanPham;

import java.util.ArrayList;

public class SanPhamDAO {
    DbHelper dbHelper;

    private static final String TABLE_NAME = "SanPham";
    private static final String COLUMN_ID_SAN_PHAM = "id_sanPham";
    private static final String COLUMN_ID_THE_LOAI = "id_theLoai";
    private static final String COLUMN_TEN_SAN_PHAM = "tenSanPham";
    private static final String COLUMN_SO_LUONG = "soLuong";
    private static final String COLUMN_DON_GIA = "donGia";
    private static final String COLUMN_MO_TA = "moTa";

    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insert(SanPham obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_THE_LOAI, obj.getId_theLoai());
        contentValues.put(COLUMN_TEN_SAN_PHAM, obj.getTenSanPham());
        contentValues.put(COLUMN_SO_LUONG, obj.getSoLuong());
        contentValues.put(COLUMN_DON_GIA, obj.getDonGia());
        contentValues.put(COLUMN_MO_TA, obj.getMoTa());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        obj.setId_sanPham((int) check);
        return check != -1;
    }

    public boolean delete(SanPham obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_sanPham())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID_SAN_PHAM + "=?", dk);
        return check != -1;
    }

    public boolean update(SanPham obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_sanPham())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_THE_LOAI, obj.getId_theLoai());
        contentValues.put(COLUMN_TEN_SAN_PHAM, obj.getTenSanPham());
        contentValues.put(COLUMN_SO_LUONG, obj.getSoLuong());
        contentValues.put(COLUMN_DON_GIA, obj.getDonGia());
        contentValues.put(COLUMN_MO_TA, obj.getMoTa());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID_SAN_PHAM + "=?", dk);
        return check != -1;
    }

    private ArrayList<SanPham> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<SanPham> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id_sanPham = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_SAN_PHAM));
                int id_theLoai = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_THE_LOAI));
                String tenSanPham = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_SAN_PHAM));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_LUONG));
                int donGia = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DON_GIA));
                String moTa = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MO_TA));
                list.add(new SanPham(id_sanPham, id_theLoai, tenSanPham, soLuong, donGia, moTa));
            }
        }
        return list;
    }

    public ArrayList<SanPham> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public SanPham selectID(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID_SAN_PHAM + " = ?";
        ArrayList<SanPham> list = getAll(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
