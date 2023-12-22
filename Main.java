

import entity.User;
import services.CartService;
import services.OrderService;
import services.ProductService;
import services.UserService;

import java.util.Date;
import java.util.Scanner;

import static services.CartService.readFromFileCartLines;
import static services.CategoryService.readFromFileCategoryList;
import static services.OrderService.*;
import static services.ProductService.readFromFileProductList;
import static services.UserService.readFromFileUserList;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        readFromFileUserList();
        readFromFileCategoryList();
        readFromFileProductList();
        readFromFileCartLines();
        readFromFileOrderLines();
        readFromFileOrderLists();
        int choice = -1;
        while (choice != 0) {
            System.out.println("----------Select Service----------");
            System.out.println("1.Register.");
            System.out.println("2.Login");
            System.out.println("0.Exit");
            System.out.println("Enter a choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
            }
            switch (choice) {
                case 1:
                    UserService.register();
                    break;
                case 2:
                    Program.login();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input or invalid selection. Please re-enter!");
            }
        }
    }
}