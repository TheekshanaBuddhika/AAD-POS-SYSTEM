package lk.ijse.gdse66.pos_backend.dao.custom.impl;

import lk.ijse.gdse66.pos_backend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse66.pos_backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse66.pos_backend.entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetails entity, Connection connection) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection,"INSERT INTO order_detail (orderId, itemCode, qty ,unitPrice) VALUES (?,?,?,?)", entity.getOrderId(), entity.getItemCode(), entity.getQty(), entity.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetails dto, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(String code, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }
}
