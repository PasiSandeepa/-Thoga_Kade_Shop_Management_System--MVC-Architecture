package controller.OrderDetailsController;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.OrderDetailsInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailsManagementFormController implements Initializable {


    private OrderDetailsManagementService orderDetailService = new OrderDetailsManagementController();

    @FXML
    private TableColumn<?, ?> ColOrderID;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colOrderQTy;

    @FXML
    private TableColumn<?, ?> colitemCode;

    @FXML
    private TableView<OrderDetailsInfo> tblOrderDetails;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtOrderID;

    @FXML
    private JFXTextField txtOrderQTY;


    @FXML
    void btnADDOnAction(ActionEvent event) {

        OrderDetailsInfo orderDetailsInfo = new OrderDetailsInfo(
                txtOrderID.getText(),
                txtItemCode.getText(),
                Integer.valueOf(txtOrderQTY.getText()),
                Double.valueOf(txtDiscount.getText())
        );


        orderDetailService.AddOrderDetails(orderDetailsInfo);
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {

        ObservableList<OrderDetailsInfo> orderDetail = orderDetailService.getAllOrderDetail();


        ColOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colitemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderQTy.setCellValueFactory(new PropertyValueFactory<>("orderQTY"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));


        tblOrderDetails.setItems(orderDetail);
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        OrderDetailsInfo orderDetail = new OrderDetailsInfo(
                txtOrderID.getText(),
                txtItemCode.getText(),
                Integer.parseInt(txtOrderQTY.getText()),
                Double.parseDouble(txtDiscount.getText())
        );


        orderDetailService.updateOrderDetail(orderDetail);

    }


    @FXML
    void btnViewOrderDetailsOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/customer_management.fxml"));
            txtOrderID.getScene().setRoot(root); // current scene replace
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Order Management page!").show();
            e.printStackTrace();
        }
    }




    @FXML
    void btndeleteOnAction(ActionEvent event) {
        String orderID = txtOrderID.getText();
        String itemCode = txtItemCode.getText();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Order Details Deleted!");
        alert.showAndWait();

        orderDetailService.deleteOrderDetail(orderID, itemCode);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colitemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderQTy.setCellValueFactory(new PropertyValueFactory<>("orderQTY"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));


        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        txtOrderID.setText(newValue.getOrderID());
                        txtItemCode.setText(newValue.getItemCode());
                        txtOrderQTY.setText(String.valueOf(newValue.getOrderQTY()));
                        txtDiscount.setText(String.valueOf(newValue.getDiscount()));
                    }
                }
        );
    }
}
