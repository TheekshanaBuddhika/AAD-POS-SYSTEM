package lk.ijse.gdse66.pos_backend.dao.custom.impl;

import lk.ijse.gdse66.pos_backend.dao.custom.OrderDAO;
import lk.ijse.gdse66.pos_backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse66.pos_backend.entity.Orders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Orders dto, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Orders dto, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String orderID, Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(connection,"SELECT id FROM `orders` WHERE id=?", orderID);
        return rst.next();
    }

    @Override
    public Orders search(String code, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }
}
