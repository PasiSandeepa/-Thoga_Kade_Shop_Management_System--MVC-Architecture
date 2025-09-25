package controller.customerController;

import javafx.collections.ObservableList;
import model.CustomerInfo;

public interface  CustomerManagementService {
  void addCustomerDetails(CustomerInfo customerInfo);

  boolean updateCustomer(String CustTitle, String CustName, String DOB, String Salary,
                         String Address, String City, String Province,
                         String PostalCode, String Password, String CustId);

  ObservableList<CustomerInfo> getAllCustomerInfo();

  int deleteCustomer(String id);
}
