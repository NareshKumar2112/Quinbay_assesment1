package com.demo.services;

import com.demo.database.Database;
import com.demo.pojo.Orders;
import com.demo.pojo.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Shopping {

    private List<Orders> purchaseDatabase;
    private List<Product> ProductDatabase;
    private Scanner scanner;
    private Database database;
    private boolean flag;
    private double total_amount;
    public Shopping()
    {
        database=new Database();
        scanner=new Scanner(System.in);
    }
    public void purchaseThread()
    {
        ProductDatabase=database.readProductList1("products.txt");
        purchaseDatabase=database.readPurchaseList("orders.ser");
        int order_id=0;
        for(Orders order:purchaseDatabase)
        {
            order_id=Math.max(order_id,order.getOrder_id());
        }
        List<String>product_names=new ArrayList<String>();
        List<Double>product_cost=new ArrayList<Double>();
        List<Double>product_percost=new ArrayList<Double>();
        List<Integer>product_quantites=new ArrayList<Integer>();
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
                    flag=addItems(ProductDatabase, productId, product_names, product_percost, product_quantites, product_cost);
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

        Thread thread2=new Thread(()->
        {
            total_amount=displayBill(product_names,product_percost,product_quantites,product_cost);
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
        purchaseDatabase.add(order);
        database.writePurchaseList("orders.ser",purchaseDatabase);
        database.writePurchaseList1(purchaseDatabase);
        System.out.println("Thank you");
    }
    public void purchase()
    {
        ProductDatabase=database.readProductList1("products.txt");
        purchaseDatabase=database.readPurchaseList("orders.ser");
        int order_id=0;
        for(Orders order:purchaseDatabase)
        {
            order_id=Math.max(order_id,order.getOrder_id());
        }
        List<String>product_names=new ArrayList<String>();
        List<Double>product_cost=new ArrayList<Double>();
        List<Double>product_percost=new ArrayList<Double>();
        List<Integer>product_quantites=new ArrayList<Integer>();
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
                boolean flag=true;
                System.out.println("Enter the product id");
                int productId=scanner.nextInt();
                flag=addItems(ProductDatabase,productId,product_names,product_percost,product_quantites,product_cost);
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

        double total_amount=displayBill(product_names,product_percost,product_quantites,product_cost);
        order.setTotal_amount(total_amount);
        purchaseDatabase.add(order);
        database.writePurchaseList("orders.ser",purchaseDatabase);
        database.writePurchaseList1(purchaseDatabase);
        System.out.println("Thank you");
    }

    private boolean addItems(List<Product> productDatabase, int productId, List<String> productNames, List<Double> productPercost, List<Integer> productQuantites, List<Double> productCost) {
        flag=true;
        for(Product product:ProductDatabase)
        {
            if(product.getId()==productId&&!product.isSoft_delete()) {
                System.out.println("Enter the number of quantity");
                int quantity=scanner.nextInt();
                if(product.getStock()>=quantity) {
                    productNames.add(product.getName());
                    productPercost.add(product.getCost());
                    productQuantites.add(quantity);
                    productCost.add(product.getCost()*quantity);
                    product.setStock(product.getStock() - quantity);
                    System.out.println("Order placed successfully");
                    database.writeProductList1("products.txt",ProductDatabase);
                }
                else
                {
                    System.out.println("The availability of the product is "+product.getStock());
                }
                flag = false;
                break;
            }
        }
        return flag;

    }

    public double displayBill(List<String> product_names, List<Double> product_percost, List<Integer> product_quantites,List<Double> product_cost)
    {
        double total_amount=0;
        for(int i=0;i<product_names.size();i++)
        {
            total_amount=total_amount+product_cost.get(i);
            System.out.println(product_names.get(i)+" "+product_percost.get(i)+" "+product_quantites.get(i)+" "+product_cost.get(i));
        }
        System.out.println("The total amount is "+total_amount);
        return total_amount;
    }

    public void purchaseHistory()
    {
        purchaseDatabase=database.readPurchaseList("orders.ser");
        for(Orders order:purchaseDatabase)
        {
            System.out.println("orderId ->"+order.getOrder_id()+" \n"+"product name ->"+order.getProduct_names().toString()+" \n"+"product percost ->"+order.getProduct_percost().toString()+"\n"+
            "product quantity ->"+order.getProduct_quantities().toString()+" \n"+"product cost ->"+order.getProduct_costs().toString()+" \n"+"total amount ->"+order.getTotal_amount());
            System.out.println("---------------");
        }
    }
}
