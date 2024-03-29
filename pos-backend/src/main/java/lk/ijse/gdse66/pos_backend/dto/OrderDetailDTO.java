package lk.ijse.gdse66.pos_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailDTO {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
