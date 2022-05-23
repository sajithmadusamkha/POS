package dao.custom;

import dao.CrudDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer, String> {
    public ArrayList<Customer> getAllCustomersByAddress(String address) throws ClassNotFoundException, SQLException;
}
