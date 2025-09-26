package controller.OrderDetailsController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.OrderDetailsInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsManagementController implements  OrderDetailsManagementService{
    @Override
    public void AddOrderDetails(OrderDetailsInfo orderDetailsInfo) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();


            PreparedStatement check = connection.prepareStatement(
                    "SELECT 1 FROM orderDetail WHERE orderID=?");
            check.setString(1, orderDetailsInfo.getOrderID());
            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Item " + orderDetailsInfo.getItemCode() + " does not exist!");
                new Alert(Alert.AlertType.ERROR, "Item " + orderDetailsInfo.getItemCode() + " not found!").show();
                return;
            }

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO orderDetail (orderID, itemCode, orderQTY,discount) VALUES (?, ?, ?, ?)");

            ps.setString(1, orderDetailsInfo.getOrderID());
            ps.setString(2,orderDetailsInfo.getItemCode());
            ps.setInt(3, orderDetailsInfo.getOrderQTY());
            ps.setDouble(4, orderDetailsInfo.getDiscount());


            int result = ps.executeUpdate();
            System.out.println("✅ Rows Inserted: " + result);
        }
        catch (SQLException e) {
            System.out.println("❌ SQL Error:");
            e.printStackTrace();
        }


    }

    @Override
    public void deleteOrderDetail(String orderID, String itemCode) {
        try {Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM orderDetail WHERE orderID = ? AND itemCode = ?");

            ps.setString(1, orderID);
            ps.setString(2, itemCode);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("✅ Deleted Successfully!");
            } else {
                System.out.println("⚠ No matching record found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ObservableList<OrderDetailsInfo> getAllOrderDetail() {
        ObservableList<OrderDetailsInfo> orderDetail = FXCollections.observableArrayList();
        try { Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orderDetail");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetailsInfo od = new OrderDetailsInfo(
                        rs.getString("orderID"),
                        rs.getString("itemCode"),
                        rs.getInt("orderQTY"),
                        rs.getInt("discount")
                );
                orderDetail.add(od);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetail;
    }

    @Override
    public void updateOrderDetail(OrderDetailsInfo orderDetailsInfo) {
        try {Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE orderdetail SET orderQTY=?, discount=? WHERE orderID=? AND itemCode=?");

            ps.setInt(1, orderDetailsInfo.getOrderQTY());
            ps.setDouble(2, orderDetailsInfo.getDiscount());
            ps.setString(3, orderDetailsInfo.getOrderID());
            ps.setString(4, orderDetailsInfo.getItemCode());

            int result = ps.executeUpdate();
            System.out.println("✅ Rows Updated: " + result);

            if(result > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "OrderDetail Updated Successfully!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No matching OrderDetail found!");
                alert.show();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}





