package lk.ijse.gdse66.pos_backend.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse66.pos_backend.bo.BoFactory;
import lk.ijse.gdse66.pos_backend.bo.custom.CustomerBO;
import lk.ijse.gdse66.pos_backend.dto.CustomerDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {

    CustomerBO customerBO= BoFactory.getBoFactory().getBO(BoFactory.BOTypes.CUSTOMER_BO);

    private DataSource source;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ic = new InitialContext();
            source = (DataSource) ic.lookup("java:/comp/env/jdbc/pos");

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = source.getConnection();
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);

            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(allCustomers,resp.getWriter());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }



    }
}
