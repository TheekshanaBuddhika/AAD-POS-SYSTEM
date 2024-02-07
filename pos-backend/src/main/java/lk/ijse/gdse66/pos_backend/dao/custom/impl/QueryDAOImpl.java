package lk.ijse.gdse66.pos_backend.dao.custom.impl;

import lk.ijse.gdse66.pos_backend.dao.custom.QueryDAO;
import lk.ijse.gdse66.pos_backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse66.pos_backend.dto.OrderJoinEntity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<OrderJoinEntity> searchOrderByOID(String oid, Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderJoinEntity> allRecords = new ArrayList<>();
        String sql = "select orders.id,Orders.date,Orders.customerID,order_detail.orderId,order_detail.itemCode,order_detail.qty,order_detail.unitPrice from orders inner join order_detail on Orders.oid=OrderDetails.oid where Orders.oid=?";
        ResultSet rst = SQLUtil.execute(connection,sql, oid);
        while (rst.next()) {
            String orderID = rst.getString(1);
            LocalDate orderDate = LocalDate.parse(rst.getString(2));
            String customerID = rst.getString(3);
            String itemCode = rst.getString(5);
            int itemQty = rst.getInt(6);
            BigDecimal unitPrice = rst.getBigDecimal(7);
            OrderJoinEntity joinEntity = new OrderJoinEntity(orderID, orderDate, customerID, itemCode, itemQty, unitPrice);
            allRecords.add(joinEntity);
        }
        return allRecords;
    }
}
