package lk.ijse.gdse66.pos_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetails {

    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;

}
