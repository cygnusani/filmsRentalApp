package main;

import main.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class Customers {

    private static int customerId;
    private List<Customer> customers;

    public Customers() {
        customerId = 1;
        customers = new ArrayList<>();
    }

    public void add(Customer newCustomer) {
        newCustomer.setId(this.customerId++);
        customers.add(newCustomer);
    }

    public Customer getById(int id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst().get();
    }

    public List<Customer> getAll() {
        return this.customers;
    }

    public void update(Customer customer) {
        Customer result = customers.stream().filter(c -> c.getId() == customer.getId()).findFirst().get();
        result = customer;
    }

    public void remove(int customerId) {
        customers.removeIf(c -> c.getId() == customerId);
    }

    // Helper for tests
    public void resetIdCounter() {
        customerId = 1;
    }
}
