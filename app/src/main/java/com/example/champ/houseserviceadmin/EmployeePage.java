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

public class EmployeePage extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    String EMPLOYEEREGI_URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/EmployeeRegistration.php";
    String GETCity_URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Citydata.php";
    EditText firstname,lastname,address1,address2,contact_no,email,salary,username,password,confrimPass;
    Spinner city;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPref";
    public static final String Email = "emailkey";
    public static final String Password = "passkey";
    Button btnReg;
    String c_id;
    List<String> cityname=new ArrayList<>();
    ArrayList<GetCity>citylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        firstname = (EditText)findViewById(R.id.edt_firstname);
        lastname = (EditText)findViewById(R.id.edt_lastname);
        city = (Spinner)findViewById(R.id.spinner);
        address1 = (EditText)findViewById(R.id.edt_address1);
        address2 = (EditText)findViewById(R.id.edt_address2);
        contact_no = (EditText)findViewById(R.id.edt_phon);
        email = (EditText)findViewById(R.id.edt_email);
        salary = (EditText)findViewById(R.id.edt_salary);
        username = (EditText)findViewById(R.id.edt_username);
        password = (EditText)findViewById(R.id.edt_pass);
        confrimPass = (EditText)findViewById(R.id.edt_conpass);
        btnReg = (Button)findViewById(R.id.btn_signup);

        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);



        StringRequest request1=new StringRequest(Request.Method.GET, GETCity_URL, new Response.Listener<String>() {
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
                    Toast.makeText(EmployeePage.this, "Error" + e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EmployeePage.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue  queue= Volley.newRequestQueue( EmployeePage.this);
        queue.add(request1);


        //Spinner spinner = (Spinner)findViewById(R.id.spinner);

        city.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,cityname);
        city.setAdapter(aa);


        citylist=new ArrayList<GetCity>();



        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fn = firstname.getText().toString();
                final String ln = lastname.getText().toString();
                //final String cn = city.getText().toString();
                final String add1 = address1.getText().toString();
                final String add2 = address2.getText().toString();
                final String mob_no = contact_no.getText().toString();
                final String email_id = email.getText().toString();
                final String slry = salary.getText().toString();
                final String uname = username.getText().toString();
                final String pass = password.getText().toString().trim();
                String conpass = confrimPass.getText().toString().trim();





                if(pass.equals(conpass))
                {
                    StringRequest request = new StringRequest(Request.Method.POST, EMPLOYEEREGI_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.trim().equals("Success"))
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Email,email_id);
                                editor.putString(Password,pass);
                                editor.putString("reg","1");
                                editor.commit();
                                Toast.makeText(EmployeePage.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EmployeePage.this,Employee_page2.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(EmployeePage.this, "Register failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EmployeePage.this, "Error"+error, Toast.LENGTH_SHORT).show();
                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>str=new HashMap<>();
                            str.put("firstname",fn);
                            str.put("lastname",ln);
                           str.put("cid",c_id);
                            str.put("address1",add1);
                            str.put("address2",add2);
                            str.put("contactno",mob_no);
                            str.put("email",email_id);
                            str.put("salary",slry);
                            str.put("username",uname);
                            str.put("password",pass);

                            return str;
                        }
                    };

                    RequestQueue queue1 = Volley.newRequestQueue(EmployeePage.this);
                    queue1.add(request);

                }
                else
                {
                    Toast.makeText(EmployeePage.this, "Password not match", Toast.LENGTH_SHORT).show();
                }

            }
        });








    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        c_id=citylist.get(i).getC_id().toString();
        Toast.makeText(this, citylist.get(i).getCity().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
