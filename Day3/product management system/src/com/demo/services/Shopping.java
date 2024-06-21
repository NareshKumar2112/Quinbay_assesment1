package com.demo.services;

import com.demo.database.Database;
import com.demo.database.MongoDb;
import com.demo.database.PostgresDatabase;
import com.demo.pojo.Orders;
import com.demo.pojo.Product;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Shopping {

    private List<Orders> purchaseDatabase;
    private List<Product> ProductDatabase;
    private Scanner scanner;
    private Database database;
    private boolean flag;
    private PostgresDatabase postgresdatabase;
    private double total_amount;
    private Connection connection;
    public Shopping()
    {
        database=new Database();
        scanner=new Scanner(System.in);
        this.postgresdatabase=new PostgresDatabase();
    }
    public void purchaseThread()
    {
        int order_id=0;
        connection= postgresdatabase.getConnection();
        try {
            Statement st = connection.createStatement();
            String query = "select * from orders";
            ResultSet resultset = st.executeQuery(query);
            while(resultset.next())
            {
                order_id=Math.max(order_id,resultset.getInt("order_id"));
            }
        }
        catch(Exception exception)
        {
            System.out.println("error");
        }

        List<String>product_names=new ArrayList<String>();
        List<Double>product_cost=new ArrayList<Double>();
        List<Double>product_percost=new ArrayList<Double>();
        List<Integer>product_quantites=new ArrayList<Integer>();
        List<String>product_suppliers=new ArrayList<String>();
        Orders order=new Orders();
        order.setOrder_id(order_id+1);
        while(true)
        {
            System.out.println("You want add item (yes/no)");
            String option=scanner.next().toLowerCase();
            if(option.equals("no"))
            {
                System.out.println("Thank you for your shopping");
                break;
            }
            else
            {
                System.out.println("Enter the product id");
                int productId=scanner.nextInt();
                Thread thread1=new Thread(()-> {
                    flag=addItems(ProductDatabase, productId, product_names, product_percost, product_quantites, product_cost,product_suppliers);
                });
                thread1.start();
                try
                {
                    thread1.join();
                }
                catch (Exception e)
                {
                    System.out.println("thread1 error");
                }
                if(flag)
                {
                    System.out.println("The product is not available");
                }
            }
        }
        order.setProduct_names(product_names);
        order.setProduct_costs(product_cost);
        order.setProduct_percost(product_percost);
        order.setProduct_quantities(product_quantites);
        order.setProduct_suppilers(product_suppliers);
        Thread thread2=new Thread(()->
        {
            total_amount=displayBill(product_names,product_percost,product_quantites,product_cost,product_suppliers);
        });
        thread2.start();
        try
        {
            thread2.join();
        }
        catch (Exception e)
        {
            System.out.println("thread2 error");
        }
        order.setTotal_amount(total_amount);
        connection= postgresdatabase.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("insert into orders(order_id,total_amount,number_of_product) values(?,?,?)");
            pst.setInt(1,order.getOrder_id());
            pst.setDouble(2,order.getTotal_amount());
            pst.setInt(3,order.getProduct_names().size());
            pst.executeUpdate();
            PreparedStatement pst2=connection.prepareStatement("insert into ordered_items(order_id,product_percost," +
                    "product_names,supplier_name,product_cost,product_quantity)values(?,?,?,?,?,?)");
            for(int i=0;i<order.getProduct_names().size();i++)
            {
                pst2.setInt(1,order.getOrder_id());
                pst2.setDouble(2,order.getProduct_percost().get(i));
                pst2.setString(3,order.getProduct_names().get(i));
                pst2.setString(4,order.getProduct_suppilers().get(i));
                pst2.setDouble(5,order.getProduct_costs().get(i));
                pst2.setInt(6,order.getProduct_quantities().get(i));
                pst2.executeUpdate();
            }
        }
        catch (Exception exception)
        {
            System.out.println("error");
        }
//        purchaseDatabase.add(order);
//        database.writePurchaseList("orders.ser",purchaseDatabase);
//        database.writePurchaseList1(purchaseDatabase);

        System.out.println("Thank you");
    }
