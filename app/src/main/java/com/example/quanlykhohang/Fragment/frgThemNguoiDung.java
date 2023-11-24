package com.example.quanlykhohang.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlykhohang.DAO.UserDAO;
import com.example.quanlykhohang.Model.User;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class frgThemNguoiDung extends Fragment {
    EditText edUser,edFullName, edEmail, edPassWord;
    UserDAO userDAO;
    Spinner spinner;
    ArrayList<String> list = new ArrayList<String>();
    String valueRole;
    int rolePosition;

    public frgThemNguoiDung() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_them_nguoi_dung, container, false);
        userDAO = new UserDAO(getContext());
        edUser = view.findViewById(R.id.edUserNameAdd);
        edPassWord = view.findViewById(R.id.edPassWordAdd);
        edFullName = view.findViewById(R.id.edHoTenAdd);
        edEmail = view.findViewById(R.id.edEmailAdd);

        spinner = view.findViewById(R.id.spinner_role_ThemNguoiDung);
        list.add("Admin");
        list.add("Thành Viên");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rolePosition = position;
                valueRole = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        view.findViewById(R.id.btnSaveAddThanhVien).setOnClickListener(v -> {
            String username = edUser.getText().toString().trim();
            String password = edPassWord.getText().toString().trim();
            String hoTen = edFullName.getText().toString().trim();
            String email = edEmail.getText().toString().trim();
            if (validate(username, password, hoTen,email)) {
                User user = new User(username,password, hoTen,email, rolePosition);
                if (userDAO.insertData(user)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    resetEditText();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.btnCancelAddThanhVien).setOnClickListener(v -> {
            resetEditText();
        });
        return view;
    }
    private boolean validate(String username, String password, String hoTen, String email) {
        if (username.isEmpty() || password.isEmpty() || hoTen.isEmpty()||email.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng cung cấp đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Email validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            Toast.makeText(getContext(), "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void resetEditText() {
        edUser.setText("");
        edPassWord.setText("");
        edFullName.setText("");
        edEmail.setText("");

    }
}