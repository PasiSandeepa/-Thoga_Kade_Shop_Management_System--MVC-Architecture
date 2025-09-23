package controller;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerManagementController {
    public void addCustomerDetails(String CustId, String CustTitle, String CustName, String DOB, String Salary, String Address, String City, String Province, String PostalCode, String Password) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setObject(1, CustId);
            preparedStatement.setObject(2, CustTitle);
            preparedStatement.setObject(3, CustName);
            preparedStatement.setObject(4, DOB);
            preparedStatement.setObject(5, Salary);
            preparedStatement.setObject(6, Address);
            preparedStatement.setObject(7, City);
            preparedStatement.setObject(8, Province);
            preparedStatement.setObject(9, PostalCode);
            preparedStatement.setObject(10, Password);


            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}