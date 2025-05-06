// Project: Loan Management System
// Jimmy Chen, Matthew Pragel, Valen Usher
// Make sure to enter your Oracle credentials in db.properties
// before running this program

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import dao.AutoLoanDAO;
import dao.CustomerDAO;
import dao.LoanDAO;
import dao.MortgageLoanDAO;
import dao.PersonalLoanDAO;
import dao.StudentLoanDAO;
import model.AutoLoan;
import model.Customer;
import model.Loan;
import model.MortgageLoan;
import model.PersonalLoan;
import model.StudentLoan;

public class DBUtil {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Loan Management System =====");
            System.out.println("1. Add Customer");
            System.out.println("2. Edit/Update Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. Search Customer by Name");
            System.out.println("5. View All Customers");
            System.out.println("6. Add Loan");
            System.out.println("7. Edit/Update Loan");
            System.out.println("8. Delete Loan");
            System.out.println("9. Search Loan by Type");
            System.out.println("10. View All Loans");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
        
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
        
            try (Connection conn = getConnection()) {
                CustomerDAO customerDAO = new CustomerDAO(conn);
                LoanDAO loanDAO = new LoanDAO(conn);
                AutoLoanDAO autoDAO = new AutoLoanDAO(conn);
                MortgageLoanDAO mortgageDAO = new MortgageLoanDAO(conn);
                PersonalLoanDAO personalDAO = new PersonalLoanDAO(conn);
                StudentLoanDAO studentDAO = new StudentLoanDAO(conn);
                // Handle customer and loan operations
                switch (choice) {
                    case 1: {
                        int custId = -1;
                        while (true) {
                            System.out.print("Enter customer ID (numeric): ");
                            String input = scanner.nextLine();
                            try {
                                custId = Integer.parseInt(input);
                                if (custId <= 0) {
                                    System.out.println("Customer ID must be a positive number.");
                                    continue;
                                }
                                if (customerDAO.customerExists(custId)) {
                                    System.out.println("Customer ID already exists. Choose a different one.");
                                    continue;
                                }
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a numeric value.");
                            }
                        }

                        String name = "";
                        while (true) {
                            System.out.print("Enter customer name: ");
                            name = scanner.nextLine().trim();
                            if (name.isEmpty()) {
                                System.out.println("Name cannot be empty.");
                            } else if (name.length() > 50) {
                                System.out.println("Name is too long (max 50 characters).");
                            } else {
                                break;
                            }
                        }

                        Customer customer = new Customer(custId, name);
                        try {
                            customerDAO.addCustomer(customer);
                        } catch (SQLException e) {
                            System.out.println("Error adding customer: " + e.getMessage());
                        }
                        System.out.println("Customer added successfully.");
                        break;
                    }
                    case 2: {
                        System.out.print("Enter customer ID to update: ");
                        int custId = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        Customer customer = customerDAO.getCustomerById(custId);
                        if (customer == null) {
                            System.out.println("Customer not found.");
                            break;
                        }
                        System.out.print("Enter new name (current: " + customer.getCustName() + "): ");
                        String newName = scanner.nextLine();
                        customer.setCustName(newName);
                        customerDAO.updateCustomer(customer);
                        System.out.println("Customer updated successfully.");
                        break;
                    }
                    case 3: {
                        System.out.print("Enter customer ID to delete: ");
                        int custId = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        customerDAO.deleteCustomer(custId);
                        System.out.println("Customer deleted successfully.");
                        break;
                    }
                    case 4: {
                        System.out.print("Enter customer name to search: ");
                        String name = scanner.nextLine();
                        List<Customer> customers = customerDAO.searchCustomerByName(name);
                        if (customers.isEmpty()) {
                            System.out.println("No customers found with that name.");
                        } else {
                            for (Customer c : customers) System.out.println(c);
                        }
                        break;
                    }
                    case 5:
                        System.out.println("\nAll Customers:");
                        List<Customer> customers = customerDAO.getAllCustomers();
                        for (Customer c : customers) System.out.println(c);
                        break;
                    case 6: {
                        System.out.println("Select loan type:");
                        System.out.println("1. Mortgage");
                        System.out.println("2. Auto");
                        System.out.println("3. Personal");
                        System.out.println("4. Student");
                        System.out.print("Choice: ");
                        int typeChoice = scanner.nextInt();
                        scanner.nextLine();

                        String loanType = switch (typeChoice) {
                            case 1 -> "Mortgage";
                            case 2 -> "Auto";
                            case 3 -> "Personal";
                            case 4 -> "Student";
                            default -> {
                                System.out.println("Invalid type.");
                                yield null;
                            }
                        };

                        if (loanType == null) break;

                        int loanId = loanDAO.getNextLoanId();
                        System.out.println("Generated Loan ID: " + loanId);

                        System.out.print("Start date (YYYY-MM-DD): ");
                        String startDate = scanner.nextLine();
                        System.out.print("End date (YYYY-MM-DD): ");
                        String endDate = scanner.nextLine();

                        loanDAO.addLoan(new Loan(loanId, startDate, endDate));
                        loanDAO.addLoanType(loanId, loanType);

                        switch (loanType) {
                            case "Mortgage" -> {
                                System.out.print("House number: ");
                                String hNum = scanner.nextLine();
                                System.out.print("Street: ");
                                String street = scanner.nextLine();
                                System.out.print("City: ");
                                String city = scanner.nextLine();
                                System.out.print("State: ");
                                String state = scanner.nextLine();
                                System.out.print("ZIP: ");
                                int zip = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Area: ");
                                String area = scanner.nextLine();
                                System.out.print("Bedrooms: ");
                                int bedrooms = scanner.nextInt();
                                System.out.print("House price: ");
                                double hPrice = scanner.nextDouble();
                                System.out.print("Loan amount: ");
                                double amount = scanner.nextDouble();
                                System.out.print("Interest rate: ");
                                double rate = scanner.nextDouble();
                                System.out.print("Total paid: ");
                                double totalPaid = scanner.nextDouble();
                                System.out.print("Number of payments: ");
                                int payments = scanner.nextInt();

                                MortgageLoan mortgage = new MortgageLoan(loanId, startDate, endDate, hNum, street, city, state, zip, area, bedrooms, hPrice, amount, rate, totalPaid, payments);
                                new LoanDAO(conn).addMortgageLoan(mortgage);
                            }
                            case "Auto" -> {
                                System.out.print("VIN: ");
                                String vin = scanner.nextLine();
                                System.out.print("Make: ");
                                String make = scanner.nextLine();
                                System.out.print("Model: ");
                                String model = scanner.nextLine();
                                System.out.print("Year: ");
                                int year = scanner.nextInt();
                                System.out.print("Amount: ");
                                double amount = scanner.nextDouble();
                                System.out.print("Interest rate: ");
                                double rate = scanner.nextDouble();
                                System.out.print("Total paid: ");
                                double totalPaid = scanner.nextDouble();
                                System.out.print("Number of payments: ");
                                int payments = scanner.nextInt();
                            
                                AutoLoan auto = new AutoLoan(loanId, startDate, endDate, vin, make, model, year, amount, rate, totalPaid, payments);
                                new LoanDAO(conn).addAutoLoan(auto);
                            }
                            case "Personal" -> {
                                System.out.print("Purpose: ");
                                String purpose = scanner.nextLine();
                                System.out.print("Amount: ");
                                double amount = scanner.nextDouble();
                                System.out.print("Interest rate: ");
                                double rate = scanner.nextDouble();
                                System.out.print("Total paid: ");
                                double totalPaid = scanner.nextDouble();
                                System.out.print("Number of payments: ");
                                int payments = scanner.nextInt();
                            
                                PersonalLoan personal = new PersonalLoan(loanId, startDate, endDate, purpose, amount, rate, totalPaid, payments);
                                new LoanDAO(conn).addPersonalLoan(personal);
                            }
                            case "Student" -> {
                                System.out.print("Term: ");
                                String term = scanner.nextLine();
                                System.out.print("Disbursement date (YYYY-MM-DD): ");
                                String disb = scanner.nextLine();
                                System.out.print("Monthly payment: ");
                                double mpay = scanner.nextDouble();
                                System.out.print("Grace period (months): ");
                                int gPeriod = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Student loan type: ");
                                String sLoanType = scanner.nextLine();

                                StudentLoan student = new StudentLoan(loanId, startDate, endDate, term, disb, mpay, gPeriod, sLoanType);
                                new LoanDAO(conn).addStudentLoan(student);
                            }
                        }

                        // Prompt to link to customer
                        System.out.print("Do you want to assign this loan to a customer? (y/n): ");
                        String assign = scanner.nextLine();
                        if (assign.equalsIgnoreCase("y")) {
                            System.out.print("Enter customer ID: ");
                            int custId = scanner.nextInt();
                            if (customerDAO.getCustomerById(custId) != null) {
                                customerDAO.addLoanToCustomer(custId, loanId);
                                System.out.println("Loan linked to customer.");
                            } else {
                                System.out.println("Customer not found.");
                            }
                        }

                        System.out.println("Loan added successfully.");
                        break;
                    }
                    case 7: {
                        System.out.print("Enter loan ID to update: ");
                        int loanId = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        Loan loan = loanDAO.getLoanById(loanId);
                        if (loan == null) {
                            System.out.println("Loan not found.");
                            break;
                        }

                        System.out.print("Enter new start date (current: " + loan.getStartDate() + ", press Enter to skip): ");
                        String newStartDate = scanner.nextLine().trim();
                        if (!newStartDate.isEmpty()) {
                            loan.setStartDate(newStartDate);
                        }

                        System.out.print("Enter new end date (current: " + loan.getEndDate() + ", press Enter to skip): ");
                        String newEndDate = scanner.nextLine().trim();
                        if (!newEndDate.isEmpty()) {
                            loan.setEndDate(newEndDate);
                        }

                        loanDAO.updateLoan(loan);

                        // Update subtype-specific fields
                        String type = loanDAO.getLoanType(loanId);
                        switch (type.toLowerCase()) {
                            case "auto":
                                AutoLoan auto = autoDAO.getAutoLoanById(loanId);
                                if (auto != null) updateAutoLoanFields(auto, scanner);
                                autoDAO.updateAutoLoan(auto);
                                break;
                            case "mortgage":
                                MortgageLoan mortgage = mortgageDAO.getMortgageLoanById(loanId);
                                if (mortgage != null) updateMortgageLoanFields(mortgage, scanner);
                                mortgageDAO.updateMortgageLoan(mortgage);
                                break;
                            case "student":
                                StudentLoan student = studentDAO.getStudentLoanById(loanId);
                                if (student != null) updateStudentLoanFields(student, scanner);
                                studentDAO.updateStudentLoan(student);
                                break;
                            case "personal":
                                PersonalLoan personal = personalDAO.getPersonalLoanById(loanId);
                                if (personal != null) updatePersonalLoanFields(personal, scanner);
                                personalDAO.updatePersonalLoan(personal);
                                break;
                            default:
                                System.out.println("Unknown loan subtype.");
                        }

                        System.out.println("Loan updated successfully.");
                        break;
                    }
                    case 8: {
                        System.out.print("Enter loan ID to delete: ");
                        int loanId = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        loanDAO.deleteLoan(loanId);
                        System.out.println("Loan deleted successfully.");
                        break;
                    }
                    case 9: {
                        System.out.print("Enter loan type to search: ");
                        String type = scanner.nextLine();
                        List<Loan> loans = loanDAO.getLoansByType(type);
                        if (loans.isEmpty()) {
                            System.out.println("No loans found with that type.");
                        } else {
                            for (Loan l : loans) System.out.println(l);
                        }
                        break;
                    }
                    case 10:
                        List<Loan> loans = loanDAO.getAllLoans();
                        for (Loan l : loans) System.out.println(l);
                        break;
                    case 11:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws IOException, SQLException {
        String dbUrl = "jdbc:oracle:thin:@h3oracle.ad.psu.edu:1521/orclpdb.ad.psu.edu";
        Properties props = new Properties();
        FileInputStream input = new FileInputStream("db.properties");
        props.load(input);
        input.close();
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        return DriverManager.getConnection(dbUrl, username, password);
    }

    private static void updateAutoLoanFields(AutoLoan loan, Scanner scanner) {
        System.out.print("Enter VIN (current: " + loan.getVin() + ", Enter to skip): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setVin(input);

        System.out.print("Enter make (current: " + loan.getMake() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setMake(input);

        System.out.print("Enter model (current: " + loan.getModel() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setModel(input);

        System.out.print("Enter year (current: " + loan.getYear() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setYear(Integer.parseInt(input));

        System.out.print("Enter amount (current: " + loan.getAmount() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setAmount(Double.parseDouble(input));

        System.out.print("Enter interest rate (current: " + loan.getInterestRate() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setInterestRate(Double.parseDouble(input));

        System.out.print("Enter total paid (current: " + loan.getTotalPaid() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setTotalPaid(Double.parseDouble(input));

        System.out.print("Enter number of payments (current: " + loan.getNumPayments() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setNumPayments(Integer.parseInt(input));
    }

    private static void updateMortgageLoanFields(MortgageLoan loan, Scanner scanner) {
        System.out.print("Enter house number (current: " + loan.getHouseNumber() + ", Enter to skip): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setHouseNumber(input);
    
        System.out.print("Enter street (current: " + loan.getStreet() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setStreet(input);
    
        System.out.print("Enter city (current: " + loan.getCity() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setCity(input);
    
        System.out.print("Enter state (current: " + loan.getState() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setState(input);
    
        System.out.print("Enter ZIP (current: " + loan.getZip() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setZip(Integer.parseInt(input));
    
        System.out.print("Enter area (current: " + loan.getArea() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setArea(input);
    
        System.out.print("Enter number of bedrooms (current: " + loan.getBedrooms() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setBedrooms(Integer.parseInt(input));
    
        System.out.print("Enter house price (current: " + loan.getPrice() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setPrice(Double.parseDouble(input));
    
        System.out.print("Enter amount (current: " + loan.getAmount() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setAmount(Double.parseDouble(input));
    
        System.out.print("Enter interest rate (current: " + loan.getInterestRate() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setInterestRate(Double.parseDouble(input));
    
        System.out.print("Enter total paid (current: " + loan.getTotalPaid() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setTotalPaid(Double.parseDouble(input));
    
        System.out.print("Enter number of payments (current: " + loan.getNumPayments() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setNumPayments(Integer.parseInt(input));
    }
    
    private static void updateStudentLoanFields(StudentLoan loan, Scanner scanner) {
        System.out.print("Enter term (current: " + loan.getTerm() + ", Enter to skip): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setTerm(input);
    
        System.out.print("Enter disbursement date (current: " + loan.getDisbursement() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setDisbursement(input);
    
        System.out.print("Enter monthly payment (current: " + loan.getMonthlyPayment() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setMonthlyPayment(Double.parseDouble(input));
    
        System.out.print("Enter grace period (months) (current: " + loan.getGracePeriod() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setGracePeriod(Integer.parseInt(input));
    
        System.out.print("Enter student loan type (current: " + loan.getLoanType() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setLoanType(input);
    }

    private static void updatePersonalLoanFields(PersonalLoan loan, Scanner scanner) {
        System.out.print("Enter purpose (current: " + loan.getPurpose() + ", Enter to skip): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setPurpose(input);
    
        System.out.print("Enter amount (current: " + loan.getAmount() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setAmount(Double.parseDouble(input));
    
        System.out.print("Enter interest rate (current: " + loan.getInterestRate() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setInterestRate(Double.parseDouble(input));
    
        System.out.print("Enter total paid (current: " + loan.getTotalPaid() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setTotalPaid(Double.parseDouble(input));
    
        System.out.print("Enter number of payments (current: " + loan.getNumPayments() + ", Enter to skip): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) loan.setNumPayments(Integer.parseInt(input));
    }
}