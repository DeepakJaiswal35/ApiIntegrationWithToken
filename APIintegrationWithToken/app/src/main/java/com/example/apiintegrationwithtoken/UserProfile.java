package com.example.apiintegrationwithtoken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiintegrationwithtoken.databinding.ActivityUserProfileBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    ActivityUserProfileBinding binding;
    private String id="";
    private ArrayList<UserPostsClass> arrayList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent in= getIntent();
        id=in.getStringExtra("id");
        binding.textProName.setText(in.getStringExtra("name"));
        binding.textProEmail.setText(in.getStringExtra("email"));
        binding.textProGender.setText(in.getStringExtra("gender"));

        binding.recyclerViewProfile.setLayoutManager(new LinearLayoutManager(UserProfile.this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        makeGetRequest();
    }

    void makeGetRequest(){

        String url="https://gorest.co.in/public/v2/users/"+id+"/posts";
        String token="b6f9f4bc7795e4aa1e6926f7e92910f4f4d32c8c9be442cb80b2dd482b9242ea";

        Request request= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parshJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserProfile.this, "No Post Available", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params= new HashMap<>();
                params.put("Authorization","Bearer "+token);
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(UserProfile.this);
        queue.add(request);
    }

    void parshJSON(JSONArray array)
    {
        for (int i=0;i<array.length();i++)
        {
            try {
                JSONObject object= array.getJSONObject(i);
                UserPostsClass post = new UserPostsClass();
                post.postId=object.getString("id");
                post.userId = object.getString("user_id");
                post.title = object.getString("title");
                post.body = object.getString("body");

                arrayList.add(post);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            binding.recyclerViewProfile.setAdapter(new NewAdapter(arrayList));
        }
    }
}