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

import com.example.quanlykhohang.DAO.UserDAO;
import com.example.quanlykhohang.Interface.ItemClickListener;
import com.example.quanlykhohang.Model.User;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    Context context;
    ArrayList<User> list;

    private ItemClickListener itemClickListener;

    UserDAO userDAO;

    private static final String TAG = "ThanhVienAdapter";

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }


    public ThanhVienAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        userDAO = new UserDAO(context);
    }
    @NonNull
    @Override
    public ThanhVienAdapter.ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanh_vien, parent, false);
        return new ThanhVienAdapter.ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienAdapter.ThanhVienViewHolder holder, int position) {
        User user = list.get(position);
        if (user != null) {
            holder.tvUsername.setText("Tài khoản thành viên :" + user.getUser());
            holder.tvFullName.setText("Tên TV :" + user.getFullname());
            holder.tvEmail.setText("Email :" + user.getEmail());
            holder.tvPassword.setText("Password: " + user.getUser());
        }
        holder.imgDelete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            if(itemClickListener != null){
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }
    public void showDeleteDialog(int position) {
        User user = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa thành viên " + user.getUser() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (userDAO.delete(user)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(user);
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
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

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvFullName, tvEmail, tvPassword;
        ImageView imgDelete;
        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvFullName = itemView.findViewById(R.id.tvFullnameTV);
            tvEmail = itemView.findViewById(R.id.tvEmailTV);
            tvPassword = itemView.findViewById(R.id.tvPasswordTV);
            imgDelete = itemView.findViewById(R.id.imgDeleteTV);
        }
    }
}
