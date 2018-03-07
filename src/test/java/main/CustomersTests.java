package main;

import main.model.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomersTests {

    protected Customers customers = new Customers();

    @Test
    public void add() {
        customers.add(new Customer());
        assertEquals(1, customers.getAll().size());
    }

    @Test
    public void getById() {
        customers.add(new Customer());
        assertEquals(1, customers.getById(1).getId());
    }

    @Test
    public void update() {
        customers.add(new Customer());
        Customer customer = customers.getById(1);
        int bonusPoints = customer.getBonusPoints();
        customer.setBonusPoints(bonusPoints += 100);
        assertEquals(100, bonusPoints);
    }
}