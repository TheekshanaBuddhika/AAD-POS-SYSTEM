package lk.ijse.gdse66.pos_backend.dao.custom;

import lk.ijse.gdse66.pos_backend.dao.SuperDAO;
import lk.ijse.gdse66.pos_backend.dto.OrderJoinEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    ArrayList<OrderJoinEntity> searchOrderByOID(String oid, Connection connection) throws SQLException, ClassNotFoundException;
}

