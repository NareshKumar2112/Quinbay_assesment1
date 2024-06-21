package com.demo.pojo;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String category_name;
    private String name;
    private double cost;
    private int stock;
    private boolean soft_delete;
    private String seller_name;
    private Category category;

public Product()
{

}

//    public Product(int id,int category_id,String name, double price, int quantity, boolean softDelete,String seller_name) {
//    this.id=id;
//    this.category_id=category_id;
//    this.name=name;
//    this.stock=quantity;
//    this.cost=price;
//    this.soft_delete=softDelete;
//    this.seller_name=seller_name;
//    }


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isSoft_delete() {
        return soft_delete;
    }

    public void setSoft_delete(boolean soft_delete) {
        this.soft_delete = soft_delete;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

//    public static Product fromString(String line) {
//        try {
//            String[] parts = line.split(",");
//            int id = Integer.parseInt(parts[0]);
//            int category_id=Integer.parseInt(parts[1]);
//            String name = parts[2];
//            double price = Double.parseDouble(parts[3]);
//            int quantity = Integer.parseInt(parts[4]);
//            boolean soft_delete = Boolean.parseBoolean(parts[5]);
//            String seller_name=parts[6];
//            return new Product(id,category_id, name, price, quantity, soft_delete,seller_name);
//        } catch (NumberFormatException e) {
//            System.out.println("error");
//        }
//    return null;
//    }
}