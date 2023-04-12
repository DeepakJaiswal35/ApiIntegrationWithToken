package com.example.apiintegrationwithtoken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiintegrationwithtoken.databinding.ActivityUserDataBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserData extends AppCompatActivity {

    ArrayList<UserPostsClass>arrayList= new ArrayList<>();
    ActivityUserDataBinding binding;
    private  String id="";
    String name,email,gender;

    public String getId() {
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Set LayoutManager On RecyclerView
        binding.recyclerViewPost.setLayoutManager(new LinearLayoutManager(UserData.this));

//        get id from itnent
        Intent obj= getIntent();
        id = obj.getStringExtra("id");

//        Button for Create A Post
        binding.imgCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createPost =new Intent(UserData.this,CreatePost.class);
                createPost.putExtra("id",id);
                startActivity(createPost);
//                Toast.makeText(UserData.this, "Create Post", Toast.LENGTH_SHORT).show();
            }
        });
        
//        Image Button For View User Profile
        binding.imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextPro = new Intent(UserData.this,UserProfile.class);
                nextPro.putExtra("id",id);
                nextPro.putExtra("name",name);
                nextPro.putExtra("email",email);
                nextPro.putExtra("gender",gender);
                startActivity(nextPro);
                Toast.makeText(UserData.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        makeGetRequestForUser(id);
        getRequestsPosts();
    }

    void makeGetRequestForUser(String id){
        String url="https://gorest.co.in/public/v2/users/"+id;
        String token="b6f9f4bc7795e4aa1e6926f7e92910f4f4d32c8c9be442cb80b2dd482b9242ea";

        StringRequest request= new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getUserData(response);
                Log.e("new Response",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("new error....",""+error);
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
        RequestQueue queue = Volley.newRequestQueue(UserData.this);
        queue.add(request);
    }

    void getUserData(String obj)
    {
        JSONObject object;
        try {
            object= new JSONObject(obj);
            binding.textViewName.setText(object.getString("name"));
            name=object.getString("name");
            email=object.getString("email");
            gender=object.getString("gender");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
//  Get Users All Posts
    void getRequestsPosts()
    {
        String url = "https://gorest.co.in/public/v2/posts";
        String token="b6f9f4bc7795e4aa1e6926f7e92910f4f4d32c8c9be442cb80b2dd482b9242ea";

        Request request= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            //            @Override
            public void onResponse(JSONArray response) {
                Log.e("response",response.toString());
                getUserPosts(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserData.this, "Post Not Found", Toast.LENGTH_SHORT).show();
            }
        })                {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(UserData.this);
        queue.add(request);
    }

    void getUserPosts(JSONArray array)
    {
        for (int i=0;i<array.length();i++) {

            JSONObject object;
            try {
                object = array.getJSONObject(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                UserPostsClass userPostsClass = new UserPostsClass();
                userPostsClass.postId = object.getString("id");
                userPostsClass.userId = object.getString("user_id");
                userPostsClass.title = object.getString("title");
                userPostsClass.body = object.getString("body");

                arrayList.add(userPostsClass);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        NewAdapter adapter = new NewAdapter(arrayList);
        adapter.notifyDataSetChanged();
        
        binding.recyclerViewPost.setAdapter(adapter);

    }
}