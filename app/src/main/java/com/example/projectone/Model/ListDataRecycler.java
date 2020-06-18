package com.example.projectone.Model;

public class ListDataRecycler {
    private String description;
    private int imgId;
    private String title;
    private Class object;

    public ListDataRecycler(String title,int imgId,String description, Class o){
        this.description = description;
        this.imgId = imgId;
        this.title = title;
        this.object = o;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Class getObject() {
        return object;
    }

    public void setObject(Class object) {
        this.object = object;
    }

}
