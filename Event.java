public abstract class Event {
    protected final Customer customer;

    public Event(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public int getCustomerNo() {
        return 0;
    }

    public boolean isTerminal() {
        return false;
    }

    public boolean isLeaveEvent() {
        return false;
    }

    public boolean isDoneEvent() {
        return false;
    }

    public boolean isWaitEvent() {
        return false;
    }

    public boolean isNoneEvent() {
        return false;
    }

    public boolean isServeEvent() {
        return false;
    }

    public double getWaitTime(ImList<ServerClass> servers) {
        return 0;
    }

    public abstract Pair<Event, ImList<ServerClass>> execute(ImList<ServerClass> servers);
}
