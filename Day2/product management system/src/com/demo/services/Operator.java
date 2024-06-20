package com.demo.services;

import com.demo.database.Database;
import com.demo.pojo.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Operator {

    private List<Product> ProductDatabase;
    private Scanner scanner;
    private Database database;
    private Product updateproduct;
    public Operator()
    {
        scanner=new Scanner(System.in);
        this.database=new Database();
    }
    public void addProduct()
    {
            System.out.println("Enter the product name");
            String name=scanner.nextLine();
            System.out.println("Enter the product price");
            double cost=Double.parseDouble(scanner.nextLine());

            System.out.println("Enter the product stock");
            int stock=Integer.parseInt(scanner.nextLine());
            System.out.println(name+" "+cost+" "+stock);
            ProductDatabase=database.readProductList1("products.txt");
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
                database.writeProductList1("products.txt", ProductDatabase);
            }
            else {
                System.out.println("Invalid input");
            }
    }
    public void viewAllProduct()
    {
            ProductDatabase=database.readProductList1("products.txt");
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
    public void viewProduct()
    {
        System.out.println("Enter the product id you want me to show");
        int productId=scanner.nextInt();
        ProductDatabase=database.readProductList1("products.txt");
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
    public synchronized Product checkExistanceOfProduct(int productId)
    {
        ProductDatabase=database.readProductList1("products.txt");
        boolean flag=true;

        for(Product product:ProductDatabase)
        {
            if(product.getId()==productId&&!product.isSoft_delete()) {
                flag=false;
                updateproduct=product;
                break;
            }
        }
        if(!flag) {
            return updateproduct;
        }else {
            return null;
        }
    }
    public void updateStockThread()
    {
        System.out.println("Enter the product id you want to update");
        int productId=scanner.nextInt();
        System.out.println("Enter the new stock value ");
        int new_stockValue=scanner.nextInt();
        Thread validate_thread=new Thread(()->
                checkExistanceOfProduct(productId)
        );

        Thread update_thread=new Thread(()->
                updateProductStock(productId,new_stockValue));

        validate_thread.start();
        update_thread.start();
        try
        {
            update_thread.join();
            validate_thread.join();
        }
        catch (InterruptedException interrupt_exception)
        {
            System.out.println("exception in thread");
        }
    }
    public synchronized void updateProductStock(int productId,int new_stockValue) {

        System.out.println(updateproduct);
        if(updateproduct==null)
        {
            System.out.println("The product is not available");
        }
        else {
            if (new_stockValue >= 0) {
                updateproduct.setStock(new_stockValue);
                database.writeProductList1("products.txt", ProductDatabase);
                System.out.println("Stock is updated successfully");
            }
            else {
                System.out.println("Invalid value");
            }
        }
    }
    public void updatePriceThread()
    {
        System.out.println("Enter the product id you want to update");
        int productId=scanner.nextInt();
        System.out.println("Enter the new price value ");
        int new_priceValue=scanner.nextInt();
        Thread validate_thread=new Thread(()->
                checkExistanceOfProduct(productId)
        );

        Thread update_thread=new Thread(()->
                updateProductPrice(productId,new_priceValue));

        validate_thread.start();
        update_thread.start();
        try
        {
            update_thread.join();
            validate_thread.join();
        }
        catch (InterruptedException interrupt_exception)
        {
            System.out.println("exception in thread");
        }
    }

    public synchronized void updateProductPrice(int productId,int new_priceValue) {

        if(updateproduct==null)
        {
            System.out.println("The product is not available");
        }
        else {
            if (new_priceValue >= 0) {
                updateproduct.setCost(new_priceValue);
                database.writeProductList1("products.txt", ProductDatabase);
                System.out.println("price is updated successfully");
            } else {
                System.out.println("Invalid input");
            }
        }
    }
    public void updateSoftDelete()
    {
        System.out.println("Enter the product id you want to update");
        int productId=scanner.nextInt();
        ProductDatabase=database.readProductList1("products.txt");
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
                database.writeProductList1("products.txt", ProductDatabase);
                System.out.println("deleted product is updated successfully");
            } else {
                System.out.println("Product is already exit");
            }
        }
    }
    public void updateNameThread()
    {
        System.out.println("Enter the product id you want to update");
        int productId=scanner.nextInt();
        System.out.println("Enter the new name value ");
        String new_nameValue=scanner.next();
        Thread validate_thread=new Thread(()->
                checkExistanceOfProduct(productId)
        );

        Thread update_thread=new Thread(()->
                updateProductName(productId,new_nameValue));

        validate_thread.start();
        update_thread.start();
        try
        {
            update_thread.join();
            validate_thread.join();
        }
        catch (InterruptedException interrupt_exception)
        {
            System.out.println("exception in thread");
        }
    }
    public synchronized void updateProductName(int productId,String new_productName)
    {
        if(updateproduct==null)
        {
            System.out.println("The product is not available");
        }
        else {
            if (new_productName.length() >= 2) {
                updateproduct.setName(new_productName);
                database.writeProductList1("products.txt", ProductDatabase);
                System.out.println("product name is updated successfully");
            } else {
                System.out.println("Invalid input");
            }
        }
    }
    public void deleteProduct()
    {

        System.out.println("Enter the product id you want to delete");
        int productId=scanner.nextInt();
        ProductDatabase=database.readProductList1("products.txt");
        boolean flag=true;
        for(Product product:ProductDatabase)
        {
            if(product.getId()==productId) {
                flag=false;
                product.setSoft_delete(true);
                database.writeProductList1("products.txt",ProductDatabase);
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
