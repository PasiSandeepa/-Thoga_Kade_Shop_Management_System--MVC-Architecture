package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
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
import java.sql.*;
import java.util.ResourceBundle;

public class CustomermanagementFormController  implements Initializable {

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

    @FXML
    void btnADDOnAction(ActionEvent event) {
        String CustId = txtCustId.getText();
        String CustTitle = txtCustTitle.getText();
        String CustName = txtCustName.getText();
        String DOB = txtDOB.getText();
        String Salary = txtSalary.getText();
        String Address = txtCustAddress.getText();
        String City = txtCity.getText();
        String Province = txtProvince.getText();
        String PostalCode = txtPostalCode.getText();
        String Password = txtCustPassword.getText();

        CustomerManagementController customerManagementController = new CustomerManagementController();
        customerManagementController.addCustomerDetails(CustId,CustTitle,CustName,DOB,Salary,Address,City,Province,PostalCode,Password);

    }


    @FXML
    void btnReloadOnAction(ActionEvent event) {
        ObservableList<CustomerInfo> customerInfos = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);

            while (resultSet.next()) {
                CustomerInfo customerInfo = new CustomerInfo(resultSet.getString("custID"), resultSet.getString("custTitle"), resultSet.getString("custName"), resultSet.getString("dob"), resultSet.getDouble("salary"), resultSet.getString("custAddress"), resultSet.getString("city"), resultSet.getString("province"), resultSet.getString("postalCode"), resultSet.getString("custPassword"));
                System.out.println(customerInfos);
                customerInfos.add(customerInfo);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Data");
            alert.setHeaderText(null);
            alert.setContentText("Customer's data successfully loaded!");
            alert.showAndWait();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        tblCustomers.setItems(customerInfos);


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String CustId = txtCustId.getText();
        String CustTitle = txtCustTitle.getText();
        String CustName = txtCustName.getText();
        String DOB = txtDOB.getText();
        String Salary = txtSalary.getText();
        String Address = txtCustAddress.getText();
        String City = txtCity.getText();
        String Province = txtProvince.getText();
        String PostalCode = txtPostalCode.getText();
        String Password = txtCustPassword.getText();

        CustomerManagementController customerManagementController = new CustomerManagementController();
        customerManagementController.UpdateCustomer(CustTitle,CustName,DOB,Salary,Address,City,Province,PostalCode,Password,CustId);


    }


    @FXML
    void btndeleteOnAction(ActionEvent event) {
        String id = txtCustId.getText();

        String SQL = "DELETE FROM customer WHERE CustID = ? ";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("customer deleted successfully");
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

            } else {
                System.out.println("Delete failed.customer ID not found");


            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Successful");
            alert.setHeaderText(null);
            alert.setContentText("Customer's data has been deleted successfully!");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void btnviewcustomerOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Item_Form.fxml"))));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("View Item");
            alert.setHeaderText(null);
            alert.setContentText("Viwe Item Page is  loaded!");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        // Table row select listener
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