//    public void purchase()
//    {
//        ProductDatabase=database.readProductList1("products.txt");
//        purchaseDatabase=database.readPurchaseList("orders.ser");
//        int order_id=0;
//        for(Orders order:purchaseDatabase)
//        {
//            order_id=Math.max(order_id,order.getOrder_id());
//        }
//        List<String>product_names=new ArrayList<String>();
//        List<Double>product_cost=new ArrayList<Double>();
//        List<Double>product_percost=new ArrayList<Double>();
//        List<Integer>product_quantites=new ArrayList<Integer>();
//        List<String>product_suppliers=new ArrayList<String>();
//        Orders order=new Orders();
//        order.setOrder_id(order_id+1);
//        while(true)
//        {
//            System.out.println("You want add item (yes/no)");
//            String option=scanner.next().toLowerCase();
//            if(option.equals("no"))
//            {
//                System.out.println("Thank you for your shopping");
//                break;
//            }
//            else
//            {
//                boolean flag=true;
//                System.out.println("Enter the product id");
//                int productId=scanner.nextInt();
//                flag=addItems(ProductDatabase,productId,product_names,product_percost,product_quantites,product_cost,product_suppliers);
//                if(flag)
//                {
//                    System.out.println("The product is not available");
//                }
//            }
//        }
//        order.setProduct_names(product_names);
//        order.setProduct_costs(product_cost);
//        order.setProduct_percost(product_percost);
//        order.setProduct_quantities(product_quantites);
//        order.setProduct_suppilers(product_suppliers);
//
//        double total_amount=displayBill(product_names,product_percost,product_quantites,product_cost,product_suppliers);
//        order.setTotal_amount(total_amount);
//        purchaseDatabase.add(order);
//        database.writePurchaseList("orders.ser",purchaseDatabase);
//        database.writePurchaseList1(purchaseDatabase);
//        System.out.println("Thank you");
//    }

    private boolean addItems(List<Product> productDatabase, int productId, List<String> productNames, List<Double> productPercost, List<Integer> productQuantites, List<Double> productCost,List<String> product_supplier) {
        flag=true;
        MongoDb mongodb=new MongoDb();
        MongoCollection mongoCollection= mongodb.getMongoCollection();
        MongoCursor<Document> cursor=mongoCollection.find().iterator();
        while(cursor.hasNext())
        {
            Document document=cursor.next();
            if(document.getInteger("product_id")==productId&&!document.getBoolean("product_soft_delete")) {
                System.out.println("Enter the number of quantity");
                int quantity=scanner.nextInt();
                if(document.getInteger("product_stock")>=quantity) {
                    productNames.add(document.getString("product_name"));
                    productPercost.add(document.getDouble("product_cost"));
                    productQuantites.add(quantity);
                    productCost.add(document.getDouble("product_cost")*quantity);
                    Document filter=new Document("product_id",productId);
                    Document update=new Document("$set",new Document("product_stock",(document.getInteger("product_stock") - quantity)));
//                    product.setStock(document.getInteger("product_stock") - quantity);
                    product_supplier.add(document.getString("product_seller_name"));
                    System.out.println("Order placed successfully");
                    mongoCollection.updateOne(filter,update);
                }
                else
                {
                    System.out.println("The availability of the product is "+document.getInteger("product_stock"));
                }
                flag = false;
                break;
            }
        }
        return flag;

    }

    public double displayBill(List<String> product_names, List<Double> product_percost, List<Integer> product_quantites,List<Double> product_cost,List<String>product_suppliers)
    {
        double total_amount=0;
        for(int i=0;i<product_names.size();i++)
        {
            total_amount=total_amount+product_cost.get(i);
            System.out.println(product_names.get(i)+" "+product_percost.get(i)+" "+product_quantites.get(i)+" "+product_cost.get(i)+" "+product_suppliers.get(i));
        }
        System.out.println("The total amount is "+total_amount);
        return total_amount;
    }

    public void purchaseHistory()
    {
        connection= postgresdatabase.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("select * from orders");
            ResultSet resultSet=pst.executeQuery();
            while(resultSet.next())
            {
                System.out.println("order_id     total_amount     number_of_product");
                System.out.println(resultSet.getInt("order_id")+" "+resultSet.getDouble("total_amount")+" "+resultSet.getInt("number_of_product"));
//                System.out.println("Enter y to get detailed info");
//                String cho=scanner.next();
//                if(cho.equals("y"))
//                {
//                    PreparedStatement pst2=connection.prepareStatement("select * from ordered_items where order_id=?");
//                    pst2.setInt(1,resultSet.getInt("order_id"));
//                    ResultSet rs2=pst2.executeQuery();
//                    System.out.println("product_name     product_quantity      product_percost        product_cost     supplier_name");
//                    while(rs2.next())
//                    {
//                        System.out.println(rs2.getString("product_names")+" "+rs2.getInt("product_quantity")
//                        +" "+rs2.getDouble("product_percost")+" "+rs2.getDouble("product_cost")+" "+rs2.getString("supplier_name"));
//                    }
//                }
            }
        }
        catch(Exception e)
        {
            System.out.println("error");
        }
    }
    public void view_ordersById() {
        System.out.println("Enter the order_id to view the detailed order info");
        int order_id=scanner.nextInt();
        connection = postgresdatabase.getConnection();
        try {
            PreparedStatement pst2=connection.prepareStatement("select * from ordered_items where order_id=?");
            pst2.setInt(1,order_id);
            ResultSet rs2=pst2.executeQuery();
            System.out.println("product_name     product_quantity      product_percost        product_cost   ");
            while(rs2.next())
            {
                System.out.println(rs2.getString("product_names")+" "+rs2.getInt("product_quantity") +" "+rs2.getDouble("product_percost")+" "+rs2.getDouble("product_cost"));
            }
        }
        catch (Exception exception)
        {
            System.out.println("error");
        }
    }
}
