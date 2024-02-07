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

import java.sql.Connection;
import java.sql.SQLException;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    ItemDAO itemDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public boolean saveOrder(OrderDTO dto, Connection connection) throws SQLException, ClassNotFoundException {

        try {

            if (orderDAO.exist(dto.getId(),connection)){
                return false;
            }

            connection.setAutoCommit(false);

            Orders orderEntity = new Orders(dto.getOrderId(), dto.getOrderDate(), dto.getCustomerId());
            boolean orderAdded = orderDAO.save(orderEntity);
            if (!orderAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO odDTO : dto.getOrderDetaisList()) {
                OrderDetails orderDetailsEntity = new OrderDetails(odDTO.getOrderID(), odDTO.getItemCode(), odDTO.getQty(), odDTO.getUnitPrice());
                boolean odAdded = orderDetailsDAO.save(orderDetailsEntity);
                if (!odAdded) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item
                ItemDTO item = findItemByID(orderDetailsEntity.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetailsEntity.getQty());
                boolean itemUpdate = itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice()));

                if (!itemUpdate) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
