package com.example.quanlykhohang.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhohang.DAO.TheLoaiDAO;
import com.example.quanlykhohang.Interface.ItemClickListener;
import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.TheLoaiViewHolder> {
    Context context;
    ArrayList<TheLoai> list;

    private ItemClickListener itemClickListener;

    TheLoaiDAO theLoaiDao;

    private static final String TAG = "TheLoai_Adapter";

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }


    public TheLoaiAdapter(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
        theLoaiDao = new TheLoaiDAO(context);
    }
    @NonNull
    @Override
    public TheLoaiAdapter.TheLoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theloai, parent, false);
        return new TheLoaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiAdapter.TheLoaiViewHolder holder, int position) {
        holder.itemView.setOnLongClickListener(v -> {
            try {
                if (itemClickListener != null) {
                    itemClickListener.UpdateItem(position);
                }
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: " + e);
            }
            return false;
        });
        holder.tvIdTheLoai.setText("Mã loại :" + list.get(position).getId_theLoai());
        holder.tvTenTheLoai.setText("Tên loại :" + list.get(position).getTenTheLoai());
        holder.imgDelete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
    }
    public void showDeleteDialog(int position) {
        TheLoai theLoai = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + theLoai.getTenTheLoai() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (theLoaiDao.delete(theLoai)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(theLoai);
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context,"Xóa không thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
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

    public class TheLoaiViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdTheLoai, tvTenTheLoai;
        ImageView imgDelete;
        public TheLoaiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdTheLoai = itemView.findViewById(R.id.tvIdTheLoai);
            tvTenTheLoai = itemView.findViewById(R.id.tvTenTheLoai);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
