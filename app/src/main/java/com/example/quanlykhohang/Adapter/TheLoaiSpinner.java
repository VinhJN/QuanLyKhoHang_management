package com.example.quanlykhohang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class TheLoaiSpinner extends BaseAdapter {
    Context context;
    ArrayList<TheLoai> list;

    public TheLoaiSpinner(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private static class TheLoaiViewHolder {
        TextView tvIdTheLoai;
        TextView tvTenTheLoai;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TheLoaiViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_theloai_spinner, parent, false);
            viewHolder = new TheLoaiViewHolder();
            viewHolder.tvIdTheLoai = convertView.findViewById(R.id.tvIdTheLoaiSpiner);
            viewHolder.tvTenTheLoai = convertView.findViewById(R.id.tvTenTheLoaiSpiner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TheLoaiViewHolder) convertView.getTag();
        }
        TheLoai theLoai = list.get(position);
        viewHolder.tvIdTheLoai.setText(String.valueOf(theLoai.getId_theLoai()));
        viewHolder.tvTenTheLoai.setText(theLoai.getTenTheLoai());
        return convertView;
    }
}
