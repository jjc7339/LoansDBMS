package model;

public class PersonalLoan extends Loan {
    private String purpose;
    private double amount;
    private double interestRate;
    private double totalPaid;
    private int numPayments;

    // constructor, getters, setters
    public PersonalLoan(int loanId, String startDate, String endDate, String purpose, double amount, double interestRate, double totalPaid, int numPayments) {
        super(loanId, startDate, endDate);
        this.purpose = purpose;
        this.amount = amount;
        this.interestRate = interestRate;
        this.totalPaid = totalPaid;
        this.numPayments = numPayments;
    }
    public PersonalLoan(int loanId, String purpose, double amount, double interestRate, double totalPaid, int numPayments) {
        super(loanId, null, null);
        this.purpose = purpose;
        this.amount = amount;
        this.interestRate = interestRate;
        this.totalPaid = totalPaid;
        this.numPayments = numPayments;
    }
    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    public double getTotalPaid() {
        return totalPaid;
    }
    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }
    public int getNumPayments() {
        return numPayments;
    }
    public void setNumPayments(int numPayments) {
        this.numPayments = numPayments;
    }
    @Override
    public String toString() {
        return "PersonalLoan [purpose=" + purpose + ", amount=" + amount + ", interestRate=" + interestRate
                + ", totalPaid=" + totalPaid + ", numPayments=" + numPayments + "]";
    }
}
