package services;

import entity.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public static List<CartLine> orderLines = new ArrayList<>();
    public static List<Order> orderLists = new ArrayList<>();
    public static void writeToFileOrderLines (List<CartLine> orderLines) {
        try {
            FileWriter fw = new FileWriter("File/orderLines.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (CartLine orderLine : orderLines) {
                bw.write(orderLine.toString());
                bw.newLine();
            }
            bw.close();
            fw.close();
        }
        catch (Exception ignored) {
        }
    }
    public static List<CartLine> readFromFileOrderLines() {
        try {
            FileReader fr = new FileReader("File/orderLines.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] txt = line.split(",");
                String userId = txt[0];
                String productId = txt[1];
                String name = txt[2];
                long price = Long.parseLong(txt[3]);
                String categoryId = txt[4];
                int quantity = Integer.parseInt(txt[5]);
                long subtotal = Long.parseLong(txt[6]);
                orderLines.add(new CartLine(userId, new Product(productId, name, price, categoryId), quantity, subtotal));
            }
            br.close();
        }
        catch (Exception e) {
            System.err.println("Error reading from file orderLists.txt");
        }
        return orderLines;
    }
    public static void writeToFileOrderLists (List<Order> orderLists) {
        try {
            FileOutputStream fileOut = new FileOutputStream("File/orderLists.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(orderLists);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static List<Order> readFromFileOrderLists() {
        try {
            FileInputStream fileIn = new FileInputStream("File/orderLists.txt");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);;
            orderLists = (List<Order>) objectIn.readObject();
            objectIn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return orderLists;
    }
    public static List<CartLine> viewOrder (User currentUser) {
        int count = 1;
        long total = 0;
        List<CartLine> orderLineUser = new ArrayList<>();
        System.out.println("----------Order of " + currentUser.getUserName() + "-----------");
        for (CartLine orderLine : orderLines) {
            if (orderLine.getUserId().equals(currentUser.getUserId())) {
                System.out.println(count + "." + orderLine.getProduct().getName() + ", Price: " + orderLine.getProduct().getPrice() + " VND, Quantity: " + orderLine.getQuantity() + ", Subtotal: " + orderLine.getSubtotal() + " VND");
                count++;
                total = total + orderLine.getSubtotal();
            }
        }
        System.out.println("Total: " + total + " VND");
        System.out.println("1.Order confirmation.");
        System.out.println("2.Order cancel.");
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
        return orderLineUser;
    }
    public static long getTotal(User currentUser) {
        long total = 0;
        for (CartLine orderLine : orderLines) {
            if (orderLine.getUserId().equals(currentUser.getUserId())) {
                total = total + orderLine.getSubtotal();
            }
        }
        return total;
    }
    public static List<CartLine> getOrderLineUser(User currentUser) {
        List<CartLine> orderLineUser = new ArrayList<>();
        for (CartLine orderLine : orderLines) {
            if (orderLine.getUserId().equals(currentUser.getUserId())) {
                orderLineUser.add(orderLine);
            }
        }
        return orderLineUser;
    }
    public static List<Order> getOrderList() {
        List<Order> orders = new ArrayList<>();
        System.out.println("----------Order List----------");
        int count = 1;
        for (Order order : orderLists) {
            System.out.println(count + ".UserID: " + order.getUserId() + ", Order Date: " + order.getOrderDate() + ", Status: " + order.getStatus());
            count++;
            orders.add(order);
        }
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
        return orders;
    }
    public static void viewOrderList() {
        System.out.println("----------Order List----------");
        int count = 1;
        for (Order order : orderLists) {
            System.out.println(count + ".UserID: " + order.getUserId() + ", Order Date: " + order.getOrderDate() + ", Status: " + order.getStatus());
            count++;
        }
    }
    public static List<Order> getOrderListUser(User currentUser) {
        int count = 1;
        List<Order> orders = new ArrayList<>();
        System.out.println("-----------Order list----------");
        for (Order order : orderLists) {
            if (order.getUserId().equals(currentUser.getUserId())) {
                System.out.println(count + ". Total: " + order.getTotal() + ", Order Date: " + order.getOrderDate() + ", Status: " + order.getStatus() + ", Product List: " + order.getOrderLineUser());
                count++;
                orders.add(order);
            }
        }
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
        return orders;
    }
    public static void viewOrderListUser(User currentUser) {
        int count = 1;
        System.out.println("-----------Order list----------");
        for (Order order : orderLists) {
            if (order.getUserId().equals(currentUser.getUserId())) {
                System.out.println(count + ". Total: " + order.getTotal() + ", Order Date: " + order.getOrderDate() + ", Status: " + order.getStatus() + ", Product List: " + order.getOrderLineUser());
                count++;
            }
        }
    }
}
