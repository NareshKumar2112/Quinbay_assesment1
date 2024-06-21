package com.demo.database;

import com.demo.pojo.Category;
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
//        String filename = "category.txt";
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
//
//            System.out.println("File '" + filename + "' has been created successfully.");
//        } catch (IOException e) {
//            System.err.println("Error writing to file '" + filename + "': " + e.getMessage());
//        }
//    }
    public List<Product> readProductList1(String filename)
    {
        List<Product> productList = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
//            Product product = Product.fromString(line);

//            productList.add(product);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return productList;
    }

public List<Product> readProductList(String filename) {
    List<Product> productList = new ArrayList<>();

    try (FileInputStream fileIn = new FileInputStream(filename);
         ObjectInputStream in = new ObjectInputStream(fileIn)) {
        productList = (List<Product>) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return productList;
}
    public void writeProductList1(String filename, List<Product> productList)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (Product product : productList) {
            writer.write(product.getId()+","+product.getName()+","+product.getCost()+","+product.getStock()+","+product.isSoft_delete());
            writer.newLine();
        }
        System.out.println("Product list has been saved to " + filename);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void writeProductList(String filename, List<Product> productList) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(productList);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
//    public List<Orders> readPurchaseList1(String filename){
//        return null;
//    }
    public void writePurchaseList(String filename, List<Orders> purchaseList) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(purchaseList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writePurchaseList1(List<Orders> purchaseList) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (Orders order : purchaseList) {
                writer.write(""+order.getOrder_id()+","+order.getProduct_names().toString()+","+order.getProduct_costs().toString()+","+order.getProduct_quantities().toString()+","+order.getTotal_amount());
                writer.newLine();
            }
            System.out.println("Product list has been saved to " + "output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Category> readCategoryList(String filename)
    {
        List<Category> categoryList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Category category = Category.fromString(line);

                categoryList.add(category);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categoryList;
    }
    public void writeCategoryList(List<Category> categoryList)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("category.txt"))) {
            for (Category category : categoryList) {
                writer.write(category.getCategory_id()+" "+category.getCategory_name());
                writer.newLine();
            }
            System.out.println("Product list has been saved to " + "category.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
