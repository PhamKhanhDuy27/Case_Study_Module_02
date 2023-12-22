import entity.*;
import services.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static services.CartService.cartLines;
import static services.OrderService.orderLines;
import static services.OrderService.orderLists;


public class Program {
    static final String ADMIN = "0981039497";
    static Scanner sc = new Scanner(System.in);
    public static void login() {
        int choice = -1;
        User currentUser = UserService.login();
        assert currentUser != null;
        if (currentUser.getUserPhoneNumber().equals(ADMIN)) {
            System.out.println("----------ADMIN----------");
            while (choice != 0) {
                adminServiceList();
                try {
                    choice = Integer.parseInt(sc.nextLine());
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid input or invalid selection. Please re-enter!");
                    continue;
                }
                switch (choice) {
                    case 1:
                        UserService.viewAccountInformation(currentUser);
                        break;
                    case 2:
                        changeInformation(currentUser);
                        break;
                    case 3:
                        UserService.viewUserList();
                        break;
                    case 4:
                        int choiceJob = -1;
                        while (choiceJob != 0) {
                            System.out.println("----------Feature List----------");
                            System.out.println("1.View order list.");
                            System.out.println("2.Update order list.");
                            System.out.println("0.Exit.");
                            System.out.println("Enter a choice: ");
                            try {
                                choiceJob = Integer.parseInt(sc.nextLine());
                            }
                            catch (NumberFormatException e) {
                                System.out.println("Invalid input or invalid selection. Please re-enter!");
                                continue;
                            }
                            switch (choiceJob) {
                                case 1:
                                    OrderService.viewOrderList();
                                    break;
                                case 2:
                                    int select = -1;
                                    while (select != 0) {
                                        List<Order> orders = OrderService.getOrderList();
                                        try {
                                            select = Integer.parseInt(sc.nextLine());
                                        }
                                        catch (Exception e) {
                                            System.out.println("Invalid input or invalid selection. Please re-enter!");
                                            continue;
                                        }
                                        if (select < 0 || select > orders.size()) {
                                            System.out.println("Invalid input or invalid selection. Please re-enter!");
                                        }
                                        else if (select > 0) {
                                            Order order = orders.get(select - 1);
                                            String status1 = "Approved";
                                            String status2 = "Accomplished";
                                            for (Order order1 : orderLists) {
                                                if (order1.getUserId().equals(order.getUserId()) && order1.getOrderDate().equals(order.getOrderDate())) {
                                                    if (order1.getStatus().equals("Processing")) {
                                                        order1.setStatus(status1);
                                                        System.out.println("----------Status update successful----------");
                                                    }
                                                    else if (order1.getStatus().equals("Approved")) {
                                                        order1.setStatus(status2);
                                                        System.out.println("----------Status update successful----------");
                                                    }
                                                    else {
                                                        System.out.println("----------The status cannot be changed----------");
                                                    }
                                                }
                                            }
                                            OrderService.writeToFileOrderLists(orderLists);
                                        }
                                    }
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Invalid input or invalid selection. Please re-enter!");
                            }
                        }
                        OrderService.getOrderList();
                        break;
                    case 5:
                        changeCategory();
                        break;
                    case 6:
                        changeProduct();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid input or invalid selection. Please re-enter!");
                }
            }
        } else {
            System.out.println("----------CUSTOMER----------");
            while (choice != 0) {
                userServiceList();
                try {
                    choice = Integer.parseInt(sc.nextLine());
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid input or invalid selection. Please re-enter!");
                    continue;
                }
                switch (choice) {
                    case 1:
                        UserService.viewAccountInformation(currentUser);
                        break;
                    case 2:
                        changeInformation(currentUser);
                        break;
                    case 3:
                        int choiceCategory = -1;
                        while (choiceCategory != 0) {
                            viewCategoryList();
                            try {
                                choiceCategory = Integer.parseInt(sc.nextLine());
                            }
                            catch (NumberFormatException e) {
                                System.out.println("Invalid input or invalid selection. Please re-enter!");
                                continue;
                            }
                            switch (choiceCategory) {
                                case 1:
                                    buyFromGender(currentUser, "Female");
                                    break;
                                case 2:
                                    buyFromGender(currentUser, "Male");
                                    break;
                                case 3:
                                    buyFromProductList(currentUser);
                                    break;
                                case 4:
                                    buyFromSort(currentUser);
                                    break;
                                case 5:
                                    buyFromSearchProduct(currentUser);
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Invalid input or invalid selection. Please re-enter!");
                            }
                        }
                        break;
                    case 4:
                        int choiceCartline= -1;
                        while (choiceCartline != 0) {
                            Cart cartUser = CartService.viewCart(currentUser);
                            try {
                                choiceCartline = Integer.parseInt(sc.nextLine());
                            }
                            catch (NumberFormatException e) {
                                System.out.println("Invalid input or invalid selection. Please re-enter!");
                                continue;
                            }
                            if (choiceCartline < 0 || choiceCartline > cartUser.getCartLinesUser().size()) {
                                System.out.println("Invalid input or invalid selection. Please re-enter!");
                            }
                            else if (choiceCartline > 0) {
                                CartLine cartLineUser = cartUser.getCartLinesUser().get(choiceCartline - 1);
                                int choiceFunction = -1;
                                while (choiceFunction != 0) {
                                    System.out.println("1.Add quantity.");
                                    System.out.println("2.Reduce quantity.");
                                    System.out.println("3.Delete CartLine.");
                                    System.out.println("4.Add CartLine To Order.");
                                    System.out.println("0.Exit.");
                                    System.out.println("Enter a choice: ");
                                    try {
                                        choiceFunction = Integer.parseInt(sc.nextLine());
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input or invalid selection. Please re-enter!");
                                    }
                                    switch (choiceFunction) {
                                        case 1:
                                            for (CartLine cartLine : cartLines) {
                                                if (cartLine.getUserId().equals(cartLineUser.getUserId()) && cartLine.getProduct().getProductId().equals(cartLineUser.getProduct().getProductId())) {
                                                    int quantity = inputQuantity();
                                                    if (quantity != 0) {
                                                        CartService.addQuantity(currentUser.getUserId(), cartLine.getProduct().getProductId(), quantity);
                                                        System.out.println("----------Add quantity successes----------");
                                                    } else {
                                                        System.out.println("----------Quantity remains unchanged----------");
                                                    }
                                                }
                                            }
                                            break;
                                        case 2:
                                            for (CartLine cartLine : cartLines) {
                                                if (cartLine.getUserId().equals(cartLineUser.getUserId()) && cartLine.getProduct().getProductId().equals(cartLineUser.getProduct().getProductId())) {
                                                    int quantity = inputQuantity();
                                                    if (quantity != 0) {
                                                        if (quantity < cartLine.getQuantity()) {
                                                            CartService.reduceQuantity(currentUser.getUserId(), cartLine.getProduct().getProductId(), quantity);
                                                            System.out.println("----------Reduce quantity successes----------");
                                                        } else {
                                                            CartService.deleteCartLine(currentUser.getUserId(), cartLine.getProduct().getProductId());
                                                            System.out.println("----------CartLine has been removed----------");
                                                        }
                                                    } else {
                                                        System.out.println("----------Quantity remains unchanged----------");
                                                    }
                                                }
                                            }
                                            break;
                                        case 3:
                                            CartService.deleteCartLine(currentUser.getUserId(), cartLineUser.getProduct().getProductId());
                                            break;
                                        case 4:
                                            CartService.addCartLineToOrder(cartLineUser);
                                            break;
                                        case 0:
                                            break;
                                        default:
                                            System.out.println("Invalid input or invalid selection. Please re-enter!");
                                    }
                                    break;
                                    }
                                }
                            }
                        break;
                    case 5:
                        int choicerequest = -1;
                        while (choicerequest != 0) {
                            OrderService.viewOrder(currentUser);
                            try {
                                choicerequest = Integer.parseInt(sc.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input or invalid selection. Please re-enter!");
                            }
                            switch (choicerequest) {
                                case 1:
                                    long total = OrderService.getTotal(currentUser);
                                    String shippingAddress = inputShippingAddress();
                                    Date orderDate = new Date();
                                    String status = "Processing";
                                    List<CartLine> orderLineUser = OrderService.getOrderLineUser(currentUser);
                                    Order order = new Order(currentUser.getUserId(), total, shippingAddress, orderDate, status, orderLineUser);
                                    orderLists.add(order);
                                    System.out.println("----------Order Success----------");
                                    orderLines.removeAll(orderLineUser);
                                    OrderService.writeToFileOrderLines(orderLines);
                                    OrderService.writeToFileOrderLists(orderLists);
                                    break;
                                case 2:
                                    List<CartLine> orderLineUser1 = OrderService.getOrderLineUser(currentUser);
                                    cartLines.addAll(orderLineUser1);
                                    orderLines.removeAll(orderLineUser1);
                                    System.out.println("----------Canceled order successfully----------");
                                    CartService.writeToFileCartLines(cartLines);
                                    OrderService.writeToFileOrderLines(orderLines);
                                    break;
                                case 0:
                                    List<CartLine> orderLineUser2 = OrderService.getOrderLineUser(currentUser);
                                    cartLines.addAll(orderLineUser2);
                                    orderLines.removeAll(orderLineUser2);
                                    CartService.writeToFileCartLines(cartLines);
                                    OrderService.writeToFileOrderLines(orderLines);
                                    break;
                                default:
                                    System.out.println("Invalid input or invalid selection. Please re-enter!");
                            }
                            break;
                        }
                        break;
                    case 6:
                        int choiceJob = -1;
                        while (choiceJob != 0) {
                            System.out.println("----------Feature List----------");
                            System.out.println("1.View order list.");
                            System.out.println("2.Update order list.");
                            System.out.println("0.Exit.");
                            System.out.println("Enter a choice: ");
                            try {
                                choiceJob = Integer.parseInt(sc.nextLine());
                            }
                            catch (NumberFormatException e) {
                                System.out.println("Invalid input or invalid selection. Please re-enter!");
                                continue;
                            }
                            switch (choiceJob) {
                                case 1:
                                    OrderService.viewOrderListUser(currentUser);
                                    break;
                                case 2:
                                    int select = -1;
                                    while (select != 0) {
                                        List<Order> orders = OrderService.getOrderListUser(currentUser);
                                        try {
                                            select = Integer.parseInt(sc.nextLine());
                                        } catch (Exception e) {
                                            System.out.println("Invalid input or invalid selection. Please re-enter!");
                                            continue;
                                        }
                                        if (select < 0 || select > orders.size()) {
                                            System.out.println("Invalid input or invalid selection. Please re-enter!");
                                        } else if (select > 0) {
                                            Order order = orders.get(select - 1);
                                            String status2 = "Accomplished";
                                            for (Order order1 : orderLists) {
                                                if (order1.getUserId().equals(order.getUserId()) && order1.getOrderDate().equals(order.getOrderDate())) {
                                                    if (order1.getStatus().equals("Approved")) {
                                                        order1.setStatus(status2);
                                                        System.out.println("----------Status update successful----------");
                                                    } else {
                                                        System.out.println("----------The status cannot be changed----------");
                                                    }
                                                }
                                            }
                                            OrderService.writeToFileOrderLists(orderLists);
                                        }
                                    }
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Invalid input or invalid selection. Please re-enter!");
                            }
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid input or invalid selection. Please re-enter!");
                }
            }
        }
    }
    private static void userServiceList() {
        System.out.println("----------Service List----------");
        System.out.println("1.View account information.");
        System.out.println("2.Change the account information.");
        System.out.println("3.Purchase.");
        System.out.println("4.Cart.");
        System.out.println("5.Order.");
        System.out.println("6.Order List.");
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
    }
    private static void adminServiceList() {
        System.out.println("----------Service List----------");
        System.out.println("1.View account information.");
        System.out.println("2.Change the account information.");
        System.out.println("3.User list.");
        System.out.println("4.Order list.");
        System.out.println("5.Change Category.");
        System.out.println("6.Change Product.");
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
    }
    private static void changeInformation() {
        System.out.println("----------Change List----------");
        System.out.println("1.Change name.");
        System.out.println("2.Change email.");
        System.out.println("3.Change password.");
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
    }
    private static void changeInformation(User user) {
        int choiceChangeInformation = -1;
        while (choiceChangeInformation != 0) {
            changeInformation();
            try {
                choiceChangeInformation = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
                continue;
            }
            switch (choiceChangeInformation) {
                case 1:
                    UserService.changeName(user);
                    break;
                case 2:
                    UserService.changeEmail(user);
                    break;
                case 3:
                    UserService.changePassword(user);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input or invalid selection. Please re-enter!");
            }
        }
    }
    private static void changeProduct() {
        int choiceChangeProduct = -1;
        while (choiceChangeProduct != 0) {
            adminChangeProductList();
            try {
                choiceChangeProduct = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
                continue;
            }
            switch (choiceChangeProduct) {
                case 1:
                    ProductService.viewProductList();
                    break;
                case 2:
                    ProductService.addProduct();
                    break;
                case 3:
                    ProductService.deleteProduct();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input or invalid selection. Please re-enter!");
            }
        }
    }
    private static void changeCategory() {
        int choiceChangeCategory = -1;
        while (choiceChangeCategory != 0) {
            adminChangeCategoryList();
            try {
                choiceChangeCategory = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
                continue;
            }
            switch (choiceChangeCategory) {
                case 1:
                    CategoryService.viewCategoryList();
                    break;
                case 2:
                    CategoryService.addCategory();
                    break;
                case 3:
                    CategoryService.deleteCategory();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input or invalid selection. Please re-enter!");
            }
        }
    }
    private static void adminChangeCategoryList() {
        System.out.println("----------Change List----------");
        System.out.println("1.View category list.");
        System.out.println("2.Add category.");
        System.out.println("3.Delete category.");
        System.out.println("0.Exit");
        System.out.println("Enter a choice: ");
    }
    private static void adminChangeProductList() {
        System.out.println("----------Change List----------");
        System.out.println("1.View product list.");
        System.out.println("2.Add product.");
        System.out.println("3.Delete product.");
        System.out.println("0.Exit");
        System.out.println("Enter a choice: ");
    }
    private static void viewCategoryList() {
        System.out.println("----------Category List----------");
        System.out.println("1.Female.");
        System.out.println("2.Male.");
        System.out.println("3.List of all product.");
        System.out.println("4.Product arrangements.");
        System.out.println("5.Search.");
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
    }
    private static void buyFromGender(User currentUser, String gender) {
        int choiceCategoryByFemale = -1;
        while (choiceCategoryByFemale != 0) {
            List<Category> categoryListByFemale = CategoryService.viewCategoriesByGender(gender);
            try {
                choiceCategoryByFemale = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
                continue;
            }
            if (choiceCategoryByFemale < 0 || choiceCategoryByFemale > categoryListByFemale.size()) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
            } else if (choiceCategoryByFemale > 0) {
                String categoryId = categoryListByFemale.get(choiceCategoryByFemale - 1).getCategoryId();
                int choiceProduct = -1;
                while (choiceProduct != 0) {
                    List<Product> productListByCategory = ProductService.viewProductListByCategory(categoryId);
                    try {
                        choiceProduct = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input or invalid selection. Please re-enter!");
                        continue;
                    }
                    if (choiceProduct < 0 || choiceProduct > productListByCategory.size()) {
                        System.out.println("Invalid input or invalid selection. Please re-enter!");
                    } else if (choiceProduct > 0) {
                        boolean isExist;
                        Product product = productListByCategory.get(choiceProduct - 1);
                        isExist = CartService.checkProductExistInCart(currentUser.getUserId(), product.getProductId());
                        int quantity = inputQuantity();
                        if (quantity != 0) {
                            if (isExist) {
                                CartService.addQuantity(currentUser.getUserId(), product.getProductId(), quantity);
                                System.out.println("----------Add quantity successes----------");
                            }
                            else {
                                CartService.addProductToCartLine(currentUser, product, quantity);
                                System.out.println("----------The product has been added to cart----------");
                            }
                        }
                        else {
                            System.out.println("----------The product has not been added to cart-----------");
                        }
                        break;
                    }
                }
            }
        }
    }
    private static void buyFromProductList(User currentUser) {
        int choiceProduct = -1;
        while (choiceProduct != 0) {
            List<Product> productLists = ProductService.getProductList();
            try {
                choiceProduct = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
                continue;
            }
            if (choiceProduct < 0 || choiceProduct > productLists.size()) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
            } else if (choiceProduct > 0) {
                boolean isExist;
                Product product = productLists.get(choiceProduct - 1);
                isExist = CartService.checkProductExistInCart(currentUser.getUserId(), product.getProductId());
                int quantity = inputQuantity();
                if (quantity != 0) {
                    if (isExist) {
                        CartService.addQuantity(currentUser.getUserId(), product.getProductId(), quantity);
                        System.out.println("----------Add quantity successes----------");
                    } else {
                        CartService.addProductToCartLine(currentUser, product, quantity);
                        System.out.println("----------The product has been added to cart----------");
                    }
                }
                else {
                    System.out.println("----------The product has not been added to cart----------");
                }
                break;
            }
        }
    }
    private static void buyFromSortProductList(User currentUser, String style) {
        int choiceProduct = -1;
        while (choiceProduct != 0) {
            List<Product> productLists = ProductService.getSortProductList(style);
            try {
                choiceProduct = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
                continue;
            }
            if (choiceProduct < 0 || choiceProduct > productLists.size()) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
            } else if (choiceProduct > 0) {
                boolean isExist;
                Product product = productLists.get(choiceProduct - 1);
                isExist = CartService.checkProductExistInCart(currentUser.getUserId(), product.getProductId());
                int quantity = inputQuantity();
                if (quantity != 0) {
                    if (isExist) {
                        CartService.addQuantity(currentUser.getUserId(), product.getProductId(), quantity);
                        System.out.println("----------Add quantity successes----------");
                    } else {
                        CartService.addProductToCartLine(currentUser, product, quantity);
                        System.out.println("----------The product has been added to cart----------");
                    }
                }
                else {
                    System.out.println("----------The product has not been added to cart----------");
                }
                break;
            }
        }
    }
    private static void buyFromSort(User currentUser){
        int choiceSoftType = -1;
        while (choiceSoftType != 0) {
            System.out.println("----------Soft Type----------");
            System.out.println("1.Sort Alphabetically.");
            System.out.println("2.Soft Ascending Style.");
            System.out.println("3.Sort Descending Style.");
            System.out.println("0.Exit.");
            System.out.println("Enter a choice: ");
            try {
                choiceSoftType = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Invalid input or invalid selection. Please re-enter!");
                continue;
            }
            switch (choiceSoftType) {
                case 1:
                    buyFromSortProductList(currentUser, "alphabetically");
                    break;
                case 2:
                    buyFromSortProductList(currentUser, "priceAscending");
                    break;
                case 3:
                    buyFromSortProductList(currentUser, "priceDescending");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input or invalid selection. Please re-enter!");
            }
        }
    }
    private static void buyFromSearchProduct(User currentUser) {
        int choiceProduct = -1;
        System.out.println("----------Search Product----------");
        System.out.println("Enter keywords: ");
        String keywords = sc.nextLine();
        while (choiceProduct != 0) {
            List<Product> productLists = ProductService.getSearchProductList(keywords);
            try {
                choiceProduct = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
                continue;
            }
            if (choiceProduct < 0 || choiceProduct > productLists.size()) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
            } else if (choiceProduct > 0) {
                boolean isExist;
                Product product = productLists.get(choiceProduct - 1);
                isExist = CartService.checkProductExistInCart(currentUser.getUserId(), product.getProductId());
                int quantity = inputQuantity();
                if (quantity != 0) {
                    if (isExist) {
                        CartService.addQuantity(currentUser.getUserId(), product.getProductId(), quantity);
                        System.out.println("----------Add quantity successes----------");
                    } else {
                        CartService.addProductToCartLine(currentUser, product, quantity);
                        System.out.println("----------The product has been added to cart----------");
                    }
                }
                else {
                    System.out.println("----------The product has not been added to cart----------");
                }
                break;
            }
        }
    }
    private static int inputQuantity () {
        int quantity;
        do {
            try {
                System.out.println("Enter quantity: ");
                quantity = Integer.parseInt(sc.nextLine());
                if (quantity < 0) {
                    throw new RuntimeException();
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input or invalid selection. Please re-enter!");
            }
        }
        while (true);
        return quantity;
    }
    private static String inputShippingAddress () {
        boolean isMatched = false;
        String shippingAddress = null;
        do {
            try {
                System.out.println("Enter Shipping Address: ");
                System.out.println("Example: 7A/5/5 - Duong Thanh Thai - Phuong 14 - Quan 10 - TP.HCM");
                shippingAddress = sc.nextLine();
                isMatched = Pattern.matches("^[#.0-9a-zA-Z\s/-]+$", shippingAddress);
                if (!isMatched) {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please re-enter!");
            }
        }
        while (!isMatched);
        return shippingAddress;
    }
}
