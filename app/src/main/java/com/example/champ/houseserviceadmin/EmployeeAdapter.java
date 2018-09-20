package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Champ on 20-09-2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {


    public List<data_model>models;
    Context context;
    LayoutInflater layoutInflater;

    public EmployeeAdapter(Context context,List<data_model> models ) {
        this.models = models;
        this.context = context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView firstname,lastname;

        public MyViewHolder(View itemView) {
            super(itemView);

            firstname=(TextView)itemView.findViewById(R.id.txt_cityname);
            lastname=(TextView)itemView.findViewById(R.id.txt_areaname);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        data_model data_model=models.get(position);
        holder.firstname.setText(data_model.getFirstname());
        holder.lastname.setText(data_model.getLastname());

    }


    @Override
    public int getItemCount() {
        return models.size();
    }


}
