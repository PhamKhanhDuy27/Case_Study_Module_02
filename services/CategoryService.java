package services;

import entity.Category;
import entity.Product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static services.ProductService.productList;

public class CategoryService {
    static List<Category> categoryList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static Integer firstNumber = 1;
    public static void addCategory() {
        System.out.println("----------Add category----------");
        boolean isExists;
        do {
        String categoryId = "C" + firstNumber;
        String gender = registerGender();
        String name = registerName();
        isExists = checkCategoryExists(gender, name);
        if (isExists) {
            System.out.println("Category already exist. Please re-create Category.");
        }
        else {
            Category category = new Category(categoryId, gender, name);
            categoryList.add(category);
            System.out.println("Create Category success.");
            firstNumber++;
            writeToFileCategoryList(categoryList, firstNumber);
            }
        }
        while (isExists);
    }
    public static List<Category> viewCategoriesByGender(String gender) {
        System.out.println("----------Category List By " + gender + "----------");
        int count = 1;
        List<Category> categoryListByGender = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getGender().equals(gender)) {
                System.out.println(count + "." + category.getName());
                count++;
                categoryListByGender.add(category);
            }
        }
        System.out.println("0.Exit.");
        System.out.println("Enter a choice: ");
        return categoryListByGender;
    }
    public static void viewCategoryList() {
        System.out.println("----------Category List----------");
        for (Category category : categoryList) {
            System.out.println("ID: " + category.getCategoryId() + ", Gender: " + category.getGender() + ", Name: " + category.getName());
        }
    }
    public static void deleteCategory() {
        System.out.println("----------Delete category----------");
        System.out.println("Enter Category ID: ");
        String categoryId = sc.nextLine();
        for (Category category : categoryList) {
            if (category.getCategoryId().equals(categoryId)) {
                categoryList.remove(category);
            }
        }
        for (Product product : productList) {
            if (product.getCategoryId().equals(categoryId)) {
                productList.remove(product);
            }
        }
        writeToFileCategoryList(categoryList, firstNumber);
        ProductService.writeToFileProductList(productList, firstNumber);
    }
    public static Pair<List<Category>, Integer> readFromFileCategoryList() {
        try {
            FileReader fr = new FileReader("File/categoryList.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("C")) {
                    firstNumber = Integer.parseInt(line);
                    break;
                }
                String txt[] = line.split(",");
                String categoryId = txt[0];
                String gender = txt[1];
                String name = txt[2];
                categoryList.add(new Category(categoryId, gender, name));
            }
            br.close();
        }
        catch (Exception e) {
            System.err.println("Error reading from file categoryList.txt");
        }
        return new Pair<>(categoryList, firstNumber);
    }

    private static void writeToFileCategoryList(List<Category> categoryList, Integer firstNumber) {
        try {
            FileWriter fw = new FileWriter("File/categoryList.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Category category : categoryList) {
                bw.write(category.toString());
                bw.newLine();
            }
            bw.write(String.valueOf(firstNumber));
            bw.close();
            fw.close();
        }
        catch (Exception e) {
        }
    }

    private static boolean checkCategoryExists(String gender, String name) {
        for (Category category : categoryList) {
            if (category.getGender().equals(gender) && category.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static String registerName() {
        boolean isMatched = false;
        do {
            try  {
                System.out.println("Enter Category Name: ");
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
                System.out.println("Please re-enter Category Name.");
            }
        }
        while (!isMatched);
        return "";
    }

    private static String registerGender() {
        boolean isMatched = false;
        do {
            try {
                System.out.println("Enter Gender: ");
                String gender = sc.nextLine();
                isMatched = Pattern.matches("(?:m|M|male|Male|f|F|female|Female|FEMALE|MALE)$", gender);
                if (!isMatched) {
                    throw new RuntimeException();
                }
                else {
                    if (gender.equalsIgnoreCase("F") || gender.equalsIgnoreCase("female")) {
                        return "Female";
                    }
                    else if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("male")) {
                        return "Male";
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Please re-enter Gender.");
            }
        }
        while (!isMatched);
        return "";
    }
}
