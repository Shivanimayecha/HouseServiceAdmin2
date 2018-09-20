package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
 * Created by Champ on 12-09-2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    public List<data_model>models;
    Context context;
    String id,category,desc;
    LayoutInflater layoutInflater;
    EditText cate,des;

    public CategoryAdapter(Context context,List<data_model> models) {
        this.models = models;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryname,description;
        ImageView edit,delete;

        public MyViewHolder(View itemView) {
            super(itemView);

            categoryname = (TextView)itemView.findViewById(R.id.txt_cityname);
            description = (TextView)itemView.findViewById(R.id.txt_areaname);
            delete = (ImageView)itemView.findViewById(R.id.iv_delete);
            edit = (ImageView)itemView.findViewById(R.id.iv_edit);

        }
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        data_model data_model = models.get(position);
        holder.categoryname.setText(data_model.getCategory());
        holder.description.setText("");

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = models.get(position).getId();
                category = models.get(position).getCategory();
                desc = models.get(position).getDesc();

                Toast.makeText(context, "helo", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit Category data");

                view = layoutInflater.inflate(R.layout.categorydialog,null);


                 cate = (EditText)view.findViewById(R.id.category);
                 cate.setText(category);
                 des = (EditText)view.findViewById(R.id.description);
                 des.setText(desc);


                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

                    String EDITURL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/updateCategory.php";
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final String str1 = cate.getText().toString();
                        final String str2 = des.getText().toString();

                        if(TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2))
                        {
                            Toast.makeText(context, "Enter both field", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                         StringRequest request = new StringRequest(Request.Method.POST, EDITURL, new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {

                                 if (response.trim().equals("edit"))
                                 {
                                     Toast.makeText(context, "Edit Succsessfully ", Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(context, CategoryPage.class);
                                     context.startActivity(intent);
                                 }
                                 else
                                 {
                                     Toast.makeText(context, "Unsccsessful to  edit", Toast.LENGTH_SHORT).show();
                                 }
                             }
                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Toast.makeText(context, "Error" +error, Toast.LENGTH_SHORT).show();
                             }
                         }){
                             @Override
                             protected Map<String, String> getParams() throws AuthFailureError {

                                 Map<String,String> stringStringMap = new HashMap<>();
                                 stringStringMap.put("categoryname",str1);
                                 stringStringMap.put("desc",str2);
                                 stringStringMap.put("category_id",id);
                                 return stringStringMap;
                             }
                         };

                         RequestQueue queue = Volley.newRequestQueue(context);
                         queue.add(request);

                        }

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setView(view);
                AlertDialog ad = builder.create();
                ad.show();

            }
        });





        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                id = models.get(position).getId();

                String DELETEURL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/deleteCategory.php";

                StringRequest request = new StringRequest(Request.Method.POST, DELETEURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("delete"))
                        {
                            Toast.makeText(context, "Delete successfully ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,CategoryPage.class);
                            context.startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(context, "Unsuccssesful to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error" +error, Toast.LENGTH_SHORT).show();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String>str = new HashMap<>();
                        str.put("category_id",id);
                        return str;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);

            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }


}