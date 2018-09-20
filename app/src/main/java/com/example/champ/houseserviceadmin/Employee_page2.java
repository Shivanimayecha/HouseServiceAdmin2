package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Employee_page2 extends AppCompatActivity {

    RecyclerView recyclerView;
    String EMPLOYEE_GETDATAURL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Employeedata.php";

    EmployeeAdapter employeeAdapter;
    List<data_model>data_models = new ArrayList<>();
    data_model d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page2);
        recyclerView = (RecyclerView)findViewById(R.id.rv2);



        final StringRequest request = new StringRequest(Request.Method.GET, EMPLOYEE_GETDATAURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("Response",">>>"+response);

                try
                {
                    JSONArray j = new JSONArray(response);
                    for(int i = 0;i<j.length();i++)
                    {
                     String firstname = j.getJSONObject(i).getString("FirstName");
                     String lastname = j.getJSONObject(i).getString("LastName");

                     Log.e("Response",">>>>"+firstname+lastname);

                     employeeAdapter = new EmployeeAdapter(Employee_page2.this,data_models);
                     RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                     recyclerView.setLayoutManager(eLayoutManager);
                     recyclerView.setItemAnimator(new DefaultItemAnimator());
                     recyclerView.setAdapter(employeeAdapter);

                     d = new data_model();
                     d.setFirstname(firstname);
                     d.setLastname(lastname);
                     data_models.add(d);

                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(Employee_page2.this, "Error"+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Employee_page2.this, "Error"+error, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue1 = Volley.newRequestQueue(Employee_page2.this);
        queue1.add(request);


    }


}
