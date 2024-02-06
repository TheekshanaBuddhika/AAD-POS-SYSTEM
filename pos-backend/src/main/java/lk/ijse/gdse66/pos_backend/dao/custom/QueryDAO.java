package lk.ijse.gdse66.pos_backend.dao.custom;

import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    public ArrayList<CustomEntity> searchOrderByOID(String oid) throws SQLException, ClassNotFoundException;
}
