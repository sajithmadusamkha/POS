package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.dto.CustomDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomDTO> searchByOrderId(String id) throws ClassNotFoundException, SQLException {
        ResultSet resultSet = SQLUtil.executeQuery("select Orders.oid,Orders.date,Orders.customerID,OrderDetails.itemCode,OrderDetails.qty,OrderDetails.unitPrice from Orders inner join OrderDetails on Orders.oid=OrderDetails.oid where Orders.oid=?;",id);
        ArrayList<CustomDTO> orderRecords = new ArrayList<>();
        while (resultSet.next()) {
            orderRecords.add(new CustomDTO(resultSet.getString(1), LocalDate.parse(resultSet.getString(2)), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getBigDecimal(6)));
        }
        return orderRecords;
    }
}
