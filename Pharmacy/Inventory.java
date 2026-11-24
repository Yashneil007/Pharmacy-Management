package Pharmacy;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Medicine> stock = new ArrayList<>();

    public Medicine searchmed(Medicine medicine) {
        for (Medicine m : stock) {
            if (m.name.equals(medicine.name)) {
                return m;
            }
        }
        return null;
    }

    public Medicine searchmed(String medName) {
        String cleanName = medName.trim().toLowerCase();
        for (Medicine m : stock) {
            if (m.name.equals(cleanName)) {
                return m;
            }
        }
        return null;
    }

    public void addStock(Medicine newMed) {
        Medicine exists = searchmed(newMed);
        if (exists != null) {
            exists.restock(newMed.quantity, newMed.price, newMed.expDate);
        } else {
            stock.add(newMed);
            System.out.println("Added new medicine: " + newMed.name + " | Quantity: " + newMed.quantity);
        }
    }
}
