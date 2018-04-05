package com.tql.wll.recycleraviewtest;

/**
 * Created by Administrator on 2018/3/8.
 */

public class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name, int imgId)
    {
        this.name = name;
        imageId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
