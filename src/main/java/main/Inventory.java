package main;

import main.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {

    private static int filmId;
    private List<Film> inventory;

    public Inventory() {
        filmId = 1;
        inventory = new ArrayList<>();
    }

    public void add(Film film) {
        film.setId(this.filmId++);
        inventory.add(film);
    }

    public void add(List<Film> films) {
        films.forEach(f -> add(f));
    }

    public void remove(int filmId) {
        inventory.removeIf(f -> f.getId() == filmId);
    }

    public void update(Film film) {
        Film result = inventory.stream().filter(f -> f.getId() == film.getId()).findFirst().get();
        result = film;
    }

    public Film getById(int filmId) {
        return inventory.stream().filter(f -> f.getId() == filmId).findFirst().get();
    }

    public List<Film> getByIds(List<Integer> filmsIds) {
        return inventory.stream().filter(f -> filmsIds.contains(f.getId())).collect(Collectors.toList());
    }

    public Film getByTitle(String title) {
        return inventory.stream().filter(f -> f.getTitle().equalsIgnoreCase(title)).findFirst().get();
    }

    public List<Film> getAll() {
        return this.inventory;
    }

    public List<Film> getAllNotRented() {
        return inventory.stream().filter(f -> f.getRentedOn()==null).collect(Collectors.toList());
    }

    public void resetIdCounter() {
        filmId = 1;
    }

    @Override
    public String toString() {
        return "main.Inventory{" +
                "inventory=" + inventory +
                '}';
    }

}