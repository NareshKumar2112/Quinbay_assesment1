package com.demo.services;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.demo.database.Database;
import com.demo.database.MongoDb;
import com.demo.pojo.Category;
import com.demo.pojo.Product;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.sql.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Operator {

    private List<Product> ProductDatabase;
    private List<Category> categoryDatabase;
    private Scanner scanner;
    private Database database;
    private boolean check_avaliblity;
    private MongoDb mongodb;
    private MongoCollection mongoCollection;
    public Operator()
    {
        scanner=new Scanner(System.in);
        this.database=new Database();
        this.mongodb=new MongoDb();
    }
    public void addProduct()
    {
            System.out.println("Enter the product name");
            String name=scanner.nextLine();
            System.out.println("Enter the product price");
            double cost=Double.parseDouble(scanner.nextLine());
            System.out.println("Enter the product stock");
            int stock=Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the seller's name");
            String seller_name=scanner.nextLine();


        MongoDatabase categorydatabase=mongodb.getMongoDatabase();
        MongoCollection mongo_category_Collection=categorydatabase.getCollection("category");

        MongoCursor<Document> cursor1=mongo_category_Collection.find().iterator();
        while(cursor1.hasNext())
        {
            Document document=cursor1.next();
            System.out.println(document.getString("category_name"));
        }
            System.out.println("Enter the category listed above, Default as others" );
            String category_name=scanner.nextLine();
            cursor1=mongo_category_Collection.find().iterator();
            boolean flag=false;
            while(cursor1.hasNext())
            {
                Document document=cursor1.next();
                if(category_name.toLowerCase().equals(document.getString("category_name")))
                {
                    flag=true;
                }
            }
            if(!flag)
            {
                category_name="others";
            }

            int productId=0;
            mongodb=new MongoDb();
            mongoCollection=mongodb.getMongoCollection();
            MongoCursor<Document> cursor=mongoCollection.find().iterator();
            while(cursor.hasNext())
            {
                Document document=cursor.next();
                productId=Math.max(productId,document.getInteger("product_id"));
            }
            Product product = new Product();
            product.setId(productId+1);
            if(cost>=1 && name.length()>=2&&stock>=1&&seller_name.length()>=2) {
                product.setCost(cost);
                product.setName(name);
                product.setStock(stock);
                product.setSoft_delete(false);
                product.setSeller_name(seller_name);
                product.setCategory_name(category_name);
                mongoCollection.insertOne(new Document().append("product_id",product.getId())
                        .append("product_name", product.getName())
                        .append("product_cost", product.getCost())
                        .append("product_stock", product.getStock())
                        .append("product_soft_delete", product.isSoft_delete())
                        .append("product_seller_name",product.getSeller_name())
                        .append("product_category",product.getCategory_name())
                );
                System.out.println("Product is added successfully");
            }
            else {
                System.out.println("Invalid input");
            }
    }
    public void viewAllProduct()
    {
            mongoCollection=mongodb.getMongoCollection();
            MongoCursor<Document> cursor=mongoCollection.find().iterator();
            if(!cursor.hasNext())
            {
                System.out.println("No products available");;
            }
            else {
                System.out.println("Id     Name   price   stock  delete_status   seller_name     category_name");
                while(cursor.hasNext())
                {
                    Document document=cursor.next();
                    System.out.println(document.getInteger("product_id")+" "+document.getString("product_name")+" "+document.getDouble("product_cost")
                    +" "+document.getInteger("product_stock")+" "+document.getBoolean("product_soft_delete")+" "+document.getString("product_seller_name")+" "+document.getString("product_category"));
                }

            }
    }
    public void viewProduct()
    {
        System.out.println("Enter the product id you want me to show");
        int productId=scanner.nextInt();

        boolean flag=true;

        mongoCollection=mongodb.getMongoCollection();
        MongoCursor<Document> cursor=mongoCollection.find().iterator();
        if(!cursor.hasNext())
        {
            System.out.println("No products available");;
        }
        else {
            while(cursor.hasNext())
            {
                Document document=cursor.next();
                if(document.getInteger("product_id")==productId) {
                    System.out.println("Id     Name   price   stock  delete_status   seller_name");
                    System.out.println(document.getInteger("product_id") + " " + document.getString("product_name") + " " + document.getDouble("product_cost")
                            + " " + document.getInteger("product_stock") + " " + document.getBoolean("product_soft_delete") + " " + document.getString("product_seller_name"));
                    flag=false;
                }
            }

        }
        if(flag)
        {
            System.out.println("The product is not available");
        }
    }
    public synchronized boolean checkExistanceOfProduct(int productId)
    {
        check_avaliblity=true;

        mongoCollection=mongodb.getMongoCollection();
        MongoCursor<Document> cursor=mongoCollection.find().iterator();

            while(cursor.hasNext())
            {
                Document document=cursor.next();
                if(document.getInteger("product_id")==productId&& !document.getBoolean("product_soft_delete")) {
//                    updatedproduct.setId(document.getInteger("product_id"));
//                    updatedproduct.setName(document.getString("product_name"));
//                    updatedproduct.setCost(document.getDouble("product_cost"));
//                    updatedproduct.setStock(document.getInteger("product_stock"));
//                    updatedproduct.setSoft_delete(document.getBoolean("product_soft_delete"));
//                    updatedproduct.setSeller_name(document.getString("product_seller_name"));

                    return false;
//                    return updatedproduct;
                }
            }

       return true;

    }
    public void updateStockThread()
    {
        System.out.println("Enter the product id you want to update");
        int productId=scanner.nextInt();
        System.out.println("Enter the new stock value ");
        int new_stockValue=scanner.nextInt();

        Thread validate_thread=new Thread(()-> {
            this.check_avaliblity=checkExistanceOfProduct(productId);
        }
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

        if(check_avaliblity)
        {
            System.out.println("The product is not available");
        }
        else {
            if (new_stockValue >= 0) {
                    mongoCollection = mongodb.getMongoCollection();
                    Document filter = new Document("product_id", productId);
                    Document update = new Document("$set", new Document("product_stock", new_stockValue));
                    mongoCollection.updateOne(filter, update);
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
        double new_priceValue=scanner.nextDouble();
        Thread validate_thread=new Thread(()->
                check_avaliblity=checkExistanceOfProduct(productId)
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

    public synchronized void updateProductPrice(int productId,double new_priceValue) {

        if(check_avaliblity)
        {
            System.out.println("The product is not available");
        }
        else {
            if (new_priceValue >= 0) {

                mongoCollection=mongodb.getMongoCollection();
                Document filter=new Document("product_id",productId);
//                System.out.println(filter.getString("ProductName"))
                Document update=new Document("$set",new Document("product_cost",new_priceValue));
                mongoCollection.updateOne(filter,update);
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
            mongoCollection=mongodb.getMongoCollection();
            Document filter=new Document("product_id",productId);
            Document update=new Document("$set",new Document("product_soft_delete",false));
            mongoCollection.updateOne(filter,update);
            System.out.println("updated successfully");

    }
    public void updateNameThread()
    {
        System.out.println("Enter the product id you want to update");
        int productId=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new name value ");
        String new_nameValue=scanner.nextLine();
        Thread validate_thread=new Thread(()->
                check_avaliblity=checkExistanceOfProduct(productId)
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
        if(check_avaliblity)
        {
            System.out.println("The product is not available");
        }
        else {
            if (new_productName.length() >= 2) {
                mongoCollection=mongodb.getMongoCollection();
                Document filter=new Document("product_id",productId);
//                System.out.println(filter.getString("ProductName"))
                Document update=new Document("$set",new Document("product_name",new_productName));
                mongoCollection.updateOne(filter,update);
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
        if(checkExistanceOfProduct(productId))
        {
            System.out.println("product is not available");
        }
        else {
            mongoCollection = mongodb.getMongoCollection();
            Document filter = new Document("product_id", productId);
            Document update = new Document("$set", new Document("product_soft_delete", true));
            mongoCollection.updateOne(filter, update);
            System.out.println("Deleted successfully");
        }
    }

    public void addCategory() {
        System.out.println("Enter the category name");

        String category_name=scanner.nextLine();
        MongoDatabase categorydatabase=mongodb.getMongoDatabase();
        MongoCollection mongo_category_Collection=categorydatabase.getCollection("category");

        int categoryId=0;
        MongoCursor<Document> cursor=mongo_category_Collection.find().iterator();
        while(cursor.hasNext())
        {
            Document document=cursor.next();
            categoryId=Math.max(categoryId,document.getInteger("category_id"));
        }
        Category category=new Category();
        category.setCategory_id(categoryId);
        category.setCategory_name(category_name);
        mongo_category_Collection.insertOne(new Document().append("category_id",category.getCategory_id()).append("category_name",category.getCategory_name()));
        System.out.println("Category is added successfully");
    }

    public void viewProductByCategory()
    {
        System.out.println("Enter the category name");
        String category_name=scanner.nextLine();
        mongoCollection=mongodb.getMongoCollection();
        MongoCursor<Document> cursor=mongoCollection.find().iterator();
        while(cursor.hasNext())
        {
            Document document=cursor.next();
            if(document.getString("product_category").equals(category_name.toLowerCase()))
            {
                System.out.println(document.getInteger("product_id")+" "+document.getString("product_name")+" "+document.getDouble("product_cost")
                +" "+document.getInteger("product_stock")+" "+document.getString("product_seller_name"));
            }
        }
    }
}
