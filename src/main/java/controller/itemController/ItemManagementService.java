package controller.itemController;

import javafx.collections.ObservableList;
import model.ItemInfo;

public interface ItemManagementService {
    void AddItemDetails(ItemInfo itemInfo);

    ObservableList<ItemInfo>getAllItemInfo();

}
