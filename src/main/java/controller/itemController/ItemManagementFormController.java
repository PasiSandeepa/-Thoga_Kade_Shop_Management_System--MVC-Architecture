package controller.itemController;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ItemInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemManagementFormController implements Initializable {



    @FXML
    private TableColumn<?, ?> ColUnitPrice;

    @FXML
    private TableColumn<?, ?> Coldescription;

    @FXML
    private TableColumn<?, ?> ColitemCode;

    @FXML
    private TableColumn<?, ?> ColpackSize;

    @FXML
    private TableColumn<?, ?> ColqtyOnHand;

    @FXML
    private TableView<ItemInfo> tblItem;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtUnitPrize;

    @FXML
    private JFXTextField txtqtyOnHand;

    private ItemManagementService itemService = new ItemManagementController();

    private ObservableList<ItemInfo> itemInfos;

    @FXML
    void btnADDOnAction(ActionEvent event) {
        ItemInfo itemInfo = new ItemInfo(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrize.getText()),
                Integer.parseInt(txtqtyOnHand.getText())

        );
        itemService.AddItemDetails(itemInfo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Item added successfully!");
        alert.showAndWait();


    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        ColitemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        Coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        ColpackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        ColUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        ColqtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));


        ObservableList<ItemInfo> itemInfos = itemService.getAllItemInfo();
        tblItem.setItems(itemInfos);
        new Alert(Alert.AlertType.INFORMATION, "Item data reloaded successfully!").show();
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String itemCode = txtItemCode.getText();
        boolean updated = itemService.Updateitem(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrize.getText()),
                Integer.parseInt(txtqtyOnHand.getText())
        );

        if (updated) {
            showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Item data updated successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Update Failed", "Update failed! Check Item ID.");
        }
        // table Refresh
        btnReloadOnAction(event);
    }

    private void clearFields() {
        txtItemCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrize.clear();
        txtqtyOnHand.clear();
    }

    @FXML
    void btndeleteOnAction(ActionEvent event) {
        String id = txtItemCode.getText();
        int affectedRows = itemService.deleteitem(id); // deleteitem method DB delete returns affected rows

        if (affectedRows > 0) {
            showAlert(Alert.AlertType.INFORMATION, "Delete Successful", "Item deleted successfully!");
            btnReloadOnAction(event);
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Delete Failed", "Item not found!");
        }

}
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ColitemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
//        Coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
//        ColpackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
//        ColUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
//        ColqtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
//
//        // Initial load
//        tblItem.setItems(itemService.getAllItemInfo());

        // Event: row select karaddi text fields update
        tblItem.setOnMouseClicked(event -> {
            ItemInfo selectedItem = (ItemInfo) tblItem.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                txtItemCode.setText(selectedItem.getItemCode());
                txtDescription.setText(selectedItem.getDescription());
                txtPackSize.setText(selectedItem.getPackSize());
                txtUnitPrize.setText(String.valueOf(selectedItem.getUnitPrice()));
                txtqtyOnHand.setText(String.valueOf(selectedItem.getQtyOnHand()));
            }
        });
    }

    public void btnviewOrderOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/order_management.fxml"))));
            stage.show();

            showAlert(Alert.AlertType.INFORMATION, "View Order", "View Order Page loaded!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    }


