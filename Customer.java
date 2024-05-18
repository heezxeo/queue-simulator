import java.util.function.Supplier; 

class Customer {

    private final int customerNo;
    private final double arrivalTimes;
    private final double waitTime;
    private final Supplier<Double> serviceTimes;

    Customer(int customerNo, double arrivalTimes, Supplier<Double> serviceTimes) {
        this.customerNo = customerNo;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.waitTime = 0;
    }

    public double getWaitTime() {
        return this.waitTime;
    }

    public int getCustomerNo() {
        return this.customerNo;
    }

    public double getArrivalTimes() {
        return this.arrivalTimes;
    }

    public double getServiceTimes() {
        return this.serviceTimes.get();
    }

    public Supplier<Double> getSupplierServiceTime() {
        return this.serviceTimes;
    }

    public Customer updateWait(double time) {
        return new Customer(this.customerNo, 
            time, this.serviceTimes);
    }

    @Override
    public String toString() {
        return String.format("%.3f ", arrivalTimes) + "customer " + customerNo;
    }
}
