package com.example.champ.houseserviceadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class Employee_Categories extends AppCompatActivity {

    private CardView worker_Management,user_request,changepass,logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__categories);

        logout=(CardView)findViewById(R.id.card_logout);
        changepass=(CardView)findViewById(R.id.card_changPass);
        worker_Management=(CardView)findViewById(R.id.card_workerManagement);
        user_request=(CardView)findViewById(R.id.card_viewUserReq);


        user_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Employee_Categories.this,ViewRequestPage.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder adb=new AlertDialog.Builder(Employee_Categories.this);
                adb.setMessage("are you sure you want to Logout");

                //positive button
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      //  Toast.makeText(Employee_Categories.this, "Logout User : " + string,Toast.LENGTH_LONG).show();

                        Toast.makeText(Employee_Categories.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Employee_Categories.this,MainActivity.class);
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
                Intent intent=new Intent(Employee_Categories.this,Employee_ChangePass.class);
                startActivity(intent);
            }
        });
        worker_Management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Employee_Categories.this,WorkerPage.class);
                startActivity(intent);
            }
        });

    }
}
