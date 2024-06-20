package com.demo.pojo;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private double cost;
    private int stock;
    private boolean soft_delete;

public Product()
{

}

    public Product(int id, String name, double price, int quantity, boolean softDelete) {
    this.id=id;
    this.name=name;
    this.stock=quantity;
    this.cost=price;
    this.soft_delete=softDelete;
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

    public static Product fromString(String line) {
        try {
            String[] parts = line.split(",");

            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            double price = Double.parseDouble(parts[2]);
            int quantity = Integer.parseInt(parts[3]);
            boolean soft_delete = Boolean.parseBoolean(parts[4]);
//            System.out.println(id+" "+namame+" "+price+" "+quantity+" "+soft_delete);
            return new Product(id, name, price, quantity, soft_delete);
        } catch (NumberFormatException e) {
            System.out.println("error");
        }
    return null;
    }
}
