package lk.ijse.gdse66.pos_backend.bo.custom.impl;

import lk.ijse.gdse66.pos_backend.bo.custom.PurchaseOrderBO;
import lk.ijse.gdse66.pos_backend.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    @Override
    public boolean saveOrder(OrderDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            /*if order id already exist*/
            if (orderDAO.exist(dto.getOrderId())) {
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
