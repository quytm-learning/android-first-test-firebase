package com.epay.tmq.firsttestfb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.epay.tmq.firsttestfb.MainActivity;
import com.epay.tmq.firsttestfb.R;
import com.epay.tmq.firsttestfb.models.User;

import java.util.ArrayList;

/**
 * Created by tmq on 11/01/2017.
 */

public class ListDataFBAdapter extends BaseAdapter {
    private ArrayList<User> arrData;
    private Context mContext;
    private LayoutInflater lf;

    public ListDataFBAdapter(Context context){
        this.mContext = context;
        lf = LayoutInflater.from(context);

        arrData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return arrData.size();
    }

    @Override
    public User getItem(int position) {
        return arrData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = lf.inflate(R.layout.item_data_fb, null);
        }

        TextView tvId = (TextView) convertView.findViewById(R.id.tv_id);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tv_username);
        TextView tvEmail = (TextView) convertView.findViewById(R.id.tv_email);
        ImageView btnDelete = (ImageView) convertView.findViewById(R.id.btn_delete_item);

        final User user = arrData.get(position);
        tvId.setText(user.uid);
        tvUsername.setText(user.username);
        tvEmail.setText(user.email);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) mContext;
                activity.deleteUser(user);
                arrData.remove(position);
            }
        });

        return convertView;
    }

    public void addItem(User item){
        arrData.add(item);
    }
}
