package lk.ijse.gdse66.pos_backend.bo.custom;

import lk.ijse.gdse66.pos_backend.bo.SuperBO;
import lk.ijse.gdse66.pos_backend.dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
    ArrayList<OrderDetailDTO> getAllDetails(Connection connection) throws SQLException, ClassNotFoundException;
}
