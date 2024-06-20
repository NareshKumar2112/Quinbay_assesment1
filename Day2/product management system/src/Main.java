import com.demo.controller.Controller;
import com.demo.database.Database;
import com.demo.pojo.Product;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to blibli\n");

        Scanner scanner=new Scanner(System.in) ;
        Controller controller=new Controller();
        int choice;
        while(true)
        {
            System.out.println("Enter 1 to add product\nEnter 2 to All view product\nEnter 3 to view particular products" +
                    "\nEnter 4 to update the stocks\nEnter 5 to update the price\nEnter 6 to update the product name\n" +"Enter 7 to update the softDelete products\n"+
                    "Enter 8 to purchase" + "\nEnter 9 to delete the product\nEnter 10 to view shopping history\nEnter 11 to exit");

            choice= scanner.nextInt();
            scanner.nextLine();
            controller.getRequest(choice);
            System.out.println("-----------");
        }

    }
}