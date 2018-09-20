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
import android.widget.EditText;
import android.widget.ImageView;
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

public class CityPage extends AppCompatActivity {

  //  private String TAG = MainActivity.class.getSimpleName();
    String URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Citydata.php";
    private CityAdapter cAdapter;
    private List<data_model> data_models = new ArrayList();
    EditText inputCity;
    private RecyclerView recyclerView;
  //  private ImageView edit,delete;
 public data_model d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_page);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        recyclerView=(RecyclerView)findViewById(R.id.rv);
        //edit=(ImageView)findViewById(R.id.iv_edit);
      //  delete=(ImageView)findViewById(R.id.iv_delete);

        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
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


                        cAdapter=new CityAdapter(CityPage.this,data_models);
                        RecyclerView.LayoutManager clayoutmanager=new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(clayoutmanager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(cAdapter);

                         d=new data_model();
                        d.setCity(city);
                        d.setId((id));
                        data_models.add(d);


                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(CityPage.this, "Error" + e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CityPage.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue  queue= Volley.newRequestQueue(CityPage.this);
        queue.add(request);

     /*   delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL2="https://shivanimayecha0908.000webhostapp.com/HandyManService/deleteCity.php";

                StringRequest request1=new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equals("delete"))
                        {
                            Toast.makeText(CityPage.this, "Delete successfully ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(CityPage.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CityPage.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue  queue= Volley.newRequestQueue(CityPage.this);
                queue.add(request1);
            }
        });*/
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City");
        builder.setMessage("Enter new city");

        inputCity = new EditText(this);
        builder.setView(inputCity);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            String URL1 = "https://shivanimayecha0908.000webhostapp.com/HandyManService/CityEntry.php";

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String txt = inputCity.getText().toString();
                if (TextUtils.isEmpty(txt)) {
                    Toast.makeText(CityPage.this, "Enter City Name", Toast.LENGTH_LONG).show();
                } else
                    {
                    StringRequest request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.trim().equals("Success")) {
                                Toast.makeText(CityPage.this, "City Added", Toast.LENGTH_SHORT).show();
                                Toast.makeText(CityPage.this, txt, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CityPage.this, CityPage.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CityPage.this, "City Not Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CityPage.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> str = new HashMap<>();
                            str.put("cityname", txt);
                            return str;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(CityPage.this);
                    queue.add(request);

                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        AlertDialog ad = builder.create();
        ad.show();
        return true;
    }
}
