package lk.ijse.gdse66.pos_backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse66.pos_backend.bo.BoFactory;
import lk.ijse.gdse66.pos_backend.bo.custom.ItemBO;
import lk.ijse.gdse66.pos_backend.dto.CustomerDTO;
import lk.ijse.gdse66.pos_backend.dto.ItemDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "Item", urlPatterns = "/items", loadOnStartup = 0)
public class ItemServlet extends HttpServlet {

    ItemBO itemBO=  BoFactory.getBoFactory().getBO(BoFactory.BOTypes.ITEM_BO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<ItemDTO> allitems = itemBO.getAllItems();
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(allitems,resp.getWriter());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
