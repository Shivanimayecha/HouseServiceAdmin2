package com.example.champ.houseserviceadmin;

import android.content.Context;
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

public class ChangePassword extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences pref;
    private EditText oldpassword,newpassword,confrompassword;
    private Button reset;
    String URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/ChangePass.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldpassword=(EditText)findViewById(R.id.edt_oldpass);
        newpassword=(EditText)findViewById(R.id.edt_newpass);
        confrompassword=(EditText)findViewById(R.id.edt_conpass);
        reset=(Button)findViewById(R.id.btn_changepass);

        pref= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String str1 = pref.getString("emailKey","");
       // Toast.makeText(ChangePassword.this, str1, Toast.LENGTH_SHORT).show();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String oldpass =oldpassword.getText().toString();
                final String newpass = newpassword.getText().toString().trim();
                final String conpass=confrompassword.getText().toString().trim();
                if(newpass.equals(conpass))
                {
                    Toast.makeText(ChangePassword.this, "password mach",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ChangePassword.this, "password not mach", Toast.LENGTH_LONG).show();
                }
                StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("update"))
                        {
                            Toast.makeText(ChangePassword.this, "password change", 0).show();
                        }
                        else
                        {
                            Toast.makeText(ChangePassword.this, "failed to change password", 0).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChangePassword.this, "Error" + error,Toast.LENGTH_LONG).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>str=new HashMap<>();
                        str.put("oldpassword", oldpass);
                        str.put("newpassword", newpass);
                        str.put("email",str1);
                        return str;
                    }
                };

                RequestQueue queue= Volley.newRequestQueue(ChangePassword.this);
                queue.add(request);
            }
        });
    }
}
