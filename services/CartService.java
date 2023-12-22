package services;

import entity.Cart;
import entity.CartLine;
import entity.Product;
import entity.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static services.OrderService.orderLines;

public class CartService {
    public static List<CartLine> cartLines = new ArrayList<>();
    public static void addProductToCartLine(User currentUser, Product product, Integer quantity) {
        String userId = currentUser.getUserId();
        long subtotal = product.getPrice() * quantity;
        cartLines.add(new CartLine(userId, product, quantity, subtotal));
        writeToFileCartLines(cartLines);
    }
    public static void writeToFileCartLines (List<CartLine> cartLines) {
        try {
            FileWriter fw = new FileWriter("File/cartLines.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (CartLine cartLine : cartLines) {
                bw.write(cartLine.toString());
                bw.newLine();
            }
            bw.close();
            fw.close();
        }
        catch (Exception ignored) {
        }
    }
    public static List<CartLine> readFromFileCartLines() {
        try {
            FileReader fr = new FileReader("File/cartLines.txt");
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
                cartLines.add(new CartLine(userId, new Product(productId, name, price, categoryId), quantity, subtotal));
            }
            br.close();
        }
        catch (Exception e) {
            System.err.println("Error reading from file cartLines.txt");
        }
        return cartLines;
    }
    public static List<CartLine> getAllCartLinesOfUser(User currentUser) {
        String userId = currentUser.getUserId();
        List<CartLine> cartLinesUser = new ArrayList<>();
        for (CartLine cartLine : cartLines) {
            if (cartLine.getUserId().equals(userId)) {
                cartLinesUser.add(cartLine);
                return cartLinesUser;
            }
        }
        return null;
    }
    public static boolean checkProductExistInCart(String userId, String productId) {
        for (CartLine cartLine : cartLines) {
            if (cartLine.getUserId().equals(userId) && cartLine.getProduct().getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }
    public static void addQuantity(String userId, String productId, int quantity) {
        for (CartLine cartLine : cartLines) {
            if (cartLine.getUserId().equals(userId) && cartLine.getProduct().getProductId().equals(productId)) {
                int newQuantity = cartLine.getQuantity() + quantity;
                long newSubtotal = newQuantity * cartLine.getProduct().getPrice();
                cartLine.setQuantity(newQuantity);
                cartLine.setSubtotal(newSubtotal);
            }
        }
        writeToFileCartLines(cartLines);
    }
    public static void reduceQuantity(String userId, String productId, int quantity) {
        for (CartLine cartLine : cartLines) {
            if (cartLine.getUserId().equals(userId) && cartLine.getProduct().getProductId().equals(productId)) {
                int newQuantity = cartLine.getQuantity() - quantity;
                long newSubtotal = newQuantity * cartLine.getProduct().getPrice();
                cartLine.setQuantity(newQuantity);
                cartLine.setSubtotal(newSubtotal);
            }
        }
        writeToFileCartLines(cartLines);
    }
    public static void deleteCartLine(String userId, String productId) {
        List<CartLine> toRemove = new ArrayList<>();
        for (CartLine cartLine : cartLines) {
            if (cartLine.getUserId().equals(userId) && cartLine.getProduct().getProductId().equals(productId)) {
                toRemove.add(cartLine);
            }
        }
        cartLines.removeAll(toRemove);
        System.out.println("----------CartLine has been removed----------");
        writeToFileCartLines(cartLines);
    }
    public static Cart viewCart (User currentUser) {
        int count = 1;
        long total = 0;
        List<CartLine> cartLineList = new ArrayList<>();
        System.out.println("----------Cart of " + currentUser.getUserName() + "-----------");
        for (CartLine cartLine : cartLines) {
            if (cartLine.getUserId().equals(currentUser.getUserId())) {
                System.out.println(count + "." + cartLine.getProduct().getName() + ", Price: " + cartLine.getProduct().getPrice() + " VND, Quantity: " + cartLine.getQuantity() + ", Subtotal: " + cartLine.getSubtotal() + " VND");
                count++;
                total = total + cartLine.getSubtotal();
                cartLineList.add(cartLine);
            }
        }
        System.out.println("Total: " + total + " VND");
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
        return new Cart(currentUser.getUserId(), total, cartLineList);
    }
    public static void addCartLineToOrder(CartLine cartLineUser) {
        List<CartLine> toRemove = new ArrayList<>();
        for (CartLine cartLine : cartLines) {
            if (cartLine.getUserId().equals(cartLineUser.getUserId()) && cartLine.getProduct().getProductId().equals(cartLineUser.getProduct().getProductId())) {
                orderLines.add(cartLine);
                toRemove.add(cartLine);
            }
        }
        cartLines.removeAll(toRemove);
        System.out.println("-----------Added cartline to order successfully----------");
        CartService.writeToFileCartLines(cartLines);
        OrderService.writeToFileOrderLines(orderLines);
    }
}
