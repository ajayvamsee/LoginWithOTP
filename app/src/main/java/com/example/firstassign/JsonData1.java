
package com.example.firstassign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonData1 extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_data1);

        mRecyclerView=findViewById(R.id.mRecyclerViewP);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d("recycle", String.valueOf(mRecyclerView));



        mExampleList=new ArrayList<>();

        mRequestQueue= Volley.newRequestQueue(this);


        parseJson();


    }
  private void parseJson() {

        // https://github.com/ajayvamsee/sampleJsonFile/blob/main/myDB.json

        String url="https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                    try{
                        JSONArray jsonArray= response.getJSONArray("hits");

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject hit=jsonArray.getJSONObject(i);

                            String author_name=hit.getString("user");
                            String image_url=hit.getString("webformatURL");

                            mExampleList.add(new ExampleItem(image_url,author_name));


                        }

                        mExampleAdapter=new ExampleAdapter(JsonData1.this,mExampleList);
                        mRecyclerView.setAdapter(mExampleAdapter);

                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }
}