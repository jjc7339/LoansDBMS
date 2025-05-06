package model;

public class StudentLoan extends Loan {
    private String term;
    private String disbursement;
    private double monthlyPayment;
    private int gracePeriod;
    private String loanType;

    // constructor, getters, setters
    public StudentLoan(int loanId, String startDate, String endDate, String term, String disbursement,
            double monthlyPayment, int gracePeriod, String loanType) {
        super(loanId, startDate, endDate);
        this.term = term;
        this.disbursement = disbursement;
        this.monthlyPayment = monthlyPayment;
        this.gracePeriod = gracePeriod;
        this.loanType = loanType;
    }
    public StudentLoan(int loanId, String term, String disbursement,
    double monthlyPayment, int gracePeriod, String loanType) {
        //TODO Auto-generated constructor stub
        super(loanId, null, null);
        this.term = term;
        this.disbursement = disbursement;
        this.monthlyPayment = monthlyPayment;
        this.gracePeriod = gracePeriod;
        this.loanType = loanType;
    }
    public String getTerm() {
        return term;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public String getDisbursement() {
        return disbursement;
    }
    public void setDisbursement(String disbursement) {
        this.disbursement = disbursement;
    }
    public double getMonthlyPayment() {
        return monthlyPayment;
    }
    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }
    public int getGracePeriod() {
        return gracePeriod;
    }
    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }
    public String getLoanType() {
        return loanType;
    }
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
    @Override
    public String toString() {
        return "StudentLoan [term=" + term + ", disbursement=" + disbursement + ", monthlyPayment=" + monthlyPayment
                + ", gracePeriod=" + gracePeriod + ", loanType=" + loanType + "]";
    }
}
