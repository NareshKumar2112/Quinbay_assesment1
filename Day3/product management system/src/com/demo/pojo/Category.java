package com.demo.pojo;

public class Category {

    private int category_id;
    private String category_name;

    public Category(int categoryId, String categoryName) {
        this.category_id=categoryId;
        this.category_name=categoryName;
    }
    public Category()
    {

    }

    public static Category fromString(String line) {
        String arr[]=line.split(",");
        int category_id=Integer.parseInt(arr[0]);
        String category_name=arr[1];
        return new Category(category_id,category_name);
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
