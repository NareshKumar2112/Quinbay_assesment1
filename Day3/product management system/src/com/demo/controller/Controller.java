package com.demo.controller;

import com.demo.database.PostgresDatabase;
import com.demo.pojo.Product;
import com.demo.services.Operator;
import com.demo.services.Shopping;

import java.util.*;
public class Controller {

    private Operator operator;
    private Scanner scanner;

    public Controller()
    {
        scanner=new Scanner(System.in);
        operator=new Operator();
    }
    public void getRequest(int choice)
    {

        if (choice == 1) {
            operator.addProduct();
        }
        else if (choice == 2) {
            operator.viewAllProduct();
        }
        else if (choice == 3) {
            operator.viewProduct();
        }
        else if (choice == 4)
        {
            operator.updateStockThread();
        }
        else if (choice == 5) {
            operator.updatePriceThread();

        } else if (choice == 6) {
            operator.updateNameThread();

        } else if (choice == 7) {
            operator.updateSoftDelete();

        } else if (choice == 8) {
            Shopping shopping=new Shopping();
            shopping.purchaseThread();

        } else if (choice == 9) {
            operator.deleteProduct();

        } else if (choice == 10) {
        Shopping shopping = new Shopping();
        shopping.purchaseHistory();

        } else if (choice == 11) {
            operator.addCategory();
        } else if (choice == 12) {
            operator.viewProductByCategory();
        } else if (choice == 13) {
            System.out.println("Thank you");
            System.exit(0);

        } else {
            System.out.println("Enter the correct option");
        }
    }
}