package lk.ijse.gdse66.pos_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private String id;
    private LocalDate date;
    private String  customerID;

}
