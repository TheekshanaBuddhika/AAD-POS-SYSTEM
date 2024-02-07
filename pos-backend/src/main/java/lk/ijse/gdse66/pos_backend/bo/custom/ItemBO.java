package lk.ijse.gdse66.pos_backend.bo.custom;

import lk.ijse.gdse66.pos_backend.bo.SuperBO;
import lk.ijse.gdse66.pos_backend.dto.ItemDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {

     ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException ;

     boolean deleteItem(String code ,Connection connection) throws SQLException, ClassNotFoundException ;

     boolean saveItem(ItemDTO dto,Connection connection) throws SQLException, ClassNotFoundException ;

     boolean updateItem(ItemDTO dto,Connection connection) throws SQLException, ClassNotFoundException;

}
