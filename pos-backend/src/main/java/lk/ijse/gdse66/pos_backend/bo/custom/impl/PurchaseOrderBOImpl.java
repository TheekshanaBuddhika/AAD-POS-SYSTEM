package lk.ijse.gdse66.pos_backend.bo.custom.impl;

import lk.ijse.gdse66.pos_backend.bo.custom.PurchaseOrderBO;
import lk.ijse.gdse66.pos_backend.dto.OrderDTO;

import java.sql.SQLException;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    @Override
    public boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }
}
