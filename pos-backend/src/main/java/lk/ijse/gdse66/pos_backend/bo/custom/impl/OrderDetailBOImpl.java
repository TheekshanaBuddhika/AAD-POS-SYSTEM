package lk.ijse.gdse66.pos_backend.bo.custom.impl;

import lk.ijse.gdse66.pos_backend.bo.custom.OrderDetailBO;
import lk.ijse.gdse66.pos_backend.dao.DAOFactory;
import lk.ijse.gdse66.pos_backend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse66.pos_backend.dto.OrderDetailDTO;
import lk.ijse.gdse66.pos_backend.entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {

    OrderDetailsDAO detailsDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public ArrayList<OrderDetailDTO> getAllDetails(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> detailDTOS = new ArrayList<>();
        ArrayList<OrderDetails> details = detailsDAO.getAll(connection);
        for (OrderDetails i : details) {
            detailDTOS.add(new OrderDetailDTO(i.getOrderId(), i.getItemCode(), i.getQty(), i.getUnitPrice()));
        }
        return detailDTOS;
    }
}
