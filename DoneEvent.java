public class DoneEvent extends Event {
    private final ServerClass server;

    public DoneEvent(Customer customer, ServerClass server) {
        super(customer); 
        this.server = server;
    }

    @Override
    public Pair<Event, ImList<ServerClass>> execute(ImList<ServerClass> servers) {
        int serverIndex = this.server.getServerID();
        ServerClass currServer = servers.get(serverIndex - 1);

        //update human server rest time 
        if (currServer.isHuman()) {
            double endTime = currServer.getRestTime() 
                + currServer.getNextAvailableTime();
            ServerClass updateServer = new Server(currServer.getServerID(),
                endTime,
                currServer.getQueue(),
                currServer.getSupplierRestTime());
            servers = servers.set(serverIndex - 1, updateServer);
        }
        NoneEvent noneEvent = new NoneEvent(customer);
        return new Pair<>(noneEvent, servers);
    }

    @Override
    public String toString() {
        return String.format("%.03f", this.customer.getArrivalTimes())
            + " " + this.customer.getCustomerNo()
            + " done serving by " + this.server.toString()
            + this.server.getServerID() + "\n";
    }

    @Override
    public boolean isDoneEvent() {
        return true;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}