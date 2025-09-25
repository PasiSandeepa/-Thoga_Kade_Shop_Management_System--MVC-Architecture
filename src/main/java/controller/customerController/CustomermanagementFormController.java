package controller.customerController;

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
import model.CustomerInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomermanagementFormController implements Initializable {

    private CustomerManagementService customerManagementService = new CustomerManagementController();

    @FXML
    private TableColumn<?, ?> colCity;
    @FXML
    private TableColumn<?, ?> colCustAddress;
    @FXML
    private TableColumn<?, ?> colCustID;
    @FXML
    private TableColumn<?, ?> colCustName;
    @FXML
    private TableColumn<?, ?> colCustPassword;
    @FXML
    private TableColumn<?, ?> colCustTitle;
    @FXML
    private TableColumn<?, ?> colDOB;
    @FXML
    private TableColumn<?, ?> colPostalCode;
    @FXML
    private TableColumn<?, ?> colProvince;
    @FXML
    private TableColumn<?, ?> colSalary;
    @FXML
    private TableView<CustomerInfo> tblCustomers;

    @FXML
    private JFXTextField txtCity;
    @FXML
    private JFXTextField txtCustAddress;
    @FXML
    private JFXTextField txtCustId;
    @FXML
    private JFXTextField txtCustName;
    @FXML
    private JFXTextField txtCustPassword;
    @FXML
    private JFXTextField txtCustTitle;
    @FXML
    private JFXTextField txtDOB;
    @FXML
    private JFXTextField txtPostalCode;
    @FXML
    private JFXTextField txtProvince;
    @FXML
    private JFXTextField txtSalary;

    private ObservableList<CustomerInfo> customerData;

    @FXML
    void btnADDOnAction(ActionEvent event) {
        try {
            CustomerInfo customerInfo = new CustomerInfo(
                    txtCustId.getText(),
                    txtCustTitle.getText(),
                    txtCustName.getText(),
                    txtDOB.getText(),
                    Double.parseDouble(txtSalary.getText()),
                    txtCustAddress.getText(),
                    txtCity.getText(),
                    txtProvince.getText(),
                    txtPostalCode.getText(),
                    txtCustPassword.getText()
            );

            customerManagementService.addCustomerDetails(customerInfo);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer added successfully!");
            btnReloadOnAction(event);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add customer! " + e.getMessage());
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        colCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        colCustTitle.setCellValueFactory(new PropertyValueFactory<>("custTitle"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colCustPassword.setCellValueFactory(new PropertyValueFactory<>("custPassword"));

        customerData = customerManagementService.getAllCustomerInfo();
        tblCustomers.setItems(customerData);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String custId = txtCustId.getText();

        boolean updated = customerManagementService.updateCustomer(
                txtCustTitle.getText(),
                txtCustName.getText(),
                txtDOB.getText(),
                txtSalary.getText(),
                txtCustAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText(),
                txtCustPassword.getText(),
                custId
        );

        if (updated) {
            showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Customer data updated successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Update Failed", "Update failed! Check customer ID.");
        }
        btnReloadOnAction(event);
    }

    @FXML
    void btndeleteOnAction(ActionEvent event) {
        String id = txtCustId.getText();
        int affectedRows = customerManagementService.deleteCustomer(id);

        if (affectedRows > 0) {
            showAlert(Alert.AlertType.INFORMATION, "Delete Successful", "Customer deleted successfully!");
            btnReloadOnAction(event);
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Delete Failed", "Customer not found!");
        }
    }

    @FXML
    public void btnviewcustomerOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Item_management.fxml"))));
            stage.show();

            showAlert(Alert.AlertType.INFORMATION, "View Item", "View Item Page loaded!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtCustId.clear();
        txtCustTitle.clear();
        txtCustName.clear();
        txtDOB.clear();
        txtSalary.clear();
        txtCustAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        txtCustPassword.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnReloadOnAction(new ActionEvent());

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtCustId.setText(newSelection.getCustID());
                txtCustTitle.setText(newSelection.getCustTitle());
                txtCustName.setText(newSelection.getCustName());
                txtDOB.setText(newSelection.getDob());
                txtSalary.setText(String.valueOf(newSelection.getSalary()));
                txtCustAddress.setText(newSelection.getCustAddress());
                txtCity.setText(newSelection.getCity());
                txtProvince.setText(newSelection.getProvince());
                txtPostalCode.setText(newSelection.getPostalCode());
                txtCustPassword.setText(newSelection.getCustPassword());
            }
        });
    }
}
