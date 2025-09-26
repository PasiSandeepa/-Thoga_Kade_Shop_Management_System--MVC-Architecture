package controller.orderController;

import javafx.collections.ObservableList;
import model.ItemInfo;
import model.OrderInfo;

public interface OrderManagementService {
    void AddOrderDetails(OrderInfo orderInfo);

     void updateOrder(OrderInfo orderInfo);

    ObservableList<OrderInfo> getAllOrderDetails();

    int deleteOrder(String id);
}
