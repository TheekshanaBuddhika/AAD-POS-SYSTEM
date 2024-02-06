package lk.ijse.gdse66.pos_backend.entity;

import java.time.LocalDate;

public class Orders {
    private String orderId;
    private LocalDate date;
    private String  customerID;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Orders(String orderId, LocalDate date, String customerID) {
        this.orderId = orderId;
        this.date = date;
        this.customerID = customerID;
    }

    public Orders() {
    }
}
