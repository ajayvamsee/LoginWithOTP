package com.example.firstassign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonData extends AppCompatActivity {

    TextView mTextView;
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_data);

        mTextView = findViewById(R.id.textView);

        mRequestQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    private void jsonParse() {
        String url = "https://github.com/ajayvamsee/myjson/blob/main/db.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response!=null){

                    try {
                        JSONArray jsonArray=response.getJSONArray("contacts");

                        for (int i=0;i<=jsonArray.length();i++){

                            JSONObject employee=jsonArray.getJSONObject(i);

                            String id = employee.getString("id");
                            String name = employee.getString("name");
                            String email = employee.getString("email");
                            String address = employee.getString("address");
                            String gender = employee.getString("gender");

                            mTextView.append("ID:"+id+"\n"+
                                    "Name:"+name+"\n"+
                                    "Email:"+email+"\n"+
                                    "Address:"+address+"\n"+
                                    "Gender:"+gender+"\n");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JsonData.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error Msg",error.getMessage());

            }
        });

        mRequestQueue.add(request);
    }
}