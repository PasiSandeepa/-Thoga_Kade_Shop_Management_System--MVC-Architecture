package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class CustomerInfo {
    private String custID;
    private String custTitle;
    private String custName;
    private String dob;
    private Double salary;
    private String custAddress;
    private String city;
    private String province;
    private String postalCode;
    private String custPassword;

}
