package com.example.champ.houseserviceadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
    //RecyclerView recyclerView;
    ListView listView;
   // private RequestPageAdapter requestPageAdapter;
    public data_model d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request_page);
        listView = (ListView)findViewById(R.id.lv);
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
                        String address = j.getJSONObject(i).getString("Address");
                        String w_id = j.getJSONObject(i).getString("W_id");

                        Log.e("username",">>>>" +username);


                       /* requestPageAdapter = new RequestPageAdapter(ViewRequestPage.this,data_models);
                        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(cLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(requestPageAdapter);*/

                        d = new data_model();
                        d.setUsername(username);
                        d.setDesc(description);
                        d.setAddress(address);
                        d.setW_id(w_id);
                        d.setId(id);
                        data_models.add(d);

                    }

                    RequestPage_BaseAdapter requestPage_baseAdapter = new RequestPage_BaseAdapter(ViewRequestPage.this,data_models);
                    listView.setAdapter(requestPage_baseAdapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            /*for (int k=0;k<data_models.get(i).size();k++){
                                str= list.get(position).getSname1();
                            }*/

                            Intent intent=new Intent(ViewRequestPage.this,UserRequest_Details.class);
                            intent.putExtra("name",data_models.get(i).getUsername());
                            intent.putExtra("description",data_models.get(i).getDesc());
                            intent.putExtra("address",data_models.get(i).getAddress());

                            startActivity(intent);
                        }
                    });



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
