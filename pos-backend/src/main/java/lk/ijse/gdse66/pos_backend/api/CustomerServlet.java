package lk.ijse.gdse66.pos_backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletContext;
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
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "Customer", urlPatterns = "/customer", loadOnStartup = 0)
public class CustomerServlet extends HttpServlet {

    CustomerBO customerBO= BoFactory.getBoFactory().getBO(BoFactory.BOTypes.CUSTOMER_BO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(allCustomers,resp.getWriter());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}
