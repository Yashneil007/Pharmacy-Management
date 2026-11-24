package Pharmacy;

import java.time.LocalDate;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Medicine implements sellable {
    String name;
    double price;
    LocalDate expDate;
    int quantity;
    private final ReentrantLock mu = new ReentrantLock();

    public Medicine(String name, double price, int quantity) {
        this.name = name.trim().toLowerCase();
        this.price = price;
        this.quantity = quantity;
    }

    public Medicine() {}

    public Medicine(String name, double price, int quantity, LocalDate expDate) {
        this.name = name.trim().toLowerCase();
        this.price = price;
        this.quantity = quantity;
        this.expDate = expDate;
    }

    public abstract double calcGST();

    @Override
    public void sell(int qty) throws ExpiredEx, OutOfStock {
        mu.lock();
        try {
            if (expDate != null && expDate.isBefore(LocalDate.now())) {
                throw new ExpiredEx(name + " is Expired (Expiry Date: " + expDate + ")");
            }
            if (qty > quantity) {
                throw new OutOfStock(name + " is Out Of stock");
            }
            quantity -= qty;
            double gst = calcGST() * qty;
            double total = gst + (qty * price);
            System.out.println("Sold " + qty + " of " + name + "\nGST: " + gst + "\nPrice: " + (qty * price) + "\nTotal: " + total);
        } finally {
            mu.unlock();
        }
    }

    public void restock(int addedQty, double newPrice, LocalDate newExpDate) {
        mu.lock();
        try {
            this.quantity += addedQty;
            this.price = newPrice;
            this.expDate = newExpDate;
            System.out.println("Restocked " + name + " | New Total Quantity: " + quantity);
        } finally {
            mu.unlock();
        }
    }
}

