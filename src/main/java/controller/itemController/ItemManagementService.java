package controller.itemController;

import javafx.collections.ObservableList;
import model.ItemInfo;

public interface ItemManagementService {
    void AddItemDetails(ItemInfo itemInfo);

    ObservableList<ItemInfo> getAllItemInfo();

    boolean Updateitem(String itemCode, String description, String packSize, Double unitPrice, Integer qtyOnHand);

    int deleteitem(String id);
}
