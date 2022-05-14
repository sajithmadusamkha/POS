package dao.custom.impl;

import dao.custom.QueryDAO;

import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public void searchByOrderId(String id) throws ClassNotFoundException, SQLException {
        String sql = "select Orders.oid,Orders.date,Orders.customerID,OrderDetails.itemCode,OrderDetails.qty,OrderDetails.unitPrice from Orders inner join OrderDetails on Orders.oid=OrderDetails.oid where Orders.oid=?;";
    }
}