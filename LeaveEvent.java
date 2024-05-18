public class LeaveEvent extends Event {
    public LeaveEvent(Customer customer) {
        super(customer);
    }

    @Override
    public Pair<Event, ImList<ServerClass>> execute(ImList<ServerClass> servers) {
        return new Pair<>(this, servers);
    }

    @Override
    public String toString() {
        return String.format("%.3f %d leaves", 
                             this.customer.getArrivalTimes(), 
                             this.customer.getCustomerNo()) + "\n";
    }

    @Override
    public boolean isLeaveEvent() {
        return true;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}

