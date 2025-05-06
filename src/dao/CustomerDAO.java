package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Loan;

public class CustomerDAO {
    private Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    public void addCustomer(Customer c) throws SQLException {
        String sql = "INSERT INTO customer VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCustName());
            ps.setInt(2, c.getCustId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // rethrow the exception for handling in the calling method
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer c = new Customer(0, null);
                c.setCustId(rs.getInt("cust_id"));
                c.setCustName(rs.getString("name"));
                list.add(c);
            }
        }
        return list;
    }

    // add updateCustomer, deleteCustomer, searchCustomerByName, etc.
    public void updateCustomer(Customer c) throws SQLException {
        String sql = "UPDATE customer SET name = ? WHERE cust_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCustName());
            ps.setInt(2, c.getCustId());
            ps.executeUpdate();
        }
    }

    public void deleteCustomer(int custId) throws SQLException {
        String sql = "DELETE FROM customer WHERE cust_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, custId);
            ps.executeUpdate();
        }
    }
    public Customer getCustomerById(int custId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE cust_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, custId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer(
                    custId,
                    rs.getString("name")
                );
                return c;
            }
        }
        return null; // or throw an exception if not found
    }

    public List<Customer> searchCustomerByName(String name) throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(0, null);
                c.setCustId(rs.getInt("cust_id"));
                c.setCustName(rs.getString("name"));
                list.add(c);
            }
        }
        return list;
    }

    // Relationship methods
    public void addLoanToCustomer(int custId, int loanId) throws SQLException {
        String sql = "INSERT INTO cust_loans (cust_id, loan_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, custId);
            ps.setInt(2, loanId);
            ps.executeUpdate();
        }
    }
    public void removeLoanFromCustomer(int custId, int loanId) throws SQLException {
        String sql = "DELETE FROM cust_loans WHERE customer_id = ? AND loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, custId);
            ps.setInt(2, loanId);
            ps.executeUpdate();
        }
    }
    public List<Loan> getLoansByCustomerId(int custId) throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT l.* FROM loan l JOIN cust_loans cl ON l.id = cl.loan_id WHERE cl.cust_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, custId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Loan loan = new Loan(rs.getInt("id"), rs.getString("start_date"), rs.getString("end_date"));
                loans.add(loan);
            }
        }
        return loans;
    }

    public boolean customerExists(int custId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer WHERE cust_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, custId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
}

