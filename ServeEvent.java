public class ServeEvent extends Event {
    private final ServerClass server;

    public ServeEvent(Customer customer, ServerClass server) {
        super(customer); 
        this.server = server;
    }

    public ImList<ServerClass> updateSelfCheckout(ImList<ServerClass> servers) {
        int first = 0;
        for (int i = 0; i < servers.size(); i++) {
            if (!servers.get(i).isHuman()) { 
                first = i; //first self checkout 
                break;
            }
        }

        for (int i = first; i < servers.size(); i++) { //update self checkout
            ServerClass currServer = servers.get(i);
            servers = servers.set(i, currServer.removeQueue());
        }

        return servers;
    }

    @Override
    public Pair<Event, ImList<ServerClass>> execute(ImList<ServerClass> servers) {
        ServerClass currServer = servers.get(this.server.getServerID() - 1);
        double customerServeTime = this.customer.getArrivalTimes() 
            + this.customer.getServiceTimes();
        Customer customer = new Customer(this.customer.getCustomerNo(), 
            customerServeTime, 
            this.customer.getSupplierServiceTime());

        //update server separately for human and self checkout
        ServerClass updateServer = currServer.updateServe(customer);
        if (currServer.isHuman()) {
            //System.out.println("a");
            servers = servers.set(this.server.getServerID() - 1, updateServer);
        } else {
            servers = servers.set(this.server.getServerID() - 1, updateServer);
            servers = this.updateSelfCheckout(servers);
        }

        //set the next event to doneEvent
        DoneEvent doneEvent = new DoneEvent(customer, updateServer);
        return new Pair<>(doneEvent, servers);
    }
    
    @Override
    public String toString() {
        return String.format("%.03f", this.customer.getArrivalTimes()) 
            + " " + this.customer.getCustomerNo()
            + " serves by " + this.server.toString() + this.server.getServerID() + "\n"; 
    }

    @Override
    public boolean isServeEvent() {
        return true;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}