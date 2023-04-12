package com.example.apiintegrationwithtoken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiintegrationwithtoken.databinding.ActivityCreatePostBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreatePost extends AppCompatActivity {

    ActivityCreatePostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent inId= getIntent();
        String user_Id= inId.getStringExtra("id");
        Toast.makeText(this, "user id"+user_Id, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createJSONObjext();
            }
        });
    }

    void createJSONObjext(){

        String title= binding.crtPostTitle.getText().toString();
        String post= binding.crtPostBody.getText().toString();

        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("title",title);
            jsonObject.put("body",post);

            makePostRequest(jsonObject);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    void makePostRequest(JSONObject object)
    {
        String url="https://gorest.co.in/public/v2/users/776641/posts";
        String token="b6f9f4bc7795e4aa1e6926f7e92910f4f4d32c8c9be442cb80b2dd482b9242ea";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(CreatePost.this, "Post Sucssecful", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CreatePost.this, "UnsuccessFul", Toast.LENGTH_SHORT).show();
                finish();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params= new HashMap<>();
                params.put("Authorization","Bearer "+token);
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(CreatePost.this);
        queue.add(request);
    }
}