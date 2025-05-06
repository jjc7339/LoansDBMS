package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.StudentLoan;

public class StudentLoanDAO {
    private Connection conn;

    public StudentLoanDAO(Connection conn) {
        this.conn = conn;
    }
    public StudentLoan getStudentLoanById(int loanId) throws SQLException {
        String sql = "SELECT * FROM student WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new StudentLoan(
                    loanId,
                    rs.getString("term"),
                    rs.getString("disbursement"),
                    rs.getDouble("m_payment"),
                    rs.getInt("g_period"),
                    rs.getString("s_loan_type")
                );
            }
        }
        return null;
    }
    public void updateStudentLoan(StudentLoan loan) throws SQLException {
        String sql = """
            UPDATE student SET term = ?, dispersement = ?, m_payment = ?, g_period = ?, s_loan_type = ?
            WHERE loan_id = ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loan.getTerm());
            ps.setString(2, loan.getDisbursement());
            ps.setDouble(3, loan.getMonthlyPayment());
            ps.setInt(4, loan.getGracePeriod());
            ps.setString(5, loan.getLoanType());
            ps.setInt(6, loan.getLoanId());
            ps.executeUpdate();
        }
    }
}
