package Pharmacy;

import java.time.LocalDate;

public class Injections extends Medicine {
    public Injections() {
        super();
    }

    public Injections(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    public Injections(String name, double price, int quantity, LocalDate expDate) {
        super(name, price, quantity, expDate);
    }

    public double calcGST() {
        return price * 0.175;
    }
}

