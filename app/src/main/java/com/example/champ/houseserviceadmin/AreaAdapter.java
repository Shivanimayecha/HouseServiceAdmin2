package com.example.champ.houseserviceadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Champ on 06-09-2018.
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.MyViewHolder> {

    private List<data_model> models;
    private LayoutInflater layoutInflater;
    List<String>cityname1;
    ArrayList<GetCity> citylist=new ArrayList<GetCity>();
    Context context;
    String URL2 = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Citydata.php";
    EditText areaname;
    Spinner spinner;
    String c_id;
    String id,area,cityname;


    public AreaAdapter(Context context,List<data_model> models) {
        this.models = models;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;

    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {
       TextView areaname,cityname;
       ImageView delete,edit;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            cityname=(TextView)itemView.findViewById(R.id.txt_cityname);
            areaname=(TextView)itemView.findViewById(R.id.txt_areaname);
            delete=(ImageView)itemView.findViewById(R.id.iv_delete);
            edit=(ImageView)itemView.findViewById(R.id.iv_edit);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final data_model data_model = models.get(position);
        holder.cityname.setText(data_model.getCity());
        holder.areaname.setText(data_model.getArea());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   editArea(models.get(position).getId(),models.get(position).getArea());
                id = models.get(position).getId();
                area= models.get(position).getArea();
                cityname=models.get(position).getCity();

                Toast.makeText(context, cityname, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                view = layoutInflater.inflate(R.layout.area_spinnerdialog, null);
                builder.setTitle("Edit");

                areaname = (EditText) view.findViewById(R.id.edt_area);
                areaname.setText(area);

                spinner = (Spinner) view.findViewById(R.id.spinner);


                //         spinner.setOnItemSelectedListener((OnItemSelectedListener) context);

                ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,cityname1);
                spinner.setAdapter(aa);
                //   Toast.makeText(context, cityname1.size(), Toast.LENGTH_SHORT).show();
                Log.e("Size",">>>>>"+cityname1.size());

                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(context, cityname1.get(i).toString(), Toast.LENGTH_SHORT).show();

                        c_id=citylist.get(i).getC_id().toString();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

                    String edit,city;

                    String URLEDIT = "https://shivanimayecha0908.000webhostapp.com/HandyManService/updateArea.php";

                    public void onClick(DialogInterface dialogInterface, int i) {
                        //  Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
                        edit = areaname.getText().toString();

                        if (TextUtils.isEmpty(edit) || TextUtils.isEmpty(c_id))
                        {
                            Toast.makeText(context, "Enter field ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            StringRequest request = new StringRequest(Request.Method.POST, URLEDIT, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.trim().equals("edit"))
                                    {
                                        Toast.makeText(context, "Edit Successfully", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(context, edit, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, AreaPage.class);
                                        context.startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(context, "Unsuccessfull", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, "Error" + error, Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> str = new HashMap<>();
                                    str.put("areaname", edit);
                                    str.put("c_id",c_id);
                                    str.put("a_id", id);
                                    return str;
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
                        dialogInterface.dismiss();
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

               // deleteArea(models.get(position).getId());
                id = models.get(position).getId();

                String URLDELETE = "https://shivanimayecha0908.000webhostapp.com/HandyManService/deleteArea.php";

                StringRequest request = new StringRequest(Request.Method.POST, URLDELETE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("delete")) {
                            Toast.makeText(context, "Delete Succssfully ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, AreaPage.class);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "Not delete", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Error" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> str = new HashMap<>();
                        str.put("a_id", id);
                        return str;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);

            }
        });

        StringRequest request1=new StringRequest(Request.Method.GET, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                cityname1=new ArrayList<>();
                Log.e("Response", ">>>>>" + response);
                try {

                    JSONArray j=new JSONArray(response);
                    for(int i=0;i<j.length();i++)
                    {
                        String city=j.getJSONObject(i).getString("CityName");
                        String id=j.getJSONObject(i).getString("C_id");
                        Log.e("City", ">>>>>" + city);

                        GetCity c=new GetCity();
                        c.setC_id(id);
                        c.setCity(city);
                        cityname1.add(city);
                        citylist.add(c);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(context ,"Error" + e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error" + error.toString(), Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue  queue= Volley.newRequestQueue( context);
        queue.add(request1);




    }




  /*  private void editArea(String id, String area) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        layoutInflater.inflate(R.layout.area_spinnerdialog, null);
        builder.setTitle("Edit");

        areaname = (EditText)

    }*/

          /*  private void deleteArea(final String id) {

                String URLDELETE = "https://shivanimayecha0908.000webhostapp.com/HandyManService/deleteArea.php";

                StringRequest request = new StringRequest(Request.Method.POST, URLDELETE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("delete")) {
                            Toast.makeText(context, "Delete Succssfully ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, AreaPage.class);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "Not delete", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Error" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> str = new HashMap<>();
                        str.put("a_id", id);
                        return str;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);


            }


*/
            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public int getItemCount() {
                return models.size();
            }


        }