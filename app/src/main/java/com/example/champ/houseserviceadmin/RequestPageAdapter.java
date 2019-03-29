package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class RequestPageAdapter extends RecyclerView.Adapter<RequestPageAdapter.MyViewHolder> {

    public List<data_model> models;
    Context context;
    String id,category,desc;
    LayoutInflater layoutInflater;

    public RequestPageAdapter( Context context,List<data_model> models) {
        this.models = models;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username,description;


        public MyViewHolder(View itemView) {
            super(itemView);

            username = (TextView)itemView.findViewById(R.id.txt_username);
            description = (TextView)itemView.findViewById(R.id.txt_desc);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dataitem_user,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        data_model data_model = models.get(position);
        holder.username.setText(data_model.getUsername());
        holder.description.setText(data_model.getDesc());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


}
