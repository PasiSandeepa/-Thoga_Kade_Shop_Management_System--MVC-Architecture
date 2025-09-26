package controller.orderController;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ItemInfo;
import model.OrderInfo;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OrderManagementFormController implements Initializable {

    private OrderManagementService orderService = new OrderManagementController();


    @FXML
    private TableColumn<?, ?> ColCustID;

    @FXML
    private TableColumn<?, ?> ColOrderID;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableView<OrderInfo> tblOrder;

    @FXML
    private JFXTextField txtCustID;

    @FXML
    private JFXTextField txtOrderDate;

    @FXML
    private JFXTextField txtOrderID;

    @FXML
    void btnADDOnAction(ActionEvent event) {
        OrderInfo orderInfo = new OrderInfo(
                txtOrderID.getText(),
                LocalDate.parse(txtOrderDate.getText()),
                txtCustID.getText()

        );
        orderService.AddOrderDetails(orderInfo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Order added successfully!");
        alert.showAndWait();
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        ColOrderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        ColCustID.setCellValueFactory(new PropertyValueFactory<>("CustID"));


        ObservableList<OrderInfo> orderInfos = orderService.getAllOrderDetails();
        tblOrder.setItems(orderInfos);
        new Alert(Alert.AlertType.INFORMATION, "Item data reloaded successfully!").show();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        OrderInfo orderInfo = new OrderInfo(
                txtOrderID.getText(),
                LocalDate.parse(txtOrderDate.getText()),
                txtCustID.getText()
        );

        try {
            orderService.updateOrder(orderInfo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Order updated successfully!");
            alert.showAndWait();

        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Update Failed");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void btnViewOrderDetailsOnAction(ActionEvent event) {

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clearFields() {
        txtOrderID.clear();
        txtOrderDate.clear();
        txtCustID.clear();
    }

    @FXML
    void btndeleteOnAction(ActionEvent event) {
        String id = txtOrderID.getText();
        int affectedRows = orderService.deleteOrder(id); // deleteitem method DB delete returns affected rows

        if (affectedRows > 0) {
            showAlert(Alert.AlertType.INFORMATION, "Delete Successful", "Order deleted successfully!");
            btnReloadOnAction(event);
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Delete Failed", "Order not found!");
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        ColCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));

        // Load data
        btnReloadOnAction(null);

        // Row click event
        tblOrder.setOnMouseClicked(event -> {
            OrderInfo selectedOrder = tblOrder.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                txtOrderID.setText(selectedOrder.getOrderID());
                txtOrderDate.setText(selectedOrder.getOrderDate().toString());
                txtCustID.setText(selectedOrder.getCustID());
            }
        });
    }
    }

