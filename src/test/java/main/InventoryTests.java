package main;

import main.model.Film;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class InventoryTests {

    protected Inventory inventory = new Inventory();

    @Before
    public void setUp() {
        Film film = new Film("Awesome Movie", FilmType.New_Release);
        film.setRentedOn(LocalDate.now());
        inventory.add(film);
    }

    @Test
    public void add() {
        inventory.add(new Film());
        assertEquals(2, inventory.getAll().size());
    }

    @Test
    public void remove() {
        inventory.remove(1);
        assertEquals(0, inventory.getAll().size());
    }

    @Test
    public void update() {
        Film film = inventory.getById(1);
        film.setTitle("Awesome Movie Title");
        film.setFilmType(FilmType.Old);
        film.setRentedOn(LocalDate.now());
        film.setReturnOn(LocalDate.now().plusDays(3));
        film.setCustomerId(10);
        inventory.update(film);
        assertEquals(film, inventory.getById(1));
    }

    @Test
    public void getByIds() {
        inventory.add(Arrays.asList(new Film()));
        List<Film> searchResult = inventory.getByIds(Arrays.asList(1, 2));
        assertEquals(Arrays.asList(searchResult.get(0).getId(), searchResult.get(1).getId()), Arrays.asList(1, 2));
    }

    @Test
    public void getByTitle() {
        String filmTitle = "Awesome Movie";
        assertEquals(filmTitle, inventory.getByTitle(filmTitle).getTitle());
    }

    @Test
    public void getAll() {
        inventory.add(Arrays.asList(new Film()));
        assertEquals(2, inventory.getAll().size());
    }

    @Test
    public void getAllNotRented() {
        inventory.add(new Film());
        assertEquals(1, inventory.getAllNotRented().size());
    }
}