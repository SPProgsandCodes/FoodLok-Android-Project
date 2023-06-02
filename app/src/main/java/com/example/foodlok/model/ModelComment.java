package com.example.foodlok.model;

import android.widget.ImageView;

public class ModelComment {

    public ImageView getCommentUserProfile() {
        return commentUserProfile;
    }

    public void setCommentUserProfile(ImageView commentUserProfile) {
        this.commentUserProfile = commentUserProfile;
    }

    ImageView commentUserProfile;

    public ModelComment(ImageView commentUserProfile, String commentBody, long commentedAt) {
        this.commentUserProfile = commentUserProfile;
        this.commentBody = commentBody;
        this.commentedAt = commentedAt;
    }

    private String commentBody;
    private long commentedAt;
    private String commentedBy;

    public ModelComment() {
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public long getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(long commentedAt) {
        this.commentedAt = commentedAt;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public ModelComment(String commentBody, long commentedAt, String commentedBy) {
        this.commentBody = commentBody;
        this.commentedAt = commentedAt;
        this.commentedBy = commentedBy;
    }
}
