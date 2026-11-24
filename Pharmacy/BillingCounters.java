package Pharmacy;

public class BillingCounters implements Runnable{
    String countername;
    Medicine med;
    int qty;

    public BillingCounters(String countername,Medicine med,int qty){
        this.countername =  countername;
        this.med=med;
        this.qty=qty;
    }
    @Override
    public void run(){
        Thread.currentThread().setName(countername);
        try{
            System.out.println("["+countername+"]: waiting to sell medicine"+med.name+"qty = "+qty);
            med.sell(qty);
        }catch(ExpiredEx | OutOfStock e){
            System.out.println("["+countername+"] Unable to sell due to: "+e.getMessage());
        }
    }
}
