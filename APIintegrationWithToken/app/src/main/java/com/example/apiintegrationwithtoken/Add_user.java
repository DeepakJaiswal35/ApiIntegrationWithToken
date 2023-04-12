package com.example.apiintegrationwithtoken;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiintegrationwithtoken.databinding.ActivityAddUserBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_user extends AppCompatActivity {

    ActivityAddUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String name= binding.editTextName.getText().toString();
              String email = binding.editTextEmail.getText().toString();
              String gender = binding.editTextGender.getText().toString();
                String status="active";

                JSONObject jObject= new JSONObject();
                try {
                    jObject.put("name",name);
                    jObject.put("email",email);
                    jObject.put("gender",gender);
                    jObject.put("status",status);

                    makePostRequest(jObject);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void makePostRequest(JSONObject object)
    {
        String url="https://gorest.co.in/public/v2/users";
        String token="b6f9f4bc7795e4aa1e6926f7e92910f4f4d32c8c9be442cb80b2dd482b9242ea";

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(Add_user.this, ""+response.getInt("id"), Toast.LENGTH_SHORT).show();
                    Log.e("response",""+response.getInt("id"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
//                try {
//                    setResult(201,new Intent().putExtra("response",response.getInt("id")));
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_user.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("error response",error.toString());
                finish();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params= new HashMap<>();
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(Add_user.this);
        queue.add(request);
    }
}