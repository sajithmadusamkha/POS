package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DBConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Customer;
import entity.Item;
import entity.OrderDetails;
import entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private OrderDetailDAO orderDetailsDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        /*Transaction*/
            Connection connection = DBConnection.getDbConnection().getConnection();
            /*if order id already exist*/
            if (orderDAO.exist(dto.getOrderId())) {

            }

            connection.setAutoCommit(false);

            boolean save = orderDAO.save(new Orders(dto.getOrderId(), dto.getOrderDate(), dto.getCustomerId()));

            if (!save) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO detailDTO : dto.getOrderDetails()) {
                boolean save1 = orderDetailsDAO.save(new OrderDetails(detailDTO.getoId(),detailDTO.getItemCode(),detailDTO.getQty(),detailDTO.getUnitPrice()));
                if (!save1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

                //Search & Update Item
                ItemDTO item = searchItem(detailDTO.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detailDTO.getQty());

                //Update Item
                boolean update = itemDAO.update(new Item(item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice()));

                if (!update) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer ent = customerDAO.search(id);
        return new CustomerDTO(ent.getId(),ent.getName(),ent.getAddress());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item ent = itemDAO.search(code);
        return new ItemDTO(ent.getCode(),ent.getDescription(),ent.getUnitPrice(),ent.getQtyOnHand());
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for(Customer ent : all){
            allCustomers.add(new CustomerDTO(ent.getId(),ent.getName(),ent.getAddress()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for(Item ent : all){
            allItems.add(new ItemDTO(ent.getCode(),ent.getDescription(),ent.getUnitPrice(),ent.getQtyOnHand()));
        }
        return allItems;
    }
}
