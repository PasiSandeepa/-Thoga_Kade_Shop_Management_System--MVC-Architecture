package controller.OrderDetailsController;

import javafx.collections.ObservableList;
import model.OrderDetailsInfo;
import model.OrderInfo;

public interface OrderDetailsManagementService {
    void AddOrderDetails(OrderDetailsInfo orderDetailsInfo);

    void deleteOrderDetail(String orderID, String itemCode);

    ObservableList<OrderDetailsInfo> getAllOrderDetail();

    public void updateOrderDetail(OrderDetailsInfo orderDetailsInfo);

}
