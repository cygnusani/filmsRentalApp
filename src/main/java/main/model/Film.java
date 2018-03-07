package main.model;

import main.FilmType;

import java.time.LocalDate;

public class Film {

    private int id;
    private String title;
    private FilmType type;
    private int customerId;
    private LocalDate rentedOn;
    private LocalDate returnOn;

    public Film() {
    }

    public Film(String title, FilmType type) {
        this.title = title;
        this.type = type;
        this.customerId = -1;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FilmType getFilmType() {
        return this.type;
    }

    public void setFilmType(FilmType type) {
        this.type = type;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getRentedOn() {
        return this.rentedOn;
    }

    public void setRentedOn(LocalDate rentedOn) {
        this.rentedOn = rentedOn;
    }

    public LocalDate getReturnOn() {
        return this.returnOn;
    }

    public void setReturnOn(LocalDate returnOn) {
        this.returnOn = returnOn;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", customerId=" + customerId +
                ", rentedOn=" + rentedOn +
                ", returnOn=" + returnOn +
                '}';
    }
}