package Pharmacy;

import java.time.LocalDate;

public class Tablets extends Medicine {
    public Tablets() {
        super();
    }

    public Tablets(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    public Tablets(String name, double price, int quantity, LocalDate expDate) {
        super(name, price, quantity, expDate);
    }

    public double calcGST() {
        return price * 0.05;
    }
}

