package lk.ijse.gdse66.pos_backend.bo.custom;

import lk.ijse.gdse66.pos_backend.bo.SuperBO;

import java.sql.SQLException;

public interface PurchaseOrderBO extends SuperBO {
    boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;
}
