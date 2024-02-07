package lk.ijse.gdse66.pos_backend.dao.custom.impl;

import lk.ijse.gdse66.pos_backend.dao.custom.ItemDAO;
import lk.ijse.gdse66.pos_backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse66.pos_backend.entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(connection,"SELECT * FROM item");
        ArrayList<Item> allItems = new ArrayList<>();
        while (rst.next()) {
            allItems.add(new Item(rst.getString(1), rst.getString(2), rst.getDouble(3),rst.getInt(4)));
        }
        return allItems;
    }

    @Override
    public boolean save(Item dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

//    @Override
//    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
//        return SQLUtil.execute("INSERT INTO item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)", entity.getCode(), entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand());
//    }
//
//    @Override
//    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
//        return SQLUtil.execute("UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?", entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getCode());
//    }
//
//    @Override
//    public boolean delete(String code) throws SQLException, ClassNotFoundException {
//        return SQLUtil.execute("DELETE FROM item WHERE code=?", code);
//    }
}
