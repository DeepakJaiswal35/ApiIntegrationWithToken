package com.example.apiintegrationwithtoken;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewHolder> {

    List<UserPostsClass> userPostsClassList;
    public NewAdapter() {
    }

    public NewAdapter(List<UserPostsClass> item) {
        this.userPostsClassList = item;
    }

    @NonNull
    @Override
    public NewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_of_user,null);
        return new NewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewHolder holder, int position) {
        UserPostsClass userPostsClass = userPostsClassList.get(position);
        holder.id.setText(userPostsClass.getPostId());
        holder.title.setText(userPostsClass.getTitle());
        holder.body.setText(userPostsClass.getBody());

    }

    @Override
    public int getItemCount() {
        return userPostsClassList.size();
    }
}

        class NewHolder extends RecyclerView.ViewHolder
        {
            TextView name;
            TextView id;
            TextView body;
            TextView title;

            public NewHolder(@NonNull View itemView) {
                super(itemView);
                id=itemView.findViewById(R.id.textViewPostId);
                title=itemView.findViewById(R.id.textViewUserTitle);
                body=itemView.findViewById(R.id.textViewUserBody);
            }
        }
