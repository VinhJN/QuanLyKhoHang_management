package com.example.quanlykhohang.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlykhohang.Adapter.TheLoaiAdapter;
import com.example.quanlykhohang.DAO.TheLoaiDAO;
import com.example.quanlykhohang.Interface.ItemClickListener;
import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class frgQuanLyTheLoai extends Fragment {
    RecyclerView rcvTheLoai;

    TheLoaiDAO theLoaiDao;

    EditText edIdTheLoai, edTenTheLoai;


    ArrayList<TheLoai> list = new ArrayList<>();

    public static final String TAG = "frgQuanLyTheLoai";
    TheLoaiAdapter theLoaiAdapter;

    public boolean isChuoi(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }
    public frgQuanLyTheLoai() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_quan_ly_the_loai, container, false);
        rcvTheLoai = view.findViewById(R.id.rcvTheLoai);
        theLoaiDao = new TheLoaiDAO(getContext());
        list = theLoaiDao.selectAll();
        theLoaiAdapter = new TheLoaiAdapter(getContext(), list);
        rcvTheLoai.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvTheLoai.setAdapter(theLoaiAdapter);
        view.findViewById(R.id.fab_TheLoai).setOnClickListener(v -> {
            showAddOrEditDialog_Tl(getContext(), 0, null);
        });
        theLoaiAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                TheLoai theLoai = list.get(position);
                showAddOrEditDialog_Tl(getContext(), 1, theLoai);
            }
        });
        return view;
    }
    @SuppressLint("MissingInflatedId")
    protected void showAddOrEditDialog_Tl(Context context, int type, TheLoai theLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_theloai, null);
        builder.setView(mView);
        AlertDialog alertDialog = builder.create();
        edIdTheLoai = mView.findViewById(R.id.edIdTheLoai);
        edTenTheLoai = mView.findViewById(R.id.edTenTheLoai);
        edIdTheLoai.setEnabled(false);

        if (type != 0) {
            edIdTheLoai.setText(String.valueOf(theLoai.getId_theLoai()));
            edTenTheLoai.setText(String.valueOf(theLoai.getTenTheLoai()));
        }
        mView.findViewById(R.id.btnCancelTheLoai).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        mView.findViewById(R.id.btnSaveTheLoai).setOnClickListener(v -> {
            String tenLoai = edTenTheLoai.getText().toString();
            if (tenLoai.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (!isChuoi(tenLoai)) {
                Toast.makeText(context, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
            } else {
                if (type == 0) {
                    TheLoai theLoainew = new TheLoai();
                    theLoainew.setTenTheLoai(tenLoai);
                    try {
                        if (theLoaiDao.insert(theLoainew)) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            list.add(theLoainew);
                            theLoaiAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context,"Thêm không thành công", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi khi thao tác với cơ sở dữ liệu", e);
                        Toast.makeText(context, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    theLoai.setTenTheLoai(tenLoai);
                    try {
                        if (theLoaiDao.update(theLoai)) {
                            Toast.makeText(context,"Sửa thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            update();
                        } else {
                            Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi khi thao tác với cơ sở dữ liệu", e);
                        Toast.makeText(context,"Sửa không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void update() {
        list.clear();
        list.addAll(theLoaiDao.selectAll());
        theLoaiAdapter.notifyDataSetChanged();
    }
}