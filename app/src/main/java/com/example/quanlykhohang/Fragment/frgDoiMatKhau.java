package com.example.quanlykhohang.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlykhohang.DAO.UserDAO;
import com.example.quanlykhohang.Model.User;
import com.example.quanlykhohang.R;

public class frgDoiMatKhau extends Fragment {
    EditText edPassWordOld, edPassWordNew, edConfirmPassWord;
    private boolean isChuoi(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }
    SharedPreferences sharedPreferences;
    UserDAO userDAO;

    public frgDoiMatKhau() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_doi_mat_khau, container, false);
        edPassWordOld = view.findViewById(R.id.edOldPassWord);
        edPassWordNew = view.findViewById(R.id.edNewPassWord);
        edConfirmPassWord = view.findViewById(R.id.edRePassWord);
        userDAO = new UserDAO(getContext());
        sharedPreferences = getContext().getSharedPreferences("LIST_USER", Context.MODE_PRIVATE);
        view.findViewById(R.id.btnSaveChage).setOnClickListener(v -> {
            String passWordOld = edPassWordOld.getText().toString().trim();
            String passWordNew = edPassWordNew.getText().toString().trim();
            String RePassWord = edConfirmPassWord.getText().toString().trim();
            if (validate(passWordOld, passWordNew, RePassWord)) {
                String userName = sharedPreferences.getString("USERNAME", "");

                User user = userDAO.SelectID(userName);
                user.setPassword(passWordNew);
                if (userDAO.updatePass(user)) {
                    Toast.makeText(getContext(), "Thay đổi thành công ", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("PASSWORD", passWordNew);
                    editor.apply();
                    clearFrom();
                } else {
                    Toast.makeText(getContext(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.btnCancelChage).setOnClickListener(v -> {
            clearFrom();
        });
        return view;
    }
    private void clearFrom() {
        edPassWordOld.setText("");
        edPassWordNew.setText("");
        edConfirmPassWord.setText("");
    }

    private boolean validate(String passWordOld, String passWordNew, String rePassWord) {
        if (passWordOld.isEmpty() || passWordNew.isEmpty() || rePassWord.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isChuoi(passWordOld) || !isChuoi(passWordNew) || !isChuoi(rePassWord)) {
            Toast.makeText(getContext(), "Nhập sai định dạng", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String pass_Old = sharedPreferences.getString("PASSWORD", "");
            if (!passWordOld.equals(pass_Old)) {
                Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!passWordNew.equals(rePassWord)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}