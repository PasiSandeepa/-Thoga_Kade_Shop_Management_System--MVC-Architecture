package controller;

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

    public static boolean updateCustomer(String CustTitle, String CustName, String DOB, String Salary, String Address, String City, String Province, String PostalCode, String Password, String CustId) {
        String SQL = "UPDATE customer SET custTitle=?, custName=?, dob=?, salary=?, custAddress=?, city=?, province=?, postalCode=?, custPassword=? WHERE custID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, CustTitle);
            preparedStatement.setString(2, CustName);
            preparedStatement.setString(3, DOB);
            preparedStatement.setDouble(4, Double.parseDouble(Salary));
            preparedStatement.setString(5, Address);
            preparedStatement.setString(6, City);
            preparedStatement.setString(7, Province);
            preparedStatement.setString(8, PostalCode);
            preparedStatement.setString(9, Password);
            preparedStatement.setString(10, CustId);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateCustomer(String CustTitle, String CustName, String DOB, String Salary, String Address, String City, String Province, String PostalCode, String Password, String CustId) {
        String SQL = "UPDATE customer SET custTitle=?, custName=?, dob=?, salary=?, custAddress=?, city=?, province=?, postalCode=?, custPassword=? WHERE custID=?";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, CustTitle);
            preparedStatement.setString(2, CustName);
            preparedStatement.setString(3, DOB);
            preparedStatement.setDouble(4, Double.parseDouble(Salary));
            preparedStatement.setString(5, Address);
            preparedStatement.setString(6, City);
            preparedStatement.setString(7, Province);
            preparedStatement.setString(8, PostalCode);
            preparedStatement.setString(9, Password);
            preparedStatement.setString(10, CustId);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}