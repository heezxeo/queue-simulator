public class NoneEvent extends Event {

    public NoneEvent(Customer customer) {
        super(customer);
    }

    @Override
    public Pair<Event, ImList<ServerClass>> execute(ImList<ServerClass> servers) {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public boolean isNoneEvent() {
        return true;
    }

    @Override
    public String toString() {
        return "";
    }
}
