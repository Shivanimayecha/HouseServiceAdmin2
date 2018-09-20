package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    String URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/AdminLogin.php";
    Button btnlogin;
    EditText inputEmail,inputPassword;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       inputEmail =(EditText)findViewById(R.id.edtEmail);
       inputPassword=(EditText)findViewById(R.id.edtPassword);
       btnlogin=(Button)findViewById(R.id.btnLogin);

        pref= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
       // String strng = pref.getString("login", "");

       btnlogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               final String email = inputEmail.getText().toString();
               final String pass = inputPassword.getText().toString();

               StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
}
