package com.demo.controller;

import com.demo.pojo.Product;
import com.demo.services.Operator;
import com.demo.services.Shopping;

import java.util.*;
public class Controller implements Runnable{

    private Operator operator;
    private Scanner scanner;
    public Controller()
    {
        scanner=new Scanner(System.in);
        operator=new Operator();

    }
    public void getRequest(int choice) {
        if (choice == 1) {

            System.out.println("Enter the product name");
            String name=scanner.next();
            System.out.println("Enter the product price");
            double cost=scanner.nextDouble();
            System.out.println("Enter the product stock");
            int stock=scanner.nextInt();
            operator.addProduct(name,cost,stock);
        }
        else if (choice == 2) {
            operator.viewProduct();
        }
        else if (choice == 3) {
            System.out.println("Enter the product id you want me to show");
            int productId=scanner.nextInt();
            operator.viewProduct(productId);

        }
        else if (choice == 4)
        {
            System.out.println("Enter the product id you want to update");
            int productId=scanner.nextInt();
            System.out.println("Enter the new stock value ");
            int new_stockValue=scanner.nextInt();
            operator.updateProductStock(productId,new_stockValue);
        }
        else if (choice == 5) {
            System.out.println("Enter the product id you want to update");
            int productId=scanner.nextInt();
            System.out.println("Enter the new price value ");
            int new_priceValue=scanner.nextInt();
            operator.updateProductPrice(productId,new_priceValue);

        } else if (choice == 6) {
            System.out.println("Enter the product id you want to update");
            int productId=scanner.nextInt();
            System.out.println("Enter the new product name ");
            String new_NameValue=scanner.next();
            operator.updateProductName(productId,new_NameValue);

        } else if (choice == 7) {
            System.out.println("Enter the product id you want to update");
            int productId=scanner.nextInt();
            operator.updateSoftDelete(productId);

        } else if (choice == 8) {
            Shopping shopping=new Shopping();
            shopping.purchase();

        } else if (choice == 9) {
            System.out.println("Enter the product id you want to delete");
            int productId=scanner.nextInt();
            operator.deleteProduct(productId);

        } else if (choice == 10) {
        Shopping shopping = new Shopping();
        shopping.purchaseHistory();

        } else if (choice == 11) {
            System.out.println("Thank you");
            System.exit(0);

        } else {
            System.out.println("Enter the correct option");
        }
    }
}