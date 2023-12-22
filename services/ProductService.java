package services;

import entity.Category;
import entity.Product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Pattern;

import static services.CategoryService.categoryList;

public class ProductService {
    static Scanner sc = new Scanner(System.in);
    public static List<Product> productList = new ArrayList<>();
    static Integer firstNumber = 1;
    public static void addProduct() {
        System.out.println("----------Add product----------");
        boolean isExists;
        do {
            String productId = "P" + firstNumber;
            String name = registerName();
            long price = registerPrice();
            String categoryId = registerCategoryId(categoryList);
            isExists = checkProductExists(productId, name);
            if (isExists) {
                System.out.println("Product already exist. Please re-create Product.");
            }
            else {
                Product product = new Product(productId, name, price, categoryId);
                productList.add(product);
                System.out.println("Create Product success.");
                firstNumber++;
                writeToFileProductList(productList, firstNumber);
            }
        }
        while (isExists);
    }
    public static void deleteProduct() {
        System.out.println("----------Delete product----------");
        System.out.println("Enter Product ID: ");
        String productId = sc.nextLine();
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                productList.remove(product);
            }
        }
        writeToFileProductList(productList, firstNumber);
    }
    public static void viewProductList() {
        System.out.println("----------Product List----------");
        for (Product product : productList) {
            System.out.println("ID: " + product.getProductId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Category ID: " + product.getCategoryId());
        }
    }
    public static List<Product> getProductList() {
        List<Product> productLists = new ArrayList<>();
        System.out.println("----------Product List----------");
        int count = 1;
        for (Product product : productList) {
            System.out.println(count + "." + product.getName() + ", Price: " + product.getPrice());
            count++;
            productLists.add(product);
        }
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
        return productLists;
    }
    public static List<Product> getSortProductList(String style) {
        int count = 1;
        List<Product> productLists = new ArrayList<>();
        productList.sort(ProductService.getComparator(style));
        System.out.println("----------Product List----------");
        for (Product product : productList) {
            System.out.println(count + "." + product.getName() + ", Price: " + product.getPrice());
            count++;
            productLists.add(product);
        }
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
        return productLists;
    }
    public static Comparator<Product> getComparator(String sortBy) {
        if (sortBy.equals("alphabetically")) {
            return new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };
        }
        else if (sortBy.equals("priceAscending")) {
            return new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return Long.compare(o1.getPrice(), o2.getPrice());
                }
            };
        }
        else if (sortBy.equals("priceDescending")) {
            return new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return Long.compare(o2.getPrice(), o1.getPrice());
                }
            };
        }
        else {
            return null;
        }
    }
    public static List<Product> getSearchProductList(String keywords) {
        int count = 1;
        List<Product> productLists = new ArrayList<>();
        System.out.println("----------Product List----------");
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(keywords.toLowerCase())) {
                System.out.println(count + "." + product.getName() + ", Price: " + product.getPrice());
                count++;
                productLists.add(product);
            }
        }
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
        return productLists;
    }
    public static Pair<List<Product>, Integer> readFromFileProductList() {
        try {
            FileReader fr = new FileReader("File/productList.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("P")) {
                    firstNumber = Integer.parseInt(line);
                    break;
                }
                String txt[] = line.split(",");
                String productId = txt[0];
                String name = txt[1];
                long price = Long.parseLong(txt[2]);
                String categoryId = txt[3];
                productList.add(new Product(productId, name, price, categoryId));
            }
            br.close();
        }
        catch (Exception e) {
            System.err.println("Error reading from file productList.txt.");
        }
        return new Pair<>(productList, firstNumber);
    }

    public static List<Product> viewProductListByCategory(String categoryId) {
        List<Product> productListByCategory = new ArrayList<>();
        System.out.println("----------Product List----------");
        int count = 1;
        for (Product product : productList) {
            if (product.getCategoryId().equals(categoryId)) {
                System.out.println(count + "." + product.getName() + ", Price: " + product.getPrice());
                count++;
                productListByCategory.add(product);
            }
        }
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
        return productListByCategory;
    }

    public static void writeToFileProductList(List<Product> productList, Integer firstNumber) {
        try {
            FileWriter fw = new FileWriter("File/productList.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Product product : productList) {
                bw.write(product.toString());
                bw.newLine();
            }
            bw.write(String.valueOf(firstNumber));
            bw.close();
            fw.close();
        }
        catch (Exception e) {
        }
    }

    private static boolean checkProductExists(String productId, String name) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId) && product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static String registerCategoryId(List<Category> categoryList) {
        boolean isMatched = false;
        do {
                System.out.println("Enter Category Id: ");
                String categoryId = sc.nextLine();
                for (Category category : categoryList) {
                    if (category.getCategoryId().equals(categoryId)) {
                        return categoryId;
                    }
                }
                if (!isMatched) {
                    System.out.println("Category does not exist. Please re-enter Category Id.");
                }
            }
        while (!isMatched);
        return "";
    }

    private static long registerPrice() {
        boolean isMatched = false;
        do {
            try {
                System.out.println("Enter Product Price: ");
                long price = Long.parseLong(sc.nextLine());
                if (price < 0) {
                    throw new RuntimeException();
                }
                else {
                    isMatched = true;
                    return price;
                }
            }
            catch (Exception e) {
                System.out.println("Invalid Product Price. Please re-enter Product Price.");
            }
        }
        while (!isMatched);
        return 0;
    }

    private static String registerName() {
        boolean isMatched = false;
        do {
            try {
                System.out.println("Enter Product Name: ");
                String name = sc.nextLine();
                isMatched = Pattern.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$", name);
                if (!isMatched) {
                    throw new RuntimeException();
                }
                else {
                    return name;
                }
            }
            catch (Exception e) {
                System.out.println("Please re-enter Product Name.");
            }
        }
        while (!isMatched);
        return "";
    }
}
