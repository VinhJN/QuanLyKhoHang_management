package com.example.quanlykhohang.Model;

public class SanPham {
    private int id_sanPham,id_theLoai;
    private String tenSanPham;
    private int soLuong;
    private int donGia;
    private String moTa;

    public SanPham() {
    }

    public SanPham(int id_sanPham, int id_theLoai, String tenSanPham, int soLuong, int donGia, String moTa) {
        this.id_sanPham = id_sanPham;
        this.id_theLoai = id_theLoai;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.moTa = moTa;
    }

    public int getId_sanPham() {
        return id_sanPham;
    }

    public void setId_sanPham(int id_sanPham) {
        this.id_sanPham = id_sanPham;
    }

    public int getId_theLoai() {
        return id_theLoai;
    }

    public void setId_theLoai(int id_theLoai) {
        this.id_theLoai = id_theLoai;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
