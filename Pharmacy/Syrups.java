package Pharmacy;

import java.time.LocalDate;

public class Syrups extends Medicine {
    public Syrups() {
        super();
    }

    public Syrups(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    public Syrups(String name, double price, int quantity, LocalDate expDate) {
        super(name, price, quantity, expDate);
    }

    public double calcGST() {
        return price * 0.075;
    }
}

