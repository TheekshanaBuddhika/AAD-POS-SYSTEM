package lk.ijse.gdse66.pos_backend.bo.custom.impl;

import lk.ijse.gdse66.pos_backend.bo.custom.CustomerBO;
import lk.ijse.gdse66.pos_backend.dto.CustomerDTO;
import lk.ijse.gdse66.pos_backend.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> arrayList= new ArrayList<>();
        for (Customer c : all) {
            arrayList.add(new CustomerDTO(c.getId(),c.getName(),c.getAddress(),c.getSalary()));
        }
        return arrayList;
    }
}
