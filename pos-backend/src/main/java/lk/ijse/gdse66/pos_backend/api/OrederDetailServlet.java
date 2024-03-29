package lk.ijse.gdse66.pos_backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse66.pos_backend.bo.BoFactory;
import lk.ijse.gdse66.pos_backend.bo.custom.OrderDetailBO;
import lk.ijse.gdse66.pos_backend.dto.OrderDetailDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/detail")
public class OrederDetailServlet extends HttpServlet {

    OrderDetailBO detailBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.Detail_BO);

    private DataSource source;

    @Override
    public void init() throws ServletException {
        try {
            source = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/pos");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = source.getConnection();){
            ArrayList<OrderDetailDTO> alldetails = detailBO.getAllDetails(connection);
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(alldetails,resp.getWriter());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
