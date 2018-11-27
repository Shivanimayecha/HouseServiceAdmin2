package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class WorkerPage extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    String WorkerRegi_URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/WorkerRegistration.php";
    String GETCity_URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Citydata.php";
    String CATEGORY_URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Categorydata.php";
    String Area_URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Areadata.php";


    EditText firstname,lastname,address,contact_no,email,serviceDes,username,password;
    Spinner city,category,area;
    Button btnReg;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPref";
    public static final String Email = "emailkey";
    public static final String Password = "passkey";

    String c_id;
    List<String> cityname=new ArrayList<>();
    ArrayList<GetCity>citylist=new ArrayList<GetCity>();

    List<String> categoryname=new ArrayList<>();
    ArrayList<data_model>categorylist=new ArrayList<data_model>();

    List<String> areaname=new ArrayList<>();
    ArrayList<data_model>arealist=new ArrayList<data_model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_page);

        city = (Spinner)findViewById(R.id.spinner1);
        firstname = (EditText)findViewById(R.id.edt_firstname);
        lastname = (EditText)findViewById(R.id.edt_lastname);
        city = (Spinner)findViewById(R.id.spinner1);
        category=(Spinner)findViewById(R.id.spinner2);
        area=(Spinner)findViewById(R.id.spinner3);
        address = (EditText)findViewById(R.id.edt_address1);
        contact_no = (EditText)findViewById(R.id.edt_phon);
        email = (EditText)findViewById(R.id.edt_email);
        username = (EditText)findViewById(R.id.edt_username);
        password = (EditText)findViewById(R.id.edt_pass);
        serviceDes=(EditText)findViewById(R.id.edt_serviceDes);
        btnReg = (Button)findViewById(R.id.btn_signup);

        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        //for city spinner
        StringRequest requestCity=new StringRequest(Request.Method.GET, GETCity_URL, new Response.Listener<String>() {
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
                    Toast.makeText(WorkerPage.this, "Error" + e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WorkerPage.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue  queue= Volley.newRequestQueue( WorkerPage.this);
        queue.add(requestCity);



        city.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cityname);
        city.setAdapter(aa);

        //for category spinner
        StringRequest requestCategory=new StringRequest(Request.Method.GET, CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response", ">>>>>" + response);
                try {

                    JSONArray j=new JSONArray(response);
                    for(int i=0;i<j.length();i++)
                    {
                        String category=j.getJSONObject(i).getString("CategoryName");
                        String id=j.getJSONObject(i).getString("Category_id");
                        Log.e("category", ">>>>>" + category);

                        data_model c=new data_model();
                        c.setId(id);
                        c.setCategory(category);
                        categoryname.add(category);
                        categorylist.add(c);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(WorkerPage.this, "Error" + e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WorkerPage.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue  queue1 = Volley.newRequestQueue( WorkerPage.this);
        queue1.add(requestCategory);

        category.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        ArrayAdapter aa1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,categoryname);
        category.setAdapter(aa1);


        //for area spinner

        StringRequest requestArea=new StringRequest(Request.Method.GET,Area_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response", ">>>>>" + response);
                try {

                    JSONArray j=new JSONArray(response);
                    for(int i=0;i<j.length();i++)
                    {
                        String area=j.getJSONObject(i).getString("AreaName");
                        String id=j.getJSONObject(i).getString("A_id");
                        Log.e("category", ">>>>>" + area);

                        data_model c=new data_model();
                        c.setId(id);
                        c.setArea(area);
                        areaname.add(area);
                        arealist.add(c);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(WorkerPage.this, "Error" + e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WorkerPage.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue  queue2 = Volley.newRequestQueue( WorkerPage.this);
        queue2.add(requestArea);

        area.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        ArrayAdapter aa2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,areaname);
        area.setAdapter(aa2);



        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fn = firstname.getText().toString();
                final String ln = lastname.getText().toString();
                //final String cn = city.getText().toString();
                final String add1 = address.getText().toString();
                final String mob_no = contact_no.getText().toString();
                final String email_id = email.getText().toString();
                final String uname = username.getText().toString();
                final String service=serviceDes.getText().toString();
                final String pass = password.getText().toString().trim();



                    StringRequest request = new StringRequest(Request.Method.POST, WorkerRegi_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.trim().equals("Success"))
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Email,email_id);
                                editor.putString(Password,pass);
                                editor.putString("reg","1");
                                editor.commit();
                                Toast.makeText(WorkerPage.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(WorkerPage.this,Employee_page2.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(WorkerPage.this, "Register failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(WorkerPage.this, "Error"+error, Toast.LENGTH_SHORT).show();
                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>str=new HashMap<>();
                            str.put("firstname",fn);
                            str.put("lastname",ln);
                            str.put("contactno",mob_no);
                            str.put("email",email_id);
                            str.put("cid",c_id);
                            str.put("serviceDes",service);
                            str.put("address1",add1);
                            str.put("username",uname);
                            str.put("password",pass);

                            return str;
                        }
                    };

                    RequestQueue queue1 = Volley.newRequestQueue(WorkerPage.this);
                    queue1.add(request);

            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(getApplicationContext(),"hiiii", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
