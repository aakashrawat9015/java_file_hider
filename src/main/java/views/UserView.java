package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    String userName;

    UserView(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    public void home() {
        do {
            System.out.println("Welcome " + userName);
            System.out.println("1. Press 1 to show hidden files ");
            System.out.println("2. Press 2 to hide a new file ");
            System.out.println("3. Press 3 to unhide a file ");
            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1:
                    List<Data> files;
                    try {
                        files = DataDAO.getAllFiles(this.email);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("ID  -   File_Name ");
                    for (Data dataFile : files) { // Changed variable name to dataFile
                        System.out.println(dataFile.getId() + "  -  " + dataFile.getFileName());
                    }
                    break;
                case 2:
                    System.out.println("Enter the file path");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 3:
                    try {
                        files = DataDAO.getAllFiles(email);
                        System.out.println("ID  -   File_Name ");
                        for (Data dataFile : files) { // Changed variable name to dataFile
                            System.out.println(dataFile.getId() + "  -  " + dataFile.getFileName());
                        }
                        System.out.println("Enter the id of file to unhide ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isValidID = false;
                        for (Data dataFile : files) { // Changed variable name to dataFile
                            if (dataFile.getId() == id) {
                                isValidID = true;
                                break;
                            }
                        }
                        if (isValidID) {
                            DataDAO.unhide(id);
                        } else {
                            System.out.println("Wrong ID");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 0:
                    System.exit(0);
                    break;
            }
        } while (true);
    }

    }
