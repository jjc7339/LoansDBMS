package model;

public class Loan {
    private int loanId;
    private String startDate;
    private String endDate;

    public Loan() {}

    public Loan(int loanId, String startDate, String endDate) {
        this.loanId = loanId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getLoanId() {
        return this.loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Loan [loanId=" + loanId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}

