package com.example.champ.houseserviceadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AreaPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Areadata.php";

    String URL2 = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Citydata.php";
    private AreaAdapter aAdapter;
    ArrayList<GetCity> citylist;
    String c_id;
    List<String>cityname;
    EditText areanew;
  //  public List<data_model> models;
    List<data_model> data_models=new ArrayList<>() ;



    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_page);
        recyclerView=(RecyclerView)findViewById(R.id.rv1);
        Toolbar toolbar2 = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);

        cityname=new ArrayList<>();
        citylist=new ArrayList<GetCity>();


        StringRequest request1=new StringRequest(Request.Method.GET, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                        cityname.add(city);
                        citylist.add(c);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(AreaPage.this, "Error" + e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AreaPage.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue  queue= Volley.newRequestQueue( AreaPage.this);
        queue.add(request1);

        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response",">>>>" +response);
                try
                {
                    JSONArray j = new JSONArray(response);
                    for(int i=0;i<j.length();i++)
                    {
                     String area = j.getJSONObject(i).getString("AreaName");
                     String city = j.getJSONObject(i).getString("CityName");
                     String id = j.getJSONObject(i).getString("A_id");
                     Log.e("Area",">>>>" +area);


                        data_model d=new data_model();
                        d.setCity(city);
                        d.setArea(area);
                        d.setId(id);
                        data_models.add(d);

                     aAdapter=new AreaAdapter(AreaPage.this,data_models);
                     RecyclerView.LayoutManager alayoutManager = new LinearLayoutManager(getApplicationContext());
                     recyclerView.setLayoutManager(alayoutManager);
                     recyclerView.setItemAnimator(new DefaultItemAnimator());
                     recyclerView.setAdapter(aAdapter);
                    }

                }
                catch (Exception e)
                {
                    Toast.makeText(AreaPage.this,"Error" +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AreaPage.this, "Error" +error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue1 = Volley.newRequestQueue(AreaPage.this);
        queue1.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(AreaPage.this);
        View view = getLayoutInflater().inflate(R.layout.area_spinnerdialog,null);
        aBuilder.setTitle("Add Area");
        final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
      // String[] country = { "India", "USA", "China", "Japan", "Other"};
        //    for (int i=0;i<data_models.size();i++) {
        //Spinner_Adapter aa=new Spinner_Adapter(AreaPage.this,data_models);
           // aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            ArrayAdapter aa=new ArrayAdapter(this,android.R.layout.simple_spinner_item,cityname);
            spinner.setAdapter(aa);
        //Setting the ArrayAdapter data on the Spinner
           areanew = (EditText)view.findViewById(R.id.edt_area);

        aBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

            String URL3 = "https://shivanimayecha0908.000webhostapp.com/HandyManService/AreaEntry.php";

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String str = areanew.getText().toString();


                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(c_id)) {
                    Toast.makeText(AreaPage.this, "Enter both", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest request = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.trim().equals("Success")) {
                                Toast.makeText(AreaPage.this, "succseffully added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AreaPage.this, AreaPage.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AreaPage.this, "unsuccsesfull to add", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(AreaPage.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> stringStringMap = new HashMap<>();
                            stringStringMap.put("cid", c_id);
                            stringStringMap.put("areaname", str);
                            return stringStringMap;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(AreaPage.this);
                    queue.add(request);
                }
            }
        });

        aBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        aBuilder.setView(view);

        AlertDialog ad = aBuilder.create();
        ad.show();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    c_id=citylist.get(i).getC_id().toString();
     //   Toast.makeText(this, , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
