package Pharmacy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inventory inventory = new Inventory();

        // 1. Create the Billing Counters first
        System.out.print("Enter number of Billing Counters: ");
        int n = sc.nextInt();
        sc.nextLine();

        ArrayList<String> counterNames = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter name for Counter " + i + ": ");
            counterNames.add(sc.nextLine());
        }

        // 2. Select a Counter -> Perform the 4 Operations
        while (true) {
            System.out.println("\n=== BILLING COUNTERS ===");
            for (int i = 0; i < counterNames.size(); i++) {
                System.out.println((i + 1) + ". " + counterNames.get(i));
            }
            System.out.println((counterNames.size() + 1) + ". Sell Simultaneously Across All Counters (Thread Test)");
            System.out.println((counterNames.size() + 2) + ". Exit");
            System.out.print("Select option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == counterNames.size() + 2) {
                System.out.println("Exiting...");
                break;
            }

            // Simultaneous Multithreaded Sale across all counters
            if (choice == counterNames.size() + 1) {
                ArrayList<Thread> threads = new ArrayList<>();
                for (String cName : counterNames) {
                    System.out.println("\n--- [" + cName + "] Sale Order ---");
                    System.out.print("Enter Medicine Name to sell: ");
                    String query = sc.nextLine();
                    System.out.print("Enter Quantity to sell: ");
                    int qty = sc.nextInt();
                    sc.nextLine();

                    Medicine med = inventory.searchmed(query);
                    if (med != null) {
                        threads.add(new Thread(new BillingCounters(cName, med, qty)));
                    } else {
                        System.out.println("[" + cName + "] Medicine not found!");
                    }
                }

                System.out.println("\n--- Starting All Billing Counter Threads ---");
                for (Thread t : threads) t.start();
                for (Thread t : threads) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted: " + e.getMessage());
                    }
                }
                continue;
            }

            // Single Counter Internal Menu (All 4 Operations)
            if (choice >= 1 && choice <= counterNames.size()) {
                String activeCounter = counterNames.get(choice - 1);

                System.out.println("\n--- [" + activeCounter + "] MENU ---");
                System.out.println("1. Add Medicine");
                System.out.println("2. Restock Medicine");
                System.out.println("3. Search Medicine");
                System.out.println("4. Sell Medicine");
                System.out.print("Enter operation: ");
                int op = sc.nextInt();
                sc.nextLine();

                switch (op) {
                    case 1:
                        System.out.print("Type (1.Tablets  2.Syrups  3.Injections): ");
                        int type = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Expiry Date (YYYY-MM-DD): ");
                        LocalDate expDate = LocalDate.parse(sc.nextLine().trim());

                        Medicine med = (type == 1) ? new Tablets(name, price, qty, expDate)
                                : (type == 2) ? new Syrups(name, price, qty, expDate)
                                : new Injections(name, price, qty, expDate);

                        inventory.addStock(med);
                        break;

                    case 2:
                        System.out.print("Enter Medicine Name to restock: ");
                        String rQuery = sc.nextLine();
                        Medicine medToRestock = inventory.searchmed(rQuery);
                        if (medToRestock != null) {
                            System.out.print("Quantity to add: ");
                            int addQty = sc.nextInt();
                            System.out.print("New Price: ");
                            double newPrice = sc.nextDouble();
                            sc.nextLine();
                            System.out.print("New Expiry Date (YYYY-MM-DD): ");
                            LocalDate newExpDate = LocalDate.parse(sc.nextLine().trim());

                            medToRestock.restock(addQty, newPrice, newExpDate);
                        } else {
                            System.out.println("Medicine not found.");
                        }
                        break;

                    case 3:
                        System.out.print("Enter Medicine Name to search: ");
                        String sQuery = sc.nextLine();
                        Medicine found = inventory.searchmed(sQuery);
                        if (found != null) {
                            System.out.println("Found -> Name: " + found.name
                                    + " | Price: " + found.price
                                    + " | Qty: " + found.quantity
                                    + " | Expiry Date: " + found.expDate);
                        } else {
                            System.out.println("Medicine not found.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Medicine Name to sell: ");
                        String sellQuery = sc.nextLine();
                        System.out.print("Quantity to sell: ");
                        int sellQty = sc.nextInt();
                        sc.nextLine();

                        Medicine medToSell = inventory.searchmed(sellQuery);
                        if (medToSell != null) {
                            Thread t = new Thread(new BillingCounters(activeCounter, medToSell, sellQty));
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException e) {
                                System.out.println("Interrupted: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Medicine not found.");
                        }
                        break;

                    default:
                        System.out.println("Invalid operation.");
                }
            } else {
                System.out.println("Invalid counter selection.");
            }
        }
        sc.close();
    }
}
