package com.example.quanlykhohang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlykhohang.Adapter.ThanhVienAdapter;
import com.example.quanlykhohang.Adapter.TheLoaiAdapter;
import com.example.quanlykhohang.DAO.UserDAO;
import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.Model.User;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class frgQuanLyThanhVien extends Fragment {
    RecyclerView rcvThanhVien;
    UserDAO userDAO;
    ArrayList<User> list = new ArrayList<>();

    public static final String TAG = "frgQuanLyThanhVien";
    ThanhVienAdapter thanhVienAdapter;
    public frgQuanLyThanhVien() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_quan_ly_thanh_vien, container, false);
        rcvThanhVien = view.findViewById(R.id.rcvThanhVien);
        userDAO = new UserDAO(getContext());
        list = userDAO.SelectAll();
        thanhVienAdapter = new ThanhVienAdapter(getContext(), list);
        rcvThanhVien.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvThanhVien.setAdapter(thanhVienAdapter);
        return view;
    }
}