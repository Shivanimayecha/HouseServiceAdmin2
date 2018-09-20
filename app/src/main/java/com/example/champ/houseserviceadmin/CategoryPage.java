package com.example.champ.houseserviceadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryPage extends AppCompatActivity {

    String CATEGORY_URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/get_Categorydata.php";
    RecyclerView recyclerView;
    private List<data_model>data_models = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    public data_model d;
    public EditText inputcategory,inputdesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        recyclerView=(RecyclerView)findViewById(R.id.category_rv);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final StringRequest request = new StringRequest(Request.Method.GET, CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response",">>>>" +response);

                try
                {

                    JSONArray j = new JSONArray(response);
                    for (int i=0;i<j.length();i++)
                    {
                        String categoryname = j.getJSONObject(i).getString("CategoryName");
                        String description = j.getJSONObject(i).getString("Description");
                        String category_id = j.getJSONObject(i).getString("Category_id");

                        Log.e("CategoryName",">>>>" +categoryname);


                        categoryAdapter = new CategoryAdapter(CategoryPage.this,data_models);
                        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(cLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(categoryAdapter);

                        d = new data_model();
                        d.setCategory(categoryname);
                        d.setDesc(description);
                        d.setId(category_id);
                        data_models.add(d);

                    }


                }
                catch (Exception e)
                {
                    Toast.makeText(CategoryPage.this, "Error" +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategoryPage.this, "Error" +error, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(CategoryPage.this);
        queue.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert new Category & description");
        View view = getLayoutInflater().inflate(R.layout.categorydialog,null);

        inputcategory = (EditText)view.findViewById(R.id.category);
        inputdesc = (EditText)view.findViewById(R.id.description);
       // builder.setMessage("Enter New Category");

       /* inputcategory = new EditText(this);
        inputcategory.setHint("category");
        inputcategory.layout(10,20,10,30);
        builder.setView(inputcategory);
        inputdesc = new EditText(this);
        inputdesc.setHint("description");
        inputdesc.layout(10,100,10,30);
        builder.setView(inputdesc);*/

        builder.setPositiveButton("Insert", new DialogInterface.OnClickListener() {

            String InsertCategory_URL = "https://shivanimayecha0908.000webhostapp.com/HandyManService/CategoriesEntry.php";

            public void onClick(DialogInterface dialogInterface, int i) {
                final String category = inputcategory.getText().toString();
                final String desc = inputdesc.getText().toString();

                if (TextUtils.isEmpty(category) || TextUtils.isEmpty(desc))
                {
                    Toast.makeText(CategoryPage.this, "Enter Category", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    StringRequest request = new StringRequest(Request.Method.POST, InsertCategory_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            if(response.trim().equals("Success"))
                            {
                                Toast.makeText(CategoryPage.this,category, Toast.LENGTH_SHORT).show();
                                Toast.makeText(CategoryPage.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CategoryPage.this,CategoryPage.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(CategoryPage.this, "Unsuccessful to add", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CategoryPage.this, "Error" + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String>str = new HashMap<>();
                            str.put("categoryname",category);
                            str.put("description",desc);
                            return str;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(CategoryPage.this);
                    queue.add(request);

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
            }
        });
        builder.setView(view);
        AlertDialog ad = builder.create();
        ad.show();
        return true;
    }
}
