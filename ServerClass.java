import java.util.function.Supplier;

public abstract class ServerClass {
    public abstract int getServerID();

    public abstract double getNextAvailableTime();

    public abstract boolean isHuman();

    public abstract double getRestTime();

    public abstract Supplier<Double> getSupplierRestTime();

    public abstract ImList<Customer> getQueue();

    public abstract ServerClass addQueue(Customer customer);

    public abstract ServerClass removeQueue();

    public abstract ServerClass updateTime(Customer customer);
    
    public abstract ServerClass updateServe(Customer customer);

    public abstract String toString();
}