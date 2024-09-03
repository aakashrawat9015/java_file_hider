package views;
import model.User;
import dao.UserDAO;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the app");

        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signup");
        System.out.println("Press 3 to exit");
//        System.out.println("Press 1 to login");

        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                signup();
                break;
            default:
                System.out.println("Invalid choice. Please select 1 or 2.");
                break;
        }
    }

    private void signup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = sc.nextLine();
        System.out.println("Enter email: ");
        String email = sc.nextLine();

        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Enter the OTP : ");
        String otp = sc.nextLine();
        if (genOTP.equals(otp)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response){
                case 0:
                    System.out.println("User register succesfully");
                    break;
                case 1:
                    System.out.println("User Already exists");
            }
        } else {
            System.out.println("Wrong OTP");
        }
    }



    private void login() {
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        try {
            if (UserDAO.isExists(email)) {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("Enter the OTP : ");
                String otp = sc.nextLine();
                if (genOTP.equals(otp)) {

                    System.out.println("welcome");
                } else {
                    System.out.println("Wrong OTP");
                }
            } else {
                System.out.println("User not exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

