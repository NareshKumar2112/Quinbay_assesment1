package com.demo.services;

import com.demo.database.Database;
import com.demo.pojo.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operator {

    private List<Product> ProductDatabase;
    private Scanner scanner;
    private Database database;
    public Operator()
    {
        scanner=new Scanner(System.in);
        this.database=new Database();
    }
    public void addProduct(String name,double cost,int stock)
    {

            ProductDatabase=database.readProductList("products.ser");
            int productId=0;
            for(Product product:ProductDatabase)
            {
                productId=Math.max(productId,product.getId());
            }
            Product product = new Product();
            product.setId(productId+1);
            if(cost>=1 && name.length()>=2&&stock>=1) {
                product.setCost(cost);
                product.setName(name);
                product.setStock(stock);
                product.setSoft_delete(false);
                ProductDatabase.add(product);
                System.out.println("Product is added successfully");
                database.writeProductList("products.ser", ProductDatabase);
            }
            else {
                System.out.println("Invalid input");
            }
    }
    public void viewProduct()
    {
            ProductDatabase=database.readProductList("products.ser");
            if(ProductDatabase.size()==0)
            {
                System.out.println("No products available");;
            }
            else {
                System.out.println("Id     Name   price   stock  delete_status");
                for (Product product : ProductDatabase) {

                        System.out.println(product.getId() + " " + product.getName() + " " + product.getCost() + " " + product.getStock()+" "+product.isSoft_delete());
                }
            }

    }
    public void viewProduct(int productId)
    {
        ProductDatabase=database.readProductList("products.ser");
        boolean flag=true;
        for(Product product:ProductDatabase)
        {
            if(product.getId()==productId) {
                flag=false;
                System.out.println("Id     Name   price   stock  delete_status");
                System.out.println(product.getId()+" "+product.getName() + " " + product.getCost() + " " + product.getStock()+" "+product.isSoft_delete());
            }
        }
        if(flag)
        {
            System.out.println("The product is not available");
        }
    }

    public void updateProductStock(int productId,int new_stockValue) {

        ProductDatabase=database.readProductList("products.ser");
        boolean flag=true;
        Product updateproduct=null;
        for(Product product:ProductDatabase)
        {
            if(product.getId()==productId&&!product.isSoft_delete()) {
                flag=false;
                updateproduct=product;
                break;
            }
        }
        if(flag)
        {
            System.out.println("The product is not available");
        }
        else {
            if (new_stockValue >= 0) {
                updateproduct.setStock(new_stockValue);
                database.writeProductList("products.ser", ProductDatabase);
                System.out.println("Stock is updated successfully");
            }
            else {
                System.out.println("Invalid value");
            }
        }
    }
    public void updateProductPrice(int productId,int new_priceValue) {

        ProductDatabase=database.readProductList("products.ser");
        boolean flag=true;
        Product updateproduct=null;
        for(Product product:ProductDatabase)
        {
            if(product.getId()==productId&&!product.isSoft_delete()) {
                updateproduct=product;
                flag=false;
                break;
            }
        }
        if(flag)
        {
            System.out.println("The product is not available");
        }
        else {
            if (new_priceValue >= 0) {
                updateproduct.setCost(new_priceValue);
                database.writeProductList("products.ser", ProductDatabase);
                System.out.println("price is updated successfully");
            } else {
                System.out.println("Invalid input");
            }
        }
    }
    public void updateSoftDelete(int productId)
    {
        ProductDatabase=database.readProductList("products.ser");
        boolean flag=true;
        Product updateproduct=null;
        for(Product product:ProductDatabase)
        {
            if(product.getId()==productId) {
                updateproduct=product;
                flag=false;
                break;
            }
        }
        if(flag)
        {
            System.out.println("product is not available");
        }
        else {
            if (!flag && updateproduct.isSoft_delete()) {
                updateproduct.setSoft_delete(false);
                database.writeProductList("products.ser", ProductDatabase);
                System.out.println("deleted product is updated successfully");
            } else {
                System.out.println("Product is already exit");
            }
        }
    }
    public void updateProductName(int productId,String new_productName)
    {

        ProductDatabase=database.readProductList("products.ser");
        boolean flag=true;
        Product updateproduct=null;
        for(Product product:ProductDatabase)
        {
            if(product.getId()==productId&&!product.isSoft_delete()) {
                updateproduct=product;
                flag=false;
                break;
            }
        }
        if(flag)
        {
            System.out.println("The product is not available");
        }
        else {
            if (new_productName.length() >= 2) {
                updateproduct.setName(new_productName);
                database.writeProductList("products.ser", ProductDatabase);
                System.out.println("product name is updated successfully");
            } else {
                System.out.println("Invalid input");
            }
        }
    }
    public void deleteProduct(int productId)
    {
        ProductDatabase=database.readProductList("products.ser");
        boolean flag=true;
        for(Product product:ProductDatabase)
        {
            if(product.getId()==productId) {
                flag=false;
                product.setSoft_delete(true);
                database.writeProductList("products.ser",ProductDatabase);
                System.out.println("product is deleted successfully");
                break;
            }
        }
        if(flag)
        {
            System.out.println("product is not available");
        }
    }
}
