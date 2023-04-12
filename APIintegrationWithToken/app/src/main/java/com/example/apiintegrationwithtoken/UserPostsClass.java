package com.example.apiintegrationwithtoken;

import java.io.Serializable;

public class UserPostsClass implements Serializable {

    public UserPostsClass() {

    }

    String postId="";
    String userId="";
    String title="";
    String body="";

    public UserPostsClass(String postId, String userId, String title, String body) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "postId:- "+postId+" user_Id:- " + userId+" Title:- " + title+" " +
                "" +
                "Body:- " + body;
    }
}
