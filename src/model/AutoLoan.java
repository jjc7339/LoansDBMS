package model;

public class AutoLoan extends Loan {
    private String vin;
    private String make;
    private String model;
    private int year;
    private double amount;
    private double interestRate;
    private double totalPaid;
    private int numPayments;

    // constructor, getters, setters
    public AutoLoan(int loanId, String startDate, String endDate, String vin, String make, String model, int year,
            double amount, double interestRate, double totalPaid, int numPayments) {
        super(loanId, startDate, endDate);
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.amount = amount;
        this.interestRate = interestRate;
        this.totalPaid = totalPaid;
        this.numPayments = numPayments;
    }

    public AutoLoan(int loanId, String vin, String make, String model, int year,
            double amount, double interestRate, double totalPaid, int numPayments) {
        super(loanId, null, null);
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.amount = amount;
        this.interestRate = interestRate;
        this.totalPaid = totalPaid;
        this.numPayments = numPayments;
    }
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
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
        return "AutoLoan [vin=" + vin + ", make=" + make + ", model=" + model + ", year=" + year + ", amount=" + amount
                + ", interestRate=" + interestRate + ", totalPaid=" + totalPaid + ", numPayments=" + numPayments
                + "]";
    }
}
