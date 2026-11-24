package Pharmacy;

public interface sellable {
    public void sell(int qty) throws ExpiredEx,OutOfStock;
}
