package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    String URL_ADMIN = "https://shivanimayecha0908.000webhostapp.com/HandyManService/AdminLogin.php";
    String URL_EMPLOYEE = "https://shivanimayecha0908.000webhostapp.com/HandyManService/EmployeeLogin.php";
    Button btnlogin;
    EditText inputEmail, inputPassword;
    RadioGroup rd_login;
    RadioButton rb_employee, rb_admin;
    TextView txtLogin;
    CheckBox checkBox_showPass;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = (EditText) findViewById(R.id.edtEmail);
        inputPassword = (EditText) findViewById(R.id.edtPassword);
        btnlogin = (Button) findViewById(R.id.btnLogin);
        rd_login = (RadioGroup) findViewById(R.id.rdlogin);

        txtLogin = (TextView) findViewById(R.id.txtLogin);

        rb_employee = (RadioButton) findViewById(R.id.employeelogin);
        rb_admin = (RadioButton) findViewById(R.id.adminlogin);

        checkBox_showPass=(CheckBox)findViewById(R.id.show_hidePass);

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String strng = pref.getString("emailKey", "");



        checkBox_showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                {
                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                else
                {
                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });


        rb_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean checked = ((RadioButton) view).isChecked();
                if (checked) {
                    txtLogin.setText("Admin Login");
                   // Toast.makeText(MainActivity.this, "Admin", Toast.LENGTH_SHORT).show();

                    btnlogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final String email = inputEmail.getText().toString();
                            final String pass = inputPassword.getText().toString();

                            StringRequest request = new StringRequest(Request.Method.POST, URL_ADMIN, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.trim().equals("Success")) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString(Email, email);
                                        editor.putString(Password, pass);
                                        editor.putString("login", "1");
                                        editor.commit();
                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(MainActivity.this, "Login User : " + strng,Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(MainActivity.this, Categories.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(MainActivity.this, "Error" + error, Toast.LENGTH_LONG).show();
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> str = new HashMap<>();
                                    str.put("email", email);
                                    str.put("password", pass);
                                    return str;
                                }
                            };

                            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                            queue.add(request);
                        }
                    });
                }
            }

        });

        rb_employee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // pref =
                        boolean checked = ((RadioButton) view).isChecked();
                        if (checked) {
                            txtLogin.setText("Employee Login");
                            // Toast.makeText(MainActivity.this, "Employee", Toast.LENGTH_SHORT).show();
                            btnlogin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    final String email = inputEmail.getText().toString();
                                    final String pass = inputPassword.getText().toString();

                                    StringRequest request = new StringRequest(Request.Method.POST, URL_EMPLOYEE, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            if (response.trim().equals("success")) {
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString(Email, email);
                                                editor.putString(Password, pass);
                                                editor.putString("login", "1");
                                                editor.commit();
                                                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(MainActivity.this, "Login User : " + strng, Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MainActivity.this, Employee_Categories.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            Toast.makeText(MainActivity.this, "Error" + error, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> str = new HashMap<>();
                                            str.put("email", email);
                                            str.put("password", pass);
                                            return str;
                                        }
                                    };

                                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                    queue.add(request);
                                }
                            });
                        }

                    }
                });


        /*rb_admin.setOnClickListener(radio_listener);
        rb_employee.setOnClickListener(radio_listener);*/

    }
}
   /* View.OnClickListener radio_listener = new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on clicks
            if(v == radio_listener)
            {

                if(rb_admin.isChecked())
                {
                    Toast.makeText(MainActivity.this, "hii", Toast.LENGTH_SHORT).show();

                   *//* txtLogin.setText("Admin Login");
                    btnlogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final String email = inputEmail.getText().toString();
                            final String pass = inputPassword.getText().toString();

                            StringRequest request=new StringRequest(Request.Method.POST, URL_ADMIN, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response)
                                {

                                    if(response.trim().equals("Success"))
                                    {
                                        SharedPreferences.Editor editor=pref.edit();
                                        editor.putString(Email,email);
                                        editor.putString(Password,pass);
                                        editor.putString("login","1");
                                        editor.commit();
                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(MainActivity.this,Categories.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(MainActivity.this, "Error" + error,Toast.LENGTH_LONG).show();
                                }
                            }
                            ){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> str = new HashMap<>();
                                    str.put("email", email);
                                    str.put("password", pass);
                                    return str;
                                }
                            };

                            RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                            queue.add(request);
                        }
                    });
*//*

                }
                if(rb_employee.isChecked() == true)
                {
                   // Toast.makeText(cafechoice.this, 1, Toast.LENGTH_SHORT).show();
                    txtLogin.setText("Employee Login");
                    btnlogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final String email = inputEmail.getText().toString();
                            final String pass = inputPassword.getText().toString();

                            StringRequest request=new StringRequest(Request.Method.POST, URL_EMPLOYEE, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response)
                                {

                                    if(response.trim().equals("success"))
                                    {
                                        SharedPreferences.Editor editor=pref.edit();
                                        editor.putString(Email,email);
                                        editor.putString(Password,pass);
                                        editor.putString("login","1");
                                        editor.commit();
                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(MainActivity.this,Employee_Categories.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(MainActivity.this, "Error" + error,Toast.LENGTH_LONG).show();
                                }
                            }
                            ){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> str = new HashMap<>();
                                    str.put("email", email);
                                    str.put("password", pass);
                                    return str;
                                }
                            };

                            RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                            queue.add(request);
                        }
                    });
                }
            }
        }
    };
*
      /*  if(rb_admin.)
        {
           txtLogin.setText("Admin Login");

            *//*Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            *//*

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String email = inputEmail.getText().toString();
                    final String pass = inputPassword.getText().toString();

                    StringRequest request=new StringRequest(Request.Method.POST, URL_ADMIN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {

                            if(response.trim().equals("Success"))
                            {
                                SharedPreferences.Editor editor=pref.edit();
                                editor.putString(Email,email);
                                editor.putString(Password,pass);
                                editor.putString("login","1");
                                editor.commit();
                                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,Categories.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this, "Error" + error,Toast.LENGTH_LONG).show();
                        }
                    }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> str = new HashMap<>();
                            str.put("email", email);
                            str.put("password", pass);
                            return str;
                        }
                    };

                    RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                    queue.add(request);
                }
            });

        }
        else
        {
           // txtLogin.setText("Employee Login");

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String email = inputEmail.getText().toString();
                    final String pass = inputPassword.getText().toString();

                    StringRequest request=new StringRequest(Request.Method.POST, URL_EMPLOYEE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {

                            if(response.trim().equals("success"))
                            {
                                SharedPreferences.Editor editor=pref.edit();
                                editor.putString(Email,email);
                                editor.putString(Password,pass);
                                editor.putString("login","1");
                                editor.commit();
                                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,Employee_Categories.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this, "Error" + error,Toast.LENGTH_LONG).show();
                        }
                    }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> str = new HashMap<>();
                            str.put("email", email);
                            str.put("password", pass);
                            return str;
                        }
                    };

                    RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                    queue.add(request);
                }
            });

        }
*/


