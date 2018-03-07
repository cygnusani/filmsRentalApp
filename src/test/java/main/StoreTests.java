package main;

import main.model.Customer;
import main.model.Film;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StoreTests {

    @Before
    public void setUp() {
        Store.inventory.add(new Film("Title 1", FilmType.New_Release));
        Store.customers.add(new Customer(25));
    }

    @Test
    public void payWithoutPoints() {
        int expectedPrice = 4;
        Store.rentFilms(1, LocalDate.now(), Arrays.asList(1), 1, false);
        assertEquals(expectedPrice, Store.lastPrice);
    }

    @Test
    public void payWithPoints() {
        int expectedPrice = 0;
        Store.rentFilms(1, LocalDate.now(), Arrays.asList(1), 1, true);
        assertEquals(expectedPrice, Store.lastPrice);
    }

    @Test
    public void returnOnTime() {
        int expectedPrice = 0;
        Store.rentFilms(1, LocalDate.now(), Arrays.asList(1), 1, false);
        Store.returnFilms(LocalDate.now().plusDays(1), Arrays.asList(1));
        assertEquals(expectedPrice, Store.lastPrice);
    }

    @Test
    public void returnLate() {
        int expectedPrice = 4;
        Store.rentFilms(1, LocalDate.now(), Arrays.asList(1), 1, false);
        Store.returnFilms(LocalDate.now().plusDays(2), Arrays.asList(1));
        assertEquals(expectedPrice, Store.lastPrice);
    }

    @After
    public void tearDown() {
        Store.inventory.remove(1);
        Store.customers.remove(1);
        Store.customers.resetIdCounter();
        Store.inventory.resetIdCounter();
    }
}