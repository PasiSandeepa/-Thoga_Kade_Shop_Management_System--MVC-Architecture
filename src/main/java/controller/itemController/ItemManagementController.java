package controller.itemController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ItemInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemManagementController implements ItemManagementService {

    @Override
    public void AddItemDetails(ItemInfo itemInfo) {
        String SQL = "INSERT INTO item VALUES(?,?,?,?,?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, itemInfo.getItemCode());
            preparedStatement.setString(2, itemInfo.getDescription());
            preparedStatement.setString(3, itemInfo.getPackSize());
            preparedStatement.setDouble(4, itemInfo.getUnitPrice());
            preparedStatement.setInt(5, itemInfo.getQtyOnHand());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while saving item!", e);
        }
    }

    @Override
    public ObservableList<ItemInfo> getAllItemInfo() {
        ObservableList<ItemInfo> itemInfos = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM item";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ItemInfo itemInfo = new ItemInfo(
                        resultSet.getString("itemCode"),
                        resultSet.getString("description"),
                        resultSet.getString("packSize"),
                        resultSet.getDouble("unitPrice"),
                        resultSet.getInt("qtyOnHand")

                );
                itemInfos.add(itemInfo);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemInfos;
    }
}


