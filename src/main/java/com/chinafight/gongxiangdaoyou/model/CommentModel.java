package com.chinafight.gongxiangdaoyou.model;

public class CommentModel {
    private Integer comment_id;
    private String comment_content;
    private String comment_user;
    private String comment_img;
    private Long comment_time;

    @Override
    public String toString() {
        return "CommentModel{" +
                "comment_id=" + comment_id +
                ", comment_content='" + comment_content + '\'' +
                ", comment_user='" + comment_user + '\'' +
                ", comment_img='" + comment_img + '\'' +
                ", comment_time=" + comment_time +
                '}';
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_user() {
        return comment_user;
    }

    public void setComment_user(String comment_user) {
        this.comment_user = comment_user;
    }

    public String getComment_img() {
        return comment_img;
    }

    public void setComment_img(String comment_img) {
        this.comment_img = comment_img;
    }

    public Long getComment_time() {
        return comment_time;
    }

    public void setComment_time(Long comment_time) {
        this.comment_time = comment_time;
    }
}
