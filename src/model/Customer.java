package model;

import java.util.List;

public class Customer {
    private int custId;
    private String custName;
    List<Loan> loans;

    public Customer(int custId, String custName) {
        this.custId = custId;
        this.custName = custName;
    }
    public int getCustId() {
        return this.custId;
    }
    public void setCustId(int custId) {
        this.custId = custId;
    }
    public String getCustName() {
        return this.custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    @Override
    public String toString() {
        return "Customer [custId=" + custId + ", custName=" + custName + "]";
    }

}
