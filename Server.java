import java.util.function.Supplier;

class Server extends ServerClass {
    private final int serverID;
    private final double nextAvailableTime;
    private final ImList<Customer> queue;
    private final Supplier<Double> rest;

    Server(int serverID, double nextAvailableTime, ImList<Customer> queue, Supplier<Double> rest) {
        this.serverID = serverID;
        this.nextAvailableTime = nextAvailableTime;
        this.queue = queue;
        this.rest = rest;
    }

    public int getServerID() {
        return this.serverID;
    }

    public double getNextAvailableTime() {
        return this.nextAvailableTime;
    }
    
    public boolean isHuman() {
        return true;
    }

    public double getRestTime() {
        return this.rest.get();
    }

    public Supplier<Double> getSupplierRestTime() {
        return this.rest;
    }

    public ImList<Customer> getQueue() {
        return this.queue;
    }

    public ServerClass addQueue(Customer customer) {
        ImList<Customer> queue = this.queue.add(customer);
        return new Server(this.serverID, this.nextAvailableTime, queue, this.rest);
    }
    
    public ServerClass removeQueue() {
        ImList<Customer> queue = this.queue.remove(0);
        return new Server(this.serverID, this.nextAvailableTime, queue, this.rest);
    }
    
    public ServerClass updateTime(Customer customer) {
        double time = this.nextAvailableTime + customer.getServiceTimes();
        return new Server(this.serverID, time, this.queue, this.rest);
    }

    public ServerClass updateServe(Customer customer) {
        double time = customer.getArrivalTimes();
        ImList<Customer> queue = this.queue.remove(0);
        return new Server(this.serverID, time, queue, this.rest);
    }

    public String toString() {
        return "";
    }
}
