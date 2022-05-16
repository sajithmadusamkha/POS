package dao.custom;

import model.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO {
    ArrayList<CustomDTO> searchByOrderId(String id) throws ClassNotFoundException, SQLException;
}
