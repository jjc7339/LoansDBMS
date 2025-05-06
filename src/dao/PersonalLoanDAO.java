package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.PersonalLoan;

public class PersonalLoanDAO {
    private Connection conn;

    public PersonalLoanDAO(Connection conn) {
        this.conn = conn;
    }
    public PersonalLoan getPersonalLoanById(int loanId) throws SQLException {
    String sql = "SELECT * FROM personal WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PersonalLoan(
                    loanId,
                    rs.getString("purpose"),
                    rs.getDouble("amount"),
                    rs.getDouble("interest_rate"),
                    rs.getDouble("total_paid"),
                    rs.getInt("num_payments")
                );
            }
        }
        return null;
    }

    public void updatePersonalLoan(PersonalLoan loan) throws SQLException {
        String sql = """
            UPDATE personal SET purpose = ?, amount = ?, interest_rate = ?, total_paid = ?, num_payments = ?
            WHERE loan_id = ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loan.getPurpose());
            ps.setDouble(2, loan.getAmount());
            ps.setDouble(3, loan.getInterestRate());
            ps.setDouble(4, loan.getTotalPaid());
            ps.setInt(5, loan.getNumPayments());
            ps.setInt(6, loan.getLoanId());
            ps.executeUpdate();
        }
    }
}
