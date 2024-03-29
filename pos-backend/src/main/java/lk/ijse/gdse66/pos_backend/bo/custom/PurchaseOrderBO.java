package lk.ijse.gdse66.pos_backend.bo.custom;

import lk.ijse.gdse66.pos_backend.bo.SuperBO;
import lk.ijse.gdse66.pos_backend.dto.OrderDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public interface PurchaseOrderBO extends SuperBO {
    boolean saveOrder(OrderDTO dto, DataSource source) throws SQLException, ClassNotFoundException;
}
