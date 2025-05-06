package dao;

import java.sql.*;
import java.util.*;

import model.AutoLoan;
import model.Loan;
import model.MortgageLoan;
import model.PersonalLoan;
import model.StudentLoan;

public class LoanDAO {
    private Connection conn;

    public LoanDAO(Connection conn) {
        this.conn = conn;
    }

    // --- Basic Loan CRUD ---

    public void addLoan(Loan loan) throws SQLException {
        String sql = "INSERT INTO loan (loan_id, start_date, end_date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loan.getLoanId());
            ps.setString(2, loan.getStartDate());
            ps.setString(3, loan.getEndDate());
            ps.executeUpdate();
        }
    }

    public Loan getLoanById(int loanId) throws SQLException {
        String sql = "SELECT * FROM loan WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Loan(
                    rs.getInt("loan_id"),
                    rs.getString("start_date"),
                    rs.getString("end_date")
                );
            }
        }
        return null;
    }

    public void updateLoan(Loan loan) throws SQLException {
        String sql = "UPDATE loan SET start_date = ?, end_date = ? WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loan.getStartDate());
            ps.setString(2, loan.getEndDate());
            ps.setInt(3, loan.getLoanId());
            ps.executeUpdate();
        }
    }

    public void deleteLoan(int loanId) throws SQLException {
        String sql = "DELETE FROM loan WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ps.executeUpdate();
        }
    }

    public List<Loan> getAllLoans() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loan ORDER BY loan_id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                loans.add(new Loan(
                    rs.getInt("loan_id"),
                    rs.getString("start_date"),
                    rs.getString("end_date")
                ));
            }
        }
        return loans;
    }

    public List<Loan> getLoansByType(String type) throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = """
            SELECT l.loan_id, l.start_date, l.end_date
            FROM loan l
            JOIN loan_types lt ON l.loan_id = lt.loan_id
            WHERE LOWER(lt.type) = ?
        """;
    
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type.toLowerCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Loan loan = new Loan(
                    rs.getInt("loan_id"),
                    rs.getString("start_date"),
                    rs.getString("end_date")
                );
                loans.add(loan);
            }
        }
    
        return loans;
    }

    public String getLoanType(int loanId) throws SQLException {
        String sql = "SELECT type FROM loan_types WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("type");
            }
        }
        return null;
    }

    // You can expand with methods like getLoansByCustomerId(int custId) or filter by loan type

    // --- Loan Subtypes ---

    public void addMortgageLoan(MortgageLoan loan) throws SQLException {
        String sql = "INSERT INTO mortgage (loan_id, h_number, street, city, state, zip, area, h_bedrooms, h_price, amount, interest_rate, total_paid, num_payments) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loan.getLoanId());
            ps.setString(2, loan.getHouseNumber());
            ps.setString(3, loan.getStreet());
            ps.setString(4, loan.getCity());
            ps.setString(5, loan.getState());
            ps.setInt(6, loan.getZip());
            ps.setString(7, loan.getArea());
            ps.setInt(8, loan.getBedrooms());
            ps.setDouble(9, loan.getPrice());
            ps.setDouble(10, loan.getAmount());
            ps.setDouble(11, loan.getInterestRate());
            ps.setDouble(12, loan.getTotalPaid());
            ps.setInt(13, loan.getNumPayments());
            ps.executeUpdate();
        }
    }

    public void addAutoLoan(AutoLoan loan) throws SQLException {
        String sql = "INSERT INTO auto (loan_id, VIN, make, model, year, amount, interest_rate, total_paid, num_payments) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loan.getLoanId());
            ps.setString(2, loan.getVin());
            ps.setString(3, loan.getMake());
            ps.setString(4, loan.getModel());
            ps.setInt(5, loan.getYear());
            ps.setDouble(6, loan.getAmount());
            ps.setDouble(7, loan.getInterestRate());
            ps.setDouble(8, loan.getTotalPaid());
            ps.setInt(9, loan.getNumPayments());
            ps.executeUpdate();
        }
    }

    public void addPersonalLoan(PersonalLoan loan) throws SQLException {
        String sql = "INSERT INTO personal (loan_id, purpose, amount, interest_rate, total_paid, num_payments) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loan.getLoanId());
            ps.setString(2, loan.getPurpose());
            ps.setDouble(3, loan.getAmount());
            ps.setDouble(4, loan.getInterestRate());
            ps.setDouble(5, loan.getTotalPaid());
            ps.setInt(6, loan.getNumPayments());
            ps.executeUpdate();
        }
    }

    public void addStudentLoan(StudentLoan loan) throws SQLException {
        String sql = "INSERT INTO student (loan_id, term, dispersement, m_payment, g_period, s_loan_type) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loan.getLoanId());
            ps.setString(2, loan.getTerm());
            ps.setString(3, loan.getDisbursement());
            ps.setDouble(4, loan.getMonthlyPayment());
            ps.setInt(5, loan.getGracePeriod());
            ps.setString(6, loan.getLoanType());
            ps.executeUpdate();
        }
    }

    // --- Subtype CRUD (get/update/delete) ---

    public MortgageLoan getMortgageLoanById(int loanId) throws SQLException {
        String sql = "SELECT * FROM mortgage WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MortgageLoan loan = new MortgageLoan(loanId, sql, sql, sql, sql, sql, sql, loanId, sql, loanId, loanId, loanId, loanId, loanId, loanId);
                loan.setLoanId(loanId);
                loan.setHouseNumber(rs.getString("h_number"));
                loan.setStreet(rs.getString("street"));
                loan.setCity(rs.getString("city"));
                loan.setState(rs.getString("state"));
                loan.setZip(rs.getInt("zip"));
                loan.setArea(rs.getString("area"));
                loan.setBedrooms(rs.getInt("h_bedrooms"));
                loan.setPrice(rs.getDouble("h_price"));
                loan.setAmount(rs.getDouble("amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setTotalPaid(rs.getDouble("total_paid"));
                loan.setNumPayments(rs.getInt("num_payments"));
                return loan;
            }
        }
        return null;
    }

    public void updateMortgageLoan(MortgageLoan loan) throws SQLException {
        String sql = "UPDATE mortgage SET h_number = ?, street = ?, city = ?, state = ?, zip = ?, area = ?, h_bedrooms = ?, h_price = ?, amount = ?, interest_rate = ?, total_paid = ?, num_payments = ? WHERE loan_id = ?";
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

    public void deleteMortgageLoan(int loanId) throws SQLException {
        String sql = "DELETE FROM mortgage WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ps.executeUpdate();
        }
    }

   // Similar get/update/delete methods can be written for AutoLoan, PersonalLoan, and StudentLoan
   public AutoLoan getAutoLoanById(int loanId) throws SQLException {
        String sql = "SELECT * FROM auto WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AutoLoan loan = new AutoLoan(loanId, sql, sql, sql, sql, sql, loanId, loanId, loanId, loanId, loanId);
                loan.setLoanId(loanId);
                loan.setVin(rs.getString("VIN"));
                loan.setMake(rs.getString("make"));
                loan.setModel(rs.getString("model"));
                loan.setYear(rs.getInt("year"));
                loan.setAmount(rs.getDouble("amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setTotalPaid(rs.getDouble("total_paid"));
                loan.setNumPayments(rs.getInt("num_payments"));
                return loan;
            }
        }
        return null;
    }

    public void updateAutoLoan(AutoLoan loan) throws SQLException {
        String sql = "UPDATE auto SET VIN = ?, make = ?, model = ?, year = ?, amount = ?, interest_rate = ?, total_paid = ?, num_payments = ? WHERE loan_id = ?";
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

    public void deleteAutoLoan(int loanId) throws SQLException {
        String sql = "DELETE FROM auto WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ps.executeUpdate();
        }
    }

    // Similar methods for PersonalLoan and StudentLoan can be added here
    public PersonalLoan getPersonalLoanById(int loanId) throws SQLException {
        String sql = "SELECT * FROM personal WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PersonalLoan loan = new PersonalLoan(loanId, sql, sql, sql, loanId, loanId, loanId, loanId);
                loan.setLoanId(loanId);
                loan.setPurpose(rs.getString("purpose"));
                loan.setAmount(rs.getDouble("amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setTotalPaid(rs.getDouble("total_paid"));
                loan.setNumPayments(rs.getInt("num_payments"));
                return loan;
            }
        }
        return null;
    }
    public void updatePersonalLoan(PersonalLoan loan) throws SQLException {
        String sql = "UPDATE personal SET purpose = ?, amount = ?, interest_rate = ?, total_paid = ?, num_payments = ? WHERE loan_id = ?";
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
    public void deletePersonalLoan(int loanId) throws SQLException {
        String sql = "DELETE FROM personal WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ps.executeUpdate();
        }
    }
    public StudentLoan getStudentLoanById(int loanId) throws SQLException {
        String sql = "SELECT * FROM student WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Create a StudentLoan object with retrieved data
                StudentLoan loan = new StudentLoan(
                    loanId,
                    rs.getString("term"), 
                    rs.getString("disbursement"), 
                    rs.getDouble("m_payment"), 
                    rs.getInt("g_period"), 
                    rs.getString("s_loan_type")
                );
                return loan;
            }
        }
        return null;
    }
    public void updateStudentLoan(StudentLoan loan) throws SQLException {
        String sql = "UPDATE student SET term = ?, dispersement = ?, m_payment = ?, g_period = ?, s_loan_type = ? WHERE loan_id = ?";
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
    public void deleteStudentLoan(int loanId) throws SQLException {
        String sql = "DELETE FROM student WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ps.executeUpdate();
        }
    }

    public void addLoanType(int loanId, String type) throws SQLException {
        String sql = "INSERT INTO loan_types (loan_id, type) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ps.setString(2, type);
            ps.executeUpdate();
        }
    }

    public int getNextLoanId() throws SQLException {
        String sql = "SELECT MAX(loan_id) AS max_id FROM loan";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
        }
        return 1; // Start from 1 if no loans exist
    }
}

