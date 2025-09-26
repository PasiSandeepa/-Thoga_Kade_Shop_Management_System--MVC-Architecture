package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDetailsInfo {

    private String orderID;
    private String itemCode;
    private int orderQTY;
    private double discount;

}
