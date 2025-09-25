package controller.customerController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CustomerInfo;

import java.sql.*;

public class CustomerManagementController implements CustomerManagementService {

    @Override
    public void addCustomerDetails(CustomerInfo customerInfo) {
        try (Connection connection =DBConnection.getInstance().getConnection()){

            String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, customerInfo.getCustID());
            preparedStatement.setString(2, customerInfo.getCustTitle());
            preparedStatement.setString(3, customerInfo.getCustName());
            preparedStatement.setString(4, customerInfo.getDob());
            preparedStatement.setDouble(5, customerInfo.getSalary());
            preparedStatement.setString(6, customerInfo.getCustAddress());
            preparedStatement.setString(7, customerInfo.getCity());
            preparedStatement.setString(8, customerInfo.getProvince());
            preparedStatement.setString(9, customerInfo.getPostalCode());
            preparedStatement.setString(10, customerInfo.getCustPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(String CustTitle, String CustName, String DOB, String Salary,
                                  String Address, String City, String Province,
                                  String PostalCode, String Password, String CustId) {
        String SQL = "UPDATE customer SET custTitle=?, custName=?, dob=?, salary=?, " +
                "custAddress=?, city=?, province=?, postalCode=?, custPassword=? WHERE custID=?";
        try (Connection connection = DBConnection.getInstance().getConnection();
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

    @Override
    public ObservableList<CustomerInfo> getAllCustomerInfo() {
        ObservableList<CustomerInfo> customerInfos = FXCollections.observableArrayList();
        try (Connection connection = DBConnection.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CustomerInfo customerInfo = new CustomerInfo(
                        resultSet.getString("custID"),
                        resultSet.getString("custTitle"),
                        resultSet.getString("custName"),
                        resultSet.getString("dob"),
                        resultSet.getDouble("salary"),
                        resultSet.getString("custAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postalCode"),
                        resultSet.getString("custPassword")
                );
                customerInfos.add(customerInfo);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerInfos;
    }

    @Override
    public int deleteCustomer(String id) {
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            String SQL = "DELETE FROM customer WHERE CustID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
