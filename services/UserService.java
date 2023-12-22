package services;

import entity.Order;
import entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static services.OrderService.orderLists;

public class UserService {
    static Scanner sc = new Scanner(System.in);
    public static List<User> userList = new ArrayList<>();
    static Integer firstNumber = 1;
    public static void register() {
        boolean isExists;
        do {
            String userId = "U" + firstNumber;
            String userName = registerUserName("Enter User Name: ");
            String userPhoneNumber = registerUserPhoneNumber();
            String userEmail = registerUserEmail("Enter User Email: ");
            String userPassword = registerUserPassword("Enter User Password: ");
            isExists = checkPhoneNumberExists(userPhoneNumber);
            if (isExists) {
                System.out.println("Account already exist. Please re-register.");
            }
            else {
                User user = new User(userId, userName, userPhoneNumber, userEmail, userPassword);
                userList.add(user);
                System.out.println("Register success.");
                firstNumber++;
                writerToFileUserList(userList, firstNumber);
            }
        }
        while (isExists);
    }
    public static User login() {
        boolean success;
        do {
            System.out.println("Enter Phone Number: ");
            String phoneNumber = sc.nextLine();
            System.out.println("Enter Password: ");
            String password = sc.nextLine();
            success = checkAccountExists(phoneNumber, password);
            if (success) {
                System.out.println("----------Login success----------");
                for (User user : userList) {
                    if (user.getUserPhoneNumber().equals(phoneNumber)) {
                        return user;
                    }
                }
            }
            else {
                System.out.println("Login fail. Please re-login.");
            }
        }
        while (!success);
        return null;
    }
    public static void viewAccountInformation(User currentUser) {
        System.out.println("----------Account information----------");
        for (User user : userList) {
            if (user.equals(currentUser)) {
                System.out.println("Name: " + user.getUserName() + "\nPhone Number: " + user.getUserPhoneNumber() + "\nEmail: " + user.getUserEmail());
            }
        }
    }
    public static void viewUserList() {
        System.out.println("----------User List----------");
        for (User user : userList) {
            System.out.println("ID: " + user.getUserId() + ", Name: " + user.getUserName() + ", Phone Number: " + user.getUserPhoneNumber() + ", Email: " + user.getUserEmail());
        }
    }
    public static void changePassword(User currentUser) {
        System.out.println("----------Change Password----------");
        for (User user : userList) {
            if (user.equals(currentUser)) {
                System.out.println("Enter old password: ");
                String oldPassword = sc.nextLine();
                if (user.getUserPassword().equals(oldPassword)) {
                    String newPassword = registerUserPassword("Enter new password: ");
                    user.setUserPassword(newPassword);
                    System.out.println("Password changed successfully.");
                }
                else {
                    System.out.println("Incorrect old password.");
                }
            }
        }
        writerToFileUserList(userList, firstNumber);
    }
    public static void changeEmail(User currentUser) {
        System.out.println("----------Change Email----------");
        for (User user : userList) {
            if (user.equals(currentUser)) {
                String newEmail = registerUserEmail("Enter new email: ");
                user.setUserEmail(newEmail);
                System.out.println("Email changed successfully.");
            }
        }
        writerToFileUserList(userList, firstNumber);
    }
    public static void changeName(User currentUser) {
        System.out.println("----------Change Name----------");
        for (User user : userList) {
            if (user.equals(currentUser)) {
                String newName = registerUserName("Enter new name: ");
                user.setUserName(newName);
                System.out.println("Name changed successfully.");
            }
        }
        writerToFileUserList(userList, firstNumber);
    }
    private static void writerToFileUserList(List<User> userList, Integer firstNumber) {
        try {
            FileWriter fw = new FileWriter("File/userList.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (User user : userList) {
                bw.write(user.toString());
                bw.newLine();
            }
            bw.write(String.valueOf(firstNumber));
            bw.close();
            fw.close();
        }
        catch (Exception ignored) {
        }
    }
    public static Pair<List<User>, Integer> readFromFileUserList() {
        try {
            FileReader fr = new FileReader("File/userList.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("U")) {
                    firstNumber = Integer.parseInt(line);
                    break;
                }
                String[] txt = line.split(",");
                String id = txt[0];
                String name = txt[1];
                String phoneNumber = txt[2];
                String email = txt[3];
                String password = txt[4];
                userList.add(new User(id, name, phoneNumber, email, password));
            }
            br.close();
        }
        catch (Exception e) {
            System.err.println("Error reading from file");
        }
        return new Pair<>(userList, firstNumber);
    }
    private static boolean checkPhoneNumberExists(String phoneNumber) {
        for (User user : userList) {
            if (user.getUserPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
    private static boolean checkAccountExists(String phoneNumber, String password) {
        for (User user : userList) {
            if (user.getUserPhoneNumber().equals(phoneNumber) && user.getUserPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static String registerUserPassword(String message) {
        boolean isMatched = false;
        do {
            try {
                System.out.println(message);
                String userPassword = sc.nextLine();
                isMatched = Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;'.,?/*~$^+=<>]).{8,32}$", userPassword);
                if (!isMatched) {
                    throw new RuntimeException();
                }
                else {
                    return userPassword;
                }
            }
            catch (Exception e) {
                System.out.println("Please re-enter: ");
            }
        }
        while (!isMatched);
        return "";
    }

    private static String registerUserEmail(String message) {
        boolean isMatched = false;
        do {
            try {
                System.out.println(message);
                String userEmail = sc.nextLine();
                isMatched = Pattern.matches("^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$", userEmail);
                if (!isMatched) {
                    throw new RuntimeException();
                }
                else {
                    return userEmail;
                }
            }
            catch (Exception e) {
                System.out.println("Please re-enter: ");
            }
        }
        while (!isMatched);
        return "";
    }

    private static String registerUserPhoneNumber() {
        boolean isMatched = false;
        do {
            try {
                System.out.println("Enter User Phone Number: ");
                String userPhoneNumber = sc.nextLine();
                isMatched = Pattern.matches("([\\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\\b", userPhoneNumber);
                if (!isMatched) {
                    throw new RuntimeException();
                }
                else {
                    return userPhoneNumber;
                }
            }
            catch (Exception e) {
                System.out.println("Please re-enter: ");
            }
        }
        while (!isMatched);
        return "";
    }

    private static String registerUserName(String message) {
        boolean isMatched = false;
        do {
            try {
                System.out.println(message);
                String userName = sc.nextLine();
                isMatched = Pattern.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$", userName);
                if (!isMatched) {
                    throw new RuntimeException();
                }
                else {
                    return userName;
                }
            }
            catch (Exception e) {
                System.out.println("Please re-enter: ");
            }
        }
        while (!isMatched);
        return "";
    }

}
