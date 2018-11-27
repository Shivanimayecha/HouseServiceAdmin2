package com.example.champ.houseserviceadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class Categories extends AppCompatActivity {

    private CardView city,area,category,employee,worker,user,changepass,logout;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        pref= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        final String string=pref.getString("emailKey","");
        logout=(CardView)findViewById(R.id.card_logout);
        changepass=(CardView)findViewById(R.id.card_changPass);
        city=(CardView)findViewById(R.id.card_city);
        area=(CardView)findViewById(R.id.card_area);
        category=(CardView)findViewById(R.id.card_category);
        employee=(CardView)findViewById(R.id.card_employee);
        worker=(CardView)findViewById(R.id.card_worker);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder adb=new AlertDialog.Builder(Categories.this);
                adb.setMessage("are you sure you want to Logout");

                //positive button
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Categories.this, "Logout User : " + string,Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("login", "0");
                        editor.commit();
                        Toast.makeText(Categories.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Categories.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                //negative button
                adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog alertDialog = adb.create();
                alertDialog.show();
            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Categories.this,ChangePassword.class);
                startActivity(intent);
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Categories.this,CityPage.class);
                startActivity(intent);
            }
        });
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Categories.this,AreaPage.class);
                startActivity(intent);
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Categories.this,CategoryPage.class);
                startActivity(intent);
            }
        });
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this,EmployeePage.class);
                startActivity(intent);
            }
        });
        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this,WorkerPage.class);
                startActivity(intent);
            }
        });
    }
}
