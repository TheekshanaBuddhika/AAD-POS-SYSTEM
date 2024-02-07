package lk.ijse.gdse66.pos_backend.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebServlet(urlPatterns = "/orders")
public class PurchaseOrderServlet extends HttpServlet {



    private DataSource source;

    @Override
    public void init() throws ServletException {
        try {
            source = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/pos");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
