package lk.ijse.gdse66.pos_backend.dao.custom;

import lk.ijse.gdse66.pos_backend.dao.SuperDAO;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {

    ArrayList<CustomEntity> searchOrderByOID(String oid, Connection connection) throws SQLException, ClassNotFoundException;
}

