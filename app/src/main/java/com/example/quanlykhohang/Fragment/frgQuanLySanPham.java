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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlykhohang.Adapter.SanPhamAdapter;
import com.example.quanlykhohang.Adapter.TheLoaiSpinner;
import com.example.quanlykhohang.DAO.SanPhamDAO;
import com.example.quanlykhohang.DAO.TheLoaiDAO;
import com.example.quanlykhohang.Interface.ItemClickListener;
import com.example.quanlykhohang.Model.SanPham;
import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class frgQuanLySanPham extends Fragment {
    RecyclerView rcvSanPham;

    SanPhamDAO sanPhamDAO;

    ArrayList<SanPham> list = new ArrayList<>();


    EditText edIdSanPham, edIdTheLoai, edTenSanPham,edSoLuong,edDonGia,edMoTa;
    Spinner spinner_theLoai;

    TheLoaiDAO theLoaiDao;

    int selectedPosition;
    TheLoaiSpinner theLoaiSpinner;

    ArrayList<TheLoai> list_TheLoai = new ArrayList<>();

    int maLoaiSanPham;

    SanPhamAdapter sanPhamAdapter;

    private boolean isChuoi(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }

    private boolean isInteger(String str) {
        return str.matches("[\\d]+");
    }

    private static final String TAG = "frgQuanLySanPham";
    public frgQuanLySanPham() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_quan_ly_san_pham, container, false);
        rcvSanPham = view.findViewById(R.id.rcvSanPham);
        sanPhamDAO = new SanPhamDAO(getContext());
        list = sanPhamDAO.selectAll();
        sanPhamAdapter = new SanPhamAdapter(getContext(), list);
        rcvSanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvSanPham.setAdapter(sanPhamAdapter);
        view.findViewById(R.id.fab_SanPham).setOnClickListener(v -> {
            showAddOrUpdateDialog(getContext(), 0, null);
        });
        sanPhamAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                SanPham sanPham = list.get(position);
                showAddOrUpdateDialog(getContext(), 1, sanPham);
            }
        });
        return view;
    }
    @SuppressLint("MissingInflatedId")
    private void showAddOrUpdateDialog(Context context, int type, SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_san_pham, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        edIdSanPham = view1.findViewById(R.id.edIdSanPham);
        spinner_theLoai = view1.findViewById(R.id.spnTheLoaiDialogSanPham);
        edTenSanPham = view1.findViewById(R.id.edTenSanPham);
        edSoLuong = view1.findViewById(R.id.edSoLuongSanPham);
        edDonGia = view1.findViewById(R.id.edDonGiaSanPham);
        edMoTa = view1.findViewById(R.id.edMoTaSanPham);
        edIdSanPham.setEnabled(false);//vô hiệu hóa Edittext
        //Spinner Thể loại
        theLoaiDao = new TheLoaiDAO(context);
        list_TheLoai = theLoaiDao.selectAll();
        theLoaiSpinner = new TheLoaiSpinner(context, list_TheLoai);
        spinner_theLoai.setAdapter(theLoaiSpinner);

        if (type != 0) {
            edIdSanPham.setText(String.valueOf(sanPham.getId_sanPham()));
            for (int i = 0; i < list_TheLoai.size(); i++) {
                if (sanPham.getId_theLoai() == list_TheLoai.get(i).getId_theLoai()) {
                    selectedPosition = i;
                }
            }
            spinner_theLoai.setSelection(selectedPosition);
            edTenSanPham.setText(sanPham.getTenSanPham());
            edSoLuong.setText(String.valueOf(sanPham.getSoLuong()));
            edDonGia.setText(String.valueOf(sanPham.getDonGia()));
            edMoTa.setText(sanPham.getMoTa());
        }
        spinner_theLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSanPham = list_TheLoai.get(position).getId_theLoai();//khi click spinner gan ma vao maLoaiSach
                Log.e(TAG, "ClickSpinner: " + maLoaiSanPham);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        view1.findViewById(R.id.btnSaveSanPham).setOnClickListener(v -> {
            String tenSanPham = edTenSanPham.getText().toString();
            String soLuong = edSoLuong.getText().toString();
            String donGia = edDonGia.getText().toString();
            String moTa = edMoTa.getText().toString();
            if (validate(tenSanPham, soLuong,donGia,moTa)) {
                if (type == 0) {
                    SanPham sanPhamNew = new SanPham();
                    sanPhamNew.setId_theLoai(maLoaiSanPham);
                    sanPhamNew.setTenSanPham(tenSanPham);
                    sanPhamNew.setSoLuong(Integer.parseInt(soLuong));
                    sanPhamNew.setDonGia(Integer.parseInt(donGia));
                    sanPhamNew.setMoTa(moTa);
                    try {
                        if (sanPhamDAO.insert(sanPhamNew)) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            list.add(sanPhamNew);
                            sanPhamAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context,"Thêm không thành công", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi Database: ", e);
                        Toast.makeText(context, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    sanPham.setId_theLoai(maLoaiSanPham);
                    Log.e(TAG, "showAddOrUpdateDialog: " + maLoaiSanPham);
                    sanPham.setTenSanPham(tenSanPham);
                    sanPham.setSoLuong(Integer.parseInt(soLuong));
                    sanPham.setDonGia(Integer.parseInt(donGia));
                    sanPham.setMoTa(moTa);

                    try {
                        if (sanPhamDAO.update(sanPham)) {
                            Toast.makeText(context,"Sửa thành công", Toast.LENGTH_SHORT).show();
                            updateList();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi Database: ", e);
                        Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        view1.findViewById(R.id.btnCancelSanPham).setOnClickListener(v -> {
            clearFrom();
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean validate(String tenSanPham, String soLuong,String donGia, String moTa) {
        try {
            if (tenSanPham.isEmpty() || soLuong.isEmpty()||donGia.isEmpty()||moTa.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng cung cấp đủ thông tin", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Xẩy ra lỗi", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void updateList() {
        list.clear();
        list.addAll(sanPhamDAO.selectAll());
        sanPhamAdapter.notifyDataSetChanged();
    }

    private void clearFrom() {
        edIdSanPham.setText("");
        edIdTheLoai.setText("");
        edTenSanPham.setText("");
        edSoLuong.setText("");
        edDonGia.setText("");
        edMoTa.setText("");
    }
}