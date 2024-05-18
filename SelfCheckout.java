import java.util.function.Supplier;

public class SelfCheckout extends ServerClass {
    private final int serverID;
    private final double nextAvailableTime;
    private final ImList<Customer> queue;

    SelfCheckout(int serverID, double nextAvailableTime, ImList<Customer> queue) {
        this.serverID = serverID;
        this.nextAvailableTime = nextAvailableTime;
        this.queue = queue;
    }

    public int getServerID() {
        return this.serverID;
    }

    public double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    public boolean isHuman() {
        return false;
    }

    public double getRestTime() {
        return 0;
    }

    public Supplier<Double> getSupplierRestTime() {
        return () -> 0.0; // or another default value
    }

    public ImList<Customer> getQueue() {
        return this.queue;
    }

    public ServerClass addQueue(Customer customer) {
        ImList<Customer> q = this.queue.add(customer);
        return new SelfCheckout(this.serverID, this.nextAvailableTime, q);
    }

    public ServerClass removeQueue() {
        ImList<Customer> q = this.queue.remove(0);
        return new SelfCheckout(this.serverID, this.nextAvailableTime, q);
    }

    public ServerClass updateTime(Customer customer) {
        double time = this.nextAvailableTime + customer.getArrivalTimes();
        return new SelfCheckout(this.serverID, time, this.queue);
    }

    public ServerClass updateServe(Customer customer) {
        return new SelfCheckout(this.serverID, customer.getArrivalTimes(), this.queue);
    }

    public String toString() {
        return "self-check ";
    }
}
