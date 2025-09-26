package controller.orderController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.OrderInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderManagementController implements  OrderManagementService {

    private boolean customerExists(Connection conn, String custId) throws SQLException {
        String checkSql = "SELECT 1 FROM customer WHERE CustID = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setString(1, custId.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public void AddOrderDetails(OrderInfo orderInfo) {
        String SQL = "INSERT INTO orders (OrderID, OrderDate, CustID) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            // Check if customer exists before inserting
            if (!customerExists(connection, orderInfo.getCustID())) {
                throw new SQLException("Customer ID not found: " + orderInfo.getCustID());
            }

            preparedStatement.setString(1, orderInfo.getOrderID());
            preparedStatement.setDate(2, java.sql.Date.valueOf(orderInfo.getOrderDate())); // LocalDate → SQL Date
            preparedStatement.setString(3, orderInfo.getCustID());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while saving order!", e);
        }
    }

    @Override
    public void updateOrder(OrderInfo orderInfo) {
        String SQL = "UPDATE orders SET orderDate = ?, custID = ? WHERE orderID = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            // Check customer exists before update
            if (!customerExists(connection, orderInfo.getCustID())) {
                throw new SQLException("Customer ID not found: " + orderInfo.getCustID());
            }

            preparedStatement.setDate(1, java.sql.Date.valueOf(orderInfo.getOrderDate()));
            preparedStatement.setString(2, orderInfo.getCustID());
            preparedStatement.setString(3, orderInfo.getOrderID());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No order found with ID: " + orderInfo.getOrderID());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating order!", e);
        }
    }

    @Override
    public ObservableList<OrderInfo> getAllOrderDetails() {
        ObservableList<OrderInfo> orderInfos = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM orders";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                OrderInfo orderInfo = new OrderInfo(
                        resultSet.getString("OrderID"),
                        resultSet.getDate("OrderDate").toLocalDate(), // Date conversion
                        resultSet.getString("CustID")
                );

                orderInfos.add(orderInfo);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderInfos;
    }

    @Override
    public int deleteOrder(String id) {
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            String SQL = "DELETE FROM orders WHERE  OrderID= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}






