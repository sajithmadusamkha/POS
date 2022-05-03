package dao;

import db.DBConnection;
import javafx.scene.control.Alert;
import model.ItemDTO;
import view.tdm.ItemTM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemDAOImpl {
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
            /*Get all items*/
            Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item");
            ArrayList<ItemDTO> itemDTO = new ArrayList<>();
            while (rst.next()) {
                String id = rst.getString(1);
                String description = rst.getString(2);
                BigDecimal unitPrice = rst.getBigDecimal(3);
                Integer qtyOnHand = rst.getInt(4);
                itemDTO.add(new ItemDTO(id,description,unitPrice,qtyOnHand));
            }
            return itemDTO;
    }

    public void deleteItem(){}
}
