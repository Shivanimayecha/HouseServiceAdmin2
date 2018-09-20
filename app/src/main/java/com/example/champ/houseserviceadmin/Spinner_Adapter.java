package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Champ on 07-09-2018.
 */

class Spinner_Adapter extends BaseAdapter {
    
    Context context;
    List<data_model>data_models;
    LayoutInflater inflater;

    public Spinner_Adapter(Context context, List<data_model> data_models) {
        this.context=context;
        this.data_models=data_models;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data_models.size();
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
        view=inflater.inflate(R.layout.textspinner,null);
        TextView textView=(TextView)view.findViewById(R.id.txt);

        textView.setText(data_models.get(i).getCity());
        Log.d("City Tags",data_models.get(i).getCity());


        return view;
    }
}
