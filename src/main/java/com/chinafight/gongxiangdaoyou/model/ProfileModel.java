package com.chinafight.gongxiangdaoyou.model;

public class ProfileModel {
    private Integer profile_id;
    private String profile_tag;
    private String profile_text;
    private Integer profile_type;
    private Integer profile_parentId;

    public Integer getProfile_type() {
        return profile_type;
    }

    public void setProfile_type(Integer profile_type) {
        this.profile_type = profile_type;
    }

    public Integer getProfile_parentId() {
        return profile_parentId;
    }

    public void setProfile_parentId(Integer profile_parentId) {
        this.profile_parentId = profile_parentId;
    }

    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Integer profile_id) {
        this.profile_id = profile_id;
    }

    public String getProfile_tag() {
        return profile_tag;
    }

    public void setProfile_tag(String profile_tag) {
        this.profile_tag = profile_tag;
    }

    public String getProfile_text() {
        return profile_text;
    }

    public void setProfile_text(String profile_text) {
        this.profile_text = profile_text;
    }
}
