package com.example.quanlykhohang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlykhohang.DAO.UserDAO;
import com.example.quanlykhohang.Model.User;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText edUserName, edPassWord;
    CheckBox chkRememberPass;
    UserDAO userDAO;
    String strUserName, strPassWord;
    Spinner spinner_role;
    String value_role;
    int role_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserName = findViewById(R.id.edUsername);
        edPassWord = findViewById(R.id.edPassword);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        spinner_role = findViewById(R.id.spinner_role);
        userDAO = new UserDAO(this);
        ArrayList<String> list = new ArrayList<>();
        list.add("Admin");
        list.add("Thành Viên");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner_role.setAdapter(adapter);
        ReadFile();
        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role_position = position;
                value_role = list.get(position);
                Toast.makeText(Login.this,role_position + value_role, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            CheckLogin();
        });
    }

    private void ReadFile() {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
        edUserName.setText(sharedPreferences.getString("USERNAME", ""));
        edPassWord.setText(sharedPreferences.getString("PASSWORD", ""));
        chkRememberPass.setChecked(sharedPreferences.getBoolean("REMEMBER", false));
        spinner_role.setSelection(sharedPreferences.getInt("ROLE", 0));
    }

    private void CheckLogin() {
        strUserName = edUserName.getText().toString().trim();
        strPassWord = edPassWord.getText().toString().trim();
        if (strUserName.isEmpty() || strPassWord.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            if (userDAO.checkLogin(strUserName, strPassWord, String.valueOf(role_position))) {
                User user = userDAO.SelectID(strUserName);
                if (user.getRole() == 0) {
                    value_role = "admin";
                } else {
                    value_role = "Thành Viên";
                }
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUserName, strPassWord, chkRememberPass.isChecked(), role_position);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("role", value_role);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void rememberUser(String strUserName, String strPassWord, boolean checked, int role_position) {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!checked) {
            editor.clear();
        } else {
            editor.putString("USERNAME", strUserName);
            editor.putString("PASSWORD", strPassWord);
            editor.putBoolean("REMEMBER", checked);
            editor.putInt("ROLE", role_position);
        }
        editor.commit();
    }
}
