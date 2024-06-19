package com.demo.database;

import com.demo.pojo.Orders;
import com.demo.pojo.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

//    public Database()
//    {
//        writeProductList("products.ser", new ArrayList<Product>());
//    }
//    public Database()
//    {
//        String filename = "products.txt";
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
//            writer.write("Hello, this is a test file created using BufferedWriter in Java!\n");
//            writer.write("Writing multiple lines to demonstrate.\n");
//            writer.write("End of file.\n");
//
//            System.out.println("File '" + filename + "' has been created successfully.");
//        } catch (IOException e) {
//            System.err.println("Error writing to file '" + filename + "': " + e.getMessage());
//        }
//    }
public List<Product> readProductList(String filename) {
    List<Product> productList = new ArrayList<>();

    try (FileInputStream fileIn = new FileInputStream(filename);
         ObjectInputStream in = new ObjectInputStream(fileIn)) {
        productList = (List<Product>) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }

    return productList;
//    List<Product> productList = new ArrayList<>();
//
//    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
//        String line;
//        while ((line = reader.readLine()) != null) {
//            Product product = Product.fromString(line);
//            productList.add(product);
//        }
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//
//    return productList;
}

    public void writeProductList(String filename, List<Product> productList) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(productList);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
//            for (Product product : productList) {
//                writer.write(product.toString());
//                writer.newLine(); // Add newline after each product
//            }
//            System.out.println("Product list has been saved to " + filename);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public List<Orders> readPurchaseList(String filename) {
        List<Orders> purchaseList = new ArrayList<>();

        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            purchaseList = (List<Orders>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return purchaseList;
    }

    public void writePurchaseList(String filename, List<Orders> purchaseList) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(purchaseList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
