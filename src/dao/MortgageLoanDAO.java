package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.MortgageLoan;

public class MortgageLoanDAO {
    private Connection conn;

    public MortgageLoanDAO(Connection conn) {
        this.conn = conn;
    }

    public MortgageLoan getMortgageLoanById(int loanId) throws SQLException {
    String sql = "SELECT * FROM mortgage WHERE loan_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, loanId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new MortgageLoan(
                loanId,
                rs.getString("h_number"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getInt("zip"),
                rs.getString("area"),
                rs.getInt("h_bedrooms"),
                rs.getDouble("h_price"),
                rs.getDouble("amount"),
                rs.getDouble("interest_rate"),
                rs.getDouble("total_paid"),
                rs.getInt("num_payments")
            );
        }
    }
    return null;
}

    public void updateMortgageLoan(MortgageLoan loan) throws SQLException {
        String sql = """
            UPDATE mortgage SET h_number = ?, street = ?, city = ?, state = ?, zip = ?, area = ?,
            h_bedrooms = ?, h_price = ?, amount = ?, interest_rate = ?, total_paid = ?, num_payments = ?
            WHERE loan_id = ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loan.getHouseNumber());
            ps.setString(2, loan.getStreet());
            ps.setString(3, loan.getCity());
            ps.setString(4, loan.getState());
            ps.setInt(5, loan.getZip());
            ps.setString(6, loan.getArea());
            ps.setInt(7, loan.getBedrooms());
            ps.setDouble(8, loan.getPrice());
            ps.setDouble(9, loan.getAmount());
            ps.setDouble(10, loan.getInterestRate());
            ps.setDouble(11, loan.getTotalPaid());
            ps.setInt(12, loan.getNumPayments());
            ps.setInt(13, loan.getLoanId());
            ps.executeUpdate();
        }
    }

}
