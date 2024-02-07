package lk.ijse.gdse66.pos_backend.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private String id;
    private LocalDate orderDate;
    private String customerId;
    List<OrderDetailDTO> orderDetaisList;



}
