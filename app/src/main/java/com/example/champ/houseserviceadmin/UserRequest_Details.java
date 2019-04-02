package com.example.champ.houseserviceadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class UserRequest_Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<data_model> models;
    TextView uname,description,address;
    String na,des,add;
    String w_id;
    Spinner spinner;
    String id;
    Button workerallo;
    String UPDATEWORKER_URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/updateWorker.php";
    String WORKERDATA_URL="https://shivanimayecha0908.000webhostapp.com/HandyManService/get_workerdata.php";
    List<String> workername=new ArrayList<>();
    ArrayList<data_model>workerlist=new ArrayList<data_model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request__details);

        spinner = (Spinner)findViewById(R.id.spinner_wrkr) ;
        workerallo = (Button)findViewById(R.id.btn_wrkrAllo);

        na = getIntent().getStringExtra("name");
        des = getIntent().getStringExtra("description");
        add = getIntent().getStringExtra("address");


        uname=findViewById(R.id.uname);
        description=findViewById(R.id.desc);
        address=findViewById(R.id.add);

        uname.setText(na);
        description.setText(des);
        address.setText(add);

        //Bind worker
        StringRequest request1=new StringRequest(Request.Method.GET,WORKERDATA_URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response", ">>>>>" + response);
                try {

                    JSONArray j=new JSONArray(response);
                    for(int i=0;i<j.length();i++)
                    {
                        String name=j.getJSONObject(i).getString("UserName");
                        id=j.getJSONObject(i).getString("W_id");
                        Log.e("Name", ">>>>>" + name);

                        data_model c=new data_model();
                        c.setW_id(id);
                        c.setUsername(name);
                        workername.add(name);
                        workerlist.add(c);
                    }


                    spinner.setOnItemSelectedListener(UserRequest_Details.this);

                    ArrayAdapter aa = new ArrayAdapter(UserRequest_Details.this,R.layout.spinner_item,workername);
                    spinner.setAdapter(aa);

                }
                catch (Exception e)
                {
                    Toast.makeText(UserRequest_Details.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserRequest_Details.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue queue= Volley.newRequestQueue( UserRequest_Details.this);
        queue.add(request1);


        workerallo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            StringRequest request = new StringRequest(Request.Method.POST, UPDATEWORKER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.trim().equals("edit"))
                    {
                        Toast.makeText(UserRequest_Details.this, "update", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(UserRequest_Details.this,"not update",Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UserRequest_Details.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> stringStringMap = new HashMap<>();
                    stringStringMap.put("w_id",w_id);
                    stringStringMap.put("ure_id",id);
                    return stringStringMap;
                }
            };

                RequestQueue queue = Volley.newRequestQueue(UserRequest_Details.this);
                queue.add(request);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        w_id=workerlist.get(i).getW_id();
        Toast.makeText(this, workerlist.get(i).getUsername(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getApplicationContext(), "Select spinner item", Toast.LENGTH_SHORT).show();

    }
}
