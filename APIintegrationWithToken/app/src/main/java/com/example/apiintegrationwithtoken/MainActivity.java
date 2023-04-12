package com.example.apiintegrationwithtoken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.example.apiintegrationwithtoken.databinding.ActivityMainBinding;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,Add_user.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = binding.editLogId.getText().toString();
                makeGetRequest(id);
                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void  makeGetRequest(String id)
        {
            String url = "https://gorest.co.in/public/v2/users/"+id;

            String token="b6f9f4bc7795e4aa1e6926f7e92910f4f4d32c8c9be442cb80b2dd482b9242ea";
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    parshJson(response);
                    Log.e("response..",response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, "user not Found", Toast.LENGTH_SHORT).show();
                }
            })
           {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer " + token);
                    return params;
                }
            };
            RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
            queue.add(request);
        }
        void parshJson(String obj)
        {
            JSONObject object;
            String id;
            try {
                 object = new JSONObject(obj);
                 id=object.getString("id");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Intent next = new Intent(MainActivity.this,UserData.class);
            next.putExtra("id",id);
            startActivity(next);
        }
}