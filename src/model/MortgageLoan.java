package model;

public class MortgageLoan extends Loan {
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private int zip;
    private String area;
    private int bedrooms;
    private double price;
    private double amount;
    private double interestRate;
    private double totalPaid;
    private int numPayments;

    // Constructor
    public MortgageLoan(int loanId, String startDate, String endDate, String houseNumber, String street, String city,
            String state, int zip, String area, int bedrooms, double price, double amount, double interestRate,
            double totalPaid, int numPayments) {
        super(loanId, startDate, endDate);
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.area = area;
        this.bedrooms = bedrooms;
        this.price = price;
        this.amount = amount;
        this.interestRate = interestRate;
        this.totalPaid = totalPaid;
        this.numPayments = numPayments;
    }
    public MortgageLoan(int loanId, String houseNumber, String street, String city,
            String state, int zip, String area, int bedrooms, double price, double amount, double interestRate,
            double totalPaid, int numPayments) {
        super(loanId, null, null);
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.area = area;
        this.bedrooms = bedrooms;
        this.price = price;
        this.amount = amount;
        this.interestRate = interestRate;
        this.totalPaid = totalPaid;
        this.numPayments = numPayments;
    }
    // Getters and Setters
    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getZip() {
        return zip;
    }
    public void setZip(int zip) {
        this.zip = zip;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public int getBedrooms() {
        return bedrooms;
    }
    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
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
        return "MortgageLoan [houseNumber=" + houseNumber + ", street=" + street + ", city=" + city + ", state=" + state
                + ", zip=" + zip + ", area=" + area + ", bedrooms=" + bedrooms + ", price=" + price + ", amount="
                + amount + ", interestRate=" + interestRate + ", totalPaid=" + totalPaid + ", numPayments="
                + numPayments + "]";
    }
    
}
