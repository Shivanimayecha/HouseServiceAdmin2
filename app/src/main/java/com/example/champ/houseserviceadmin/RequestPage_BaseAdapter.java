package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RequestPage_BaseAdapter extends BaseAdapter {

    Context context;
    List<data_model> list;
    LayoutInflater inflater;

    public RequestPage_BaseAdapter(Context context, List<data_model> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view=inflater.inflate(R.layout.dataitem_user,null);
        TextView username=(TextView)view.findViewById(R.id.txt_username);
        TextView description=(TextView)view.findViewById(R.id.txt_desc);

        data_model model=list.get(i);
        username.setText(model.getUsername());
        description.setText(model.getDesc());

        return view;
    }
}
