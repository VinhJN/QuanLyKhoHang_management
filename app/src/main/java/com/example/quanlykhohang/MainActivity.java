package com.example.quanlykhohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlykhohang.DAO.UserDAO;
import com.example.quanlykhohang.Fragment.Top10NhapKho;
import com.example.quanlykhohang.Fragment.Top10XuatKho;
import com.example.quanlykhohang.Fragment.frgDoanhThu;
import com.example.quanlykhohang.Fragment.frgDoiMatKhau;
import com.example.quanlykhohang.Fragment.frgQuanLyHoaDon;
import com.example.quanlykhohang.Fragment.frgQuanLyHoaDonChiTiet;
import com.example.quanlykhohang.Fragment.frgQuanLySanPham;
import com.example.quanlykhohang.Fragment.frgQuanLyThanhVien;
import com.example.quanlykhohang.Fragment.frgQuanLyTheLoai;
import com.example.quanlykhohang.Fragment.frgThemNguoiDung;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;

    UserDAO userDAO;

    TextView tvUser;

    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);toolbar = findViewById(R.id.Toolbar_Main);
        userDAO = new UserDAO(MainActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản Lý Kho Hàng");
        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.NavigationView);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new frgQuanLyTheLoai()).commit();
        Intent intent = getIntent();
        role = intent.getStringExtra("role");
        ReadFile(role);
        tvUser = navigationView.getHeaderView(0).findViewById(R.id.tv_HeaderTextView);
        tvUser.setText("WelCome " + role);
        if (role != null) {
            if (!role.equalsIgnoreCase("admin")) {
                navigationView.getMenu().findItem(R.id.menu_them_nguoi_dung).setVisible(false);
            }
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                showMenu(itemId);
                return true;
            }
        });
    }

    private void ReadFile(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_use", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username_user", username);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        showMenu(itemId);
        return true;
    }

    private boolean showMenu(int itemId) {
        Fragment fragment = null;
        String title = "";
        try {
            if (itemId == R.id.menu_ql_the_loai) {
                fragment = new frgQuanLyTheLoai();
                title = "Quản lý thể loại";
            } else if (itemId == R.id.menu_ql_San_Pham) {
                fragment = new frgQuanLySanPham();
                title = "Quản lý sản phẩm";
            } else if (itemId == R.id.menu_ql_hoa_don) {
                fragment = new frgQuanLyHoaDon();
                title = "Quản lý hóa đơn";
            } else if (itemId == R.id.menu_ql_hoaDonChiTiet) {
                fragment = new frgQuanLyHoaDonChiTiet();
                title = "Quản lý hóa đơn chi tiết";
            } else if (itemId == R.id.menu_ql_thanhVien) {
                fragment = new frgQuanLyThanhVien();
                title = "Quản lý thành viên";
            } else if (itemId == R.id.menu_tk_DoanhThu) {
                fragment = new frgDoanhThu();
                title = "Thống kê doanh thu";
            } else if (itemId == R.id.menu_tk_top10_NhapKho) {
                fragment = new Top10NhapKho();
                title = "Thống kê Top 10 nhập kho";
            } else if (itemId == R.id.menu_tk_top10_XuatKho) {
                fragment = new Top10XuatKho();
                title = "Thống kê Top 10 xuất kho";
            }
            else if (itemId == R.id.menu_them_nguoi_dung) {
                if(!role.equalsIgnoreCase("admin")){
                    Toast.makeText(this, "Không đủ quyền để sự dụng chức năng", Toast.LENGTH_SHORT).show();
                }else{
                    fragment = new frgThemNguoiDung();
                    title = "Thêm người dùng";
                }
            } else if (itemId == R.id.menu_doi_mat_khau) {
                fragment = new frgDoiMatKhau();
                title = "Đổi mật khẩu";
            } else if (itemId == R.id.menu_dang_Xuat) {
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
                return true;
            }
            if (fragment != null) {
                drawerLayout.close();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                getSupportActionBar().setTitle(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}