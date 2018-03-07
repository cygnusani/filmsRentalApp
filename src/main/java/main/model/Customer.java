package main.model;

public class Customer {

    private int id;
    private int bonusPoints;

    public Customer() {}

    public Customer(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    @Override
    public String toString() {
        return "main.model.Customer{" +
                "id=" + id +
                ", bonusPoints=" + bonusPoints +
                '}';
    }
}
