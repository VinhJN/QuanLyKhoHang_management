package com.example.quanlykhohang.Model;

public class HoaDon {
    private int id_hoaDon;
    private String id_User;
    private int id_SanPham;
    private int soHoaDon;
    private String loaiHoaDon;
    private String ngay;

    public HoaDon() {
    }

    public HoaDon(int id_hoaDon, String id_User, int id_SanPham, int soHoaDon, String loaiHoaDon, String ngay) {
        this.id_hoaDon = id_hoaDon;
        this.id_User = id_User;
        this.id_SanPham = id_SanPham;
        this.soHoaDon = soHoaDon;
        this.loaiHoaDon = loaiHoaDon;
        this.ngay = ngay;
    }

    public int getId_hoaDon() {
        return id_hoaDon;
    }

    public void setId_hoaDon(int id_hoaDon) {
        this.id_hoaDon = id_hoaDon;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public int getId_SanPham() {
        return id_SanPham;
    }

    public void setId_SanPham(int id_SanPham) {
        this.id_SanPham = id_SanPham;
    }

    public int getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(int soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
