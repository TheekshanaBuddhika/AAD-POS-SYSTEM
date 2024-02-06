package lk.ijse.gdse66.pos_backend.bo.custom.impl;

import lk.ijse.gdse66.pos_backend.bo.custom.CustomerBO;
import lk.ijse.gdse66.pos_backend.dao.DAOFactory;
import lk.ijse.gdse66.pos_backend.dao.custom.CustomerDAO;
import lk.ijse.gdse66.pos_backend.dto.CustomerDTO;
import lk.ijse.gdse66.pos_backend.entity.Customer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO =  DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> arrayList= new ArrayList<>();
        for (Customer c : all) {
            arrayList.add(new CustomerDTO(c.getId(),c.getName(),c.getAddress(),c.getSalary()));
        }
        return arrayList;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save( new Customer(dto.getId(), dto.getName(),dto.getAddress() , dto.getSalary()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update( new Customer(dto.getId(), dto.getName(),dto.getAddress() , dto.getSalary()));
    }
}
