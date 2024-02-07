package lk.ijse.gdse66.pos_backend.bo.custom.impl;

import lk.ijse.gdse66.pos_backend.bo.custom.PurchaseOrderBO;
import lk.ijse.gdse66.pos_backend.dao.DAOFactory;
import lk.ijse.gdse66.pos_backend.dao.custom.ItemDAO;
import lk.ijse.gdse66.pos_backend.dao.custom.OrderDAO;
import lk.ijse.gdse66.pos_backend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse66.pos_backend.dto.ItemDTO;
import lk.ijse.gdse66.pos_backend.dto.OrderDTO;
import lk.ijse.gdse66.pos_backend.dto.OrderDetailDTO;
import lk.ijse.gdse66.pos_backend.entity.Item;
import lk.ijse.gdse66.pos_backend.entity.OrderDetails;
import lk.ijse.gdse66.pos_backend.entity.Orders;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    ItemDAO itemDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public boolean saveOrder(OrderDTO dto, DataSource source) throws SQLException, ClassNotFoundException {

        try {

            Connection connection = source.getConnection();

            if (orderDAO.exist(dto.getId(),source.getConnection())){
                return false;
            }

            connection.setAutoCommit(false);

            Orders orderEntity = new Orders(dto.getId(), dto.getDate(), dto.getCustomerId());
            boolean orderAdded = orderDAO.save(orderEntity,source.getConnection());
            if (!orderAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO odDTO : dto.getOrderDetaisList()) {
                OrderDetails orderDetailsEntity = new OrderDetails(odDTO.getOrderId(), odDTO.getItemCode(), odDTO.getQty(), odDTO.getUnitPrice());
                boolean odAdded = orderDetailsDAO.save(orderDetailsEntity, source.getConnection());
                if (!odAdded) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

                ItemDTO item = findItemByID(orderDetailsEntity.getItemCode(), source.getConnection());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetailsEntity.getQty());
                boolean itemUpdate = itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()), source.getConnection());

                if (!itemUpdate) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public ItemDTO findItemByID(String code,Connection connection) {
        try {
            Item search = itemDAO.search(code,connection);
            return new ItemDTO(search.getCode(),search.getDescription(),search.getUnitPrice(),search.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
