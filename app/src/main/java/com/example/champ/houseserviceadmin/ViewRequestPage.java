package com.example.champ.houseserviceadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class ViewRequestPage extends AppCompatActivity {

    String USERREQURL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_UserRequestData.php";
    private List<data_model> data_models = new ArrayList<>();
    RecyclerView recyclerView;
    private RequestPageAdapter requestPageAdapter;
    public data_model d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request_page);
        recyclerView = (RecyclerView)findViewById(R.id.rv_viewReq);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StringRequest request = new StringRequest(Request.Method.GET, USERREQURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.e("Response",">>>>" +response);

                try
                {

                    JSONArray j = new JSONArray(response);
                    for (int i=0;i<j.length();i++)
                    {
                        String username = j.getJSONObject(i).getString("UserName");
                        String description = j.getJSONObject(i).getString("Description");
                        String id = j.getJSONObject(i).getString("Ure_id");

                        Log.e("username",">>>>" +username);


                        requestPageAdapter = new RequestPageAdapter(ViewRequestPage.this,data_models);
                        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(cLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(requestPageAdapter);

                        d = new data_model();
                        d.setUsername(username);
                        d.setDesc(description);
                        d.setId(id);
                        data_models.add(d);

                    }


                }
                catch (Exception e)
                {
                    Toast.makeText(ViewRequestPage.this, "Error" +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewRequestPage.this, "Error" +error, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(ViewRequestPage.this);
        queue.add(request);


    }
}
