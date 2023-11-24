package com.example.quanlykhohang.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhohang.DAO.SanPhamDAO;
import com.example.quanlykhohang.Interface.ItemClickListener;
import com.example.quanlykhohang.Model.SanPham;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {
    Context context;
    ArrayList<SanPham> list;

    private ItemClickListener itemClickListener;

    SanPhamDAO sanPhamDAO;

    private static final String TAG = "SanPhamAdapter";

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }


    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        sanPhamDAO = new SanPhamDAO(context);
    }
    @NonNull
    @Override
    public SanPhamAdapter.SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
        return new SanPhamAdapter.SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.SanPhamViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        if (sanPham != null) {
            holder.tvIdSanPham.setText("Mã sản phẩm: "+sanPham.getId_sanPham());
            holder.tvIdTheLoai.setText("Mã loại sản phẩm: " + sanPham.getId_theLoai());
            holder.tvTenSanPham.setText("Tên sản phẩm: " + sanPham.getTenSanPham());
            holder.tvSoLuong.setText("Số lượng: " + sanPham.getSoLuong());
            holder.tvDonGia.setText("Đơn giá: " + sanPham.getDonGia());
            holder.tvMoTa.setText("Mô tả: " + sanPham.getMoTa());
        }
        holder.imgDelete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }
    public void showDeleteDialog(int position) {
        SanPham sanPham = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + sanPham.getTenSanPham() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (sanPhamDAO.delete(sanPham)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(sanPham);
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context,"Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdSanPham, tvIdTheLoai, tvTenSanPham, tvSoLuong, tvDonGia, tvMoTa;
        ImageView imgDelete;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdSanPham = itemView.findViewById(R.id.tvIdSanPham);
            tvIdTheLoai = itemView.findViewById(R.id.tvIdTheLoaiSanPham);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuongSanPham);
            tvDonGia = itemView.findViewById(R.id.tvDonGiaSanPham);
            tvMoTa = itemView.findViewById(R.id.tvMoTaSanPham);
            imgDelete = itemView.findViewById(R.id.imgDeleteSanPham);
        }
    }
}
