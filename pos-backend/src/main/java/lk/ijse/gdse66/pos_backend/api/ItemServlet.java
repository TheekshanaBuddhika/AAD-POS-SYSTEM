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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
        String code = itemDTO.getCode();
        String description = itemDTO.getDescription();
        double unitPrice = itemDTO.getUnitPrice();
        int qty = itemDTO.getQtyOnHand();


        if(code==null || !code.matches("I\\d{3}")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item Code is empty or invalid");
            return;
        } else if (description == null || !description.matches("[A-Za-z ]+")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Description is empty or invalid");
            return;
        } else if (unitPrice < 0.0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unit Price is empty or invalid");
            return;
        }else if (qty < 0 ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantity is empty or invalid");
            return;
        }

        try {

            boolean saveItem = itemBO.saveItem(new ItemDTO(code,description,unitPrice,qty));
            if (saveItem) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Added item successfully");
            }else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save the item");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
        String code = itemDTO.getCode();
        String description = itemDTO.getDescription();
        double unitPrice = itemDTO.getUnitPrice();
        int qty = itemDTO.getQtyOnHand();

        try {

            boolean updateItem = itemBO.updateItem(new ItemDTO(code,description,unitPrice,qty));
            if (updateItem) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Updated item successfully");
            }else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update the item");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
