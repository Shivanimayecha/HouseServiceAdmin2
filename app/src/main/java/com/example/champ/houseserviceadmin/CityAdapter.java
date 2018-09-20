package com.example.champ.houseserviceadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Champ on 13-08-2018.
 */

class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {

    public List<data_model> models;
    Context context;


    public CityAdapter(Context context, List<data_model> models) {
        this.models = models;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView cityname;
        ImageView delete,edit;

        public MyViewHolder(View itemView) {
            super(itemView);
            cityname=(TextView)itemView.findViewById(R.id.txt_cityname);
            delete=(ImageView)itemView.findViewById(R.id.iv_delete);
            edit=(ImageView)itemView.findViewById(R.id.iv_edit);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        data_model data_model=models.get(position);
        holder.cityname.setText(data_model.getCity());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                deleteFromServer(models.get(position).getId());
                
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editFromServer(models.get(position).getId(),models.get(position).getCity());
              //city=models.get(position).getCity().toString();
            }
        });

    }



    private void editFromServer(final String id,String city) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Edit City");
        builder.setMessage("Enter new city for edit");


        final EditText editCity=new EditText(context);
        builder.setView(editCity);
        editCity.setText(city);
       // editCity.setText((CharSequence) cityname);
        //String string=cityname.getText().toString();
       // editCity.setText(string);

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

            String URLEDIT="https://shivanimayecha0908.000webhostapp.com/HandyManService/updateCity.php";


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                final String txt=editCity.getText().toString();

                if(TextUtils.isEmpty(txt))
                {
                    Toast.makeText(context, "Enter City Name", Toast.LENGTH_LONG).show();
                }
                else
                {
                    StringRequest request=new StringRequest(Request.Method.POST, URLEDIT, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.trim().equals("edit"))
                            {
                                Toast.makeText(context, "City updated", Toast.LENGTH_SHORT).show();
                                Toast.makeText(context,txt, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(context,CityPage.class);
                                context.startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(context,"City not update",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error" + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>str=new HashMap<>();
                            str.put("city",txt);
                            str.put("c_id",String.valueOf(id));
                            return str;
                        }
                    };

                    RequestQueue queue=Volley.newRequestQueue(context);
                    queue.add(request);
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog ad=builder.create();
        ad.show();
    }

    private void deleteFromServer(final String id) {

        String URL2="https://shivanimayecha0908.000webhostapp.com/HandyManService/deleteCity.php";

        StringRequest request1=new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equals("delete"))
                {
                    Toast.makeText(context, "Delete successfully ", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,CityPage.class);
                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, "unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error" + error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>str=new HashMap<>();
                str.put("c_id", String.valueOf(id));
                return str;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request1);
    }



    @Override
    public int getItemCount() {
        return models.size();
    }
}
