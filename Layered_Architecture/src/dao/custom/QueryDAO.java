package dao.custom;

import java.sql.SQLException;

public interface QueryDAO {
    void searchByOrderId(String id) throws ClassNotFoundException, SQLException;
}
