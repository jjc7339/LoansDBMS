package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AutoLoan;

public class AutoLoanDAO {
    private Connection conn;

    public AutoLoanDAO(Connection conn) {
        this.conn = conn;
    }

    public AutoLoan getAutoLoanById(int loanId) throws SQLException {
        String sql = "SELECT * FROM auto WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new AutoLoan(
                    loanId,
                    rs.getString("VIN"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getDouble("amount"),
                    rs.getDouble("interest_rate"),
                    rs.getDouble("total_paid"),
                    rs.getInt("num_payments")
                );
            }
        }
        return null;
    }

    public void updateAutoLoan(AutoLoan loan) throws SQLException {
        String sql = """
            UPDATE auto SET VIN = ?, make = ?, model = ?, year = ?, amount = ?,
            interest_rate = ?, total_paid = ?, num_payments = ? WHERE loan_id = ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loan.getVin());
            ps.setString(2, loan.getMake());
            ps.setString(3, loan.getModel());
            ps.setInt(4, loan.getYear());
            ps.setDouble(5, loan.getAmount());
            ps.setDouble(6, loan.getInterestRate());
            ps.setDouble(7, loan.getTotalPaid());
            ps.setInt(8, loan.getNumPayments());
            ps.setInt(9, loan.getLoanId());
            ps.executeUpdate();
        }
    }

}
