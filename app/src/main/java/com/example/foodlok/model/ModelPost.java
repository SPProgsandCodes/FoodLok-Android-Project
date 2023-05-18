package com.example.foodlok.model;

public class ModelPost {
    private String postID;
    private String postImage;
    private String postedBy;
    private String postDescription;
    private String postedAt;
    private int postLike;
    private String postLikedBy;

    public String getPostLikedBy() {
        return postLikedBy;
    }

    public void setPostLikedBy(String postLikedBy) {
        this.postLikedBy = postLikedBy;
    }

    public ModelPost(String postID, String postImage, String postedBy, String postDescription, String postedAt) {
        this.postID = postID;
        this.postImage = postImage;
        this.postedBy = postedBy;
        this.postDescription = postDescription;
        this.postedAt = postedAt;
    }


    public int getPostLike() {
        return postLike;
    }

    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }

    public ModelPost() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }
}
