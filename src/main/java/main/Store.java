package main;

import main.model.Customer;
import main.model.Film;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Store {

    public static Inventory inventory = new Inventory();
    public static Customers customers = new Customers();

    private static final int PremiumPrice = 4;
    private static final int BasicPrice = 3;

    // Helper for tests
    public static int lastPrice;

    public static void main(String[] args) {
        insertDummyData();

        // Customer with ID=1 rents films with IDs=[1, 4, 8] for 7 days
        rentFilms(1, LocalDate.now(), Arrays.asList(1, 4, 8), 7, false);
        System.out.println("------------------------------------------------------------------------------");
        // Customer with ID=1 returns films with IDs=[1, 4] after 7 days
        returnFilms(LocalDate.now().plusDays(7), Arrays.asList(1, 4));
        // Customer with ID=1 returns a film with ID=8 after 10 days (3 extra days)
        returnFilms(LocalDate.now().plusDays(10), Arrays.asList(8));

        System.out.println("------------------------------------------------------------------------------");

        // Customer with ID=2 rents films with IDs=[2, 5, 6, 9] for 5 days and uses bonus points
        rentFilms(2, LocalDate.now(), Arrays.asList(2, 5, 6, 9), 5, true);
        System.out.println("------------------------------------------------------------------------------");
        // Customer with ID=2 returns a film with ID=2 after 5 days
        returnFilms(LocalDate.now().plusDays(5), Arrays.asList(2));
        // Customer with ID=2 returns rest of the films after 7 days (2 extra days)
        returnFilms(LocalDate.now().plusDays(7), Arrays.asList(5, 6, 9));

        System.out.println("------------------------------------------------------------------------------");

        // Customer with ID=3 rents a film with ID=3 for 4 days and uses bonus points
        rentFilms(3, LocalDate.now(), Arrays.asList(3), 4, true);
        System.out.println("------------------------------------------------------------------------------");
        // Customer with ID=3 returns the film after 5 days
        returnFilms(LocalDate.now().plusDays(4), Arrays.asList(3));

        System.out.println("------------------------------------------------------------------------------");

        // Add a film
        inventory.add(new Film("Black Panther", FilmType.New_Release));

        // Remove a film
        inventory.remove(6);

        // Change the type of a film
        Film toUpdate = inventory.getByTitle("The Shape of Water");
        toUpdate.setFilmType(FilmType.Regular);
        inventory.update(toUpdate);

        // List all films
        for(Film film: inventory.getAll()) {
            System.out.println(film.toString());
        }

        System.out.println("");

        // List all films in store (e.g. not rented at the moment)
        for(Film film: inventory.getAllNotRented()) {
            System.out.println(film.toString());
        }
    }

    public static void insertDummyData() {
        inventory.add(new Film("The Shape of Water", FilmType.New_Release));
        inventory.add(new Film("Ready Player One", FilmType.New_Release));
        inventory.add(new Film("Game Night", FilmType.New_Release));
        inventory.add(new Film("Thor: Ragnarok", FilmType.Regular));
        inventory.add(new Film("Blade Runner 2049", FilmType.Regular));
        inventory.add(new Film("Only the Brave", FilmType.Regular));
        inventory.add(new Film("The Internship", FilmType.Regular));
        inventory.add(new Film("Ghost Busters", FilmType.Old));
        inventory.add(new Film("The Fifth Element", FilmType.Old));
        inventory.add(new Film("Forrest Gump", FilmType.Old));
        customers.add(new Customer(7));
        customers.add(new Customer(26));
        customers.add(new Customer(101));
    }

    public static void rentFilms(int customerId, LocalDate rentedOnDate, List<Integer> rentedFilmsIds, int rentForNrOfDays, boolean usePoints) {
        Customer customer = customers.getById(customerId);
        int customersPoints = customer.getBonusPoints();
        int earnedPoints = 0;
        int priceTotal = 0;
        int filmPrice = 0;
        String msg;
        boolean usedPoints = false;
        List<Film> rentedFilms = inventory.getByIds(rentedFilmsIds);
        for (Film film: rentedFilms) {
            switch (film.getFilmType()) {
                case New_Release:
                    if (usePoints && customersPoints >= 25) {
                        usedPoints = true;
                        // Customer has enough points to pay for all days
                        if (rentForNrOfDays * 25 <= customersPoints) {
                            int nrOfDaysPaidWithBonusPoints = customersPoints / 25;
                            customersPoints -= rentForNrOfDays * 25;
                            msg = String.format("%s(%s) %d days (Paid with %d Bonus points)", film.getTitle(), film.getFilmType().toString().replace("_", " "), rentForNrOfDays, nrOfDaysPaidWithBonusPoints);
                            // Customer can pay for some of the days
                        } else {
                            int nrOfDaysPaidWithBonusPoints = customersPoints / 25;
                            customersPoints -= nrOfDaysPaidWithBonusPoints * 25;
                            filmPrice = (rentForNrOfDays - nrOfDaysPaidWithBonusPoints) * PremiumPrice;
                            msg = String.format("%s(%s) %d days (Paid with %d Bonus points) %d EUR", film.getTitle(), film.getFilmType().toString().replace("_", " "), rentForNrOfDays, nrOfDaysPaidWithBonusPoints, filmPrice);
                        }
                    } else {
                        filmPrice = rentForNrOfDays * PremiumPrice;
                        msg = String.format("%s(%s) %d days %d EUR", film.getTitle(), film.getFilmType().toString().replace("_", " "), rentForNrOfDays, filmPrice);
                    }
                    earnedPoints += 2;
                    break;
                default:
                    if (film.getFilmType() == FilmType.Regular) {
                        filmPrice = (rentForNrOfDays <= 3) ? BasicPrice : rentForNrOfDays * BasicPrice;
                    } else {
                        filmPrice = (rentForNrOfDays <= 5) ? BasicPrice : rentForNrOfDays * BasicPrice;
                    }
                    msg = String.format("%s(%s) %d days %d EUR", film.getTitle(), film.getFilmType().toString().replace("_", " "), rentForNrOfDays, filmPrice);
                    earnedPoints++;
                    break;
            }
            // Update film info
            film.setCustomerId(customerId);
            film.setRentedOn(rentedOnDate);
            film.setReturnOn(rentedOnDate.plusDays(rentForNrOfDays));
            inventory.update(film);
            priceTotal += filmPrice;
            System.out.println(msg);
        }
        customer.setBonusPoints(customersPoints + earnedPoints);
        customers.update(customer);
        if (usedPoints) {
            System.out.printf("\nRemaining bonus points %d", customersPoints);
        }
        System.out.printf("\nTotal price : %d EUR\n", priceTotal);
        lastPrice = priceTotal;
    }

    public static void returnFilms(LocalDate returnedOnDate, List<Integer> returnedFilmsIds) {
        int additionalPayTotal = 0;
        List<Film> returnedFilms = inventory.getByIds(returnedFilmsIds);
        for (Film film: returnedFilms) {
            long daysOver = DAYS.between(film.getReturnOn(), returnedOnDate);
            int filmPrice = 0;
            if (daysOver > 0) {
                switch (film.getFilmType()) {
                    case New_Release:
                        filmPrice = (int)daysOver * PremiumPrice;
                        break;
                    default:
                        filmPrice = (int)daysOver * BasicPrice;
                        break;
                }
                additionalPayTotal += filmPrice;
            }
            film.setCustomerId(-1);
            film.setRentedOn(null);
            film.setReturnOn(null);
            inventory.update(film);
            System.out.printf("\n%s(%s) %d extra days %d EUR", film.getTitle(), film.getFilmType().toString().replace("_", " "), daysOver, filmPrice);
        }
        System.out.printf("\n\nTotal price : %d EUR\n", additionalPayTotal);
        lastPrice = additionalPayTotal;
    }
}
