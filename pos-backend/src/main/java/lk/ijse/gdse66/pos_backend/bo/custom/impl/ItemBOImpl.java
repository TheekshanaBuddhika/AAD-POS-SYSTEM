package lk.ijse.gdse66.pos_backend.bo.custom.impl;

import lk.ijse.gdse66.pos_backend.bo.custom.ItemBO;
import lk.ijse.gdse66.pos_backend.dto.ItemDTO;
import lk.ijse.gdse66.pos_backend.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> list = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item i : all) {
            list.add(new ItemDTO(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand()));
        }
        return list;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }
}
