public class WaitEvent extends Event {
    private final ServerClass server;
    private final boolean secondWaitEvent;

    public WaitEvent(Customer customer, ServerClass server, boolean secondWaitEvent) {
        super(customer);
        this.server = server;
        this.secondWaitEvent = secondWaitEvent;
    }

    //find the first fastest self checkout
    public ServerClass firstSelfCheckout(ImList<ServerClass> servers) {
        int first = 0;
        int fast = 0;
        for (int i = 0; i < servers.size(); i++) {
            if (!servers.get(i).isHuman()) {
                first = i;
                break;
            }
        }

        for (int i = first + 1; i < servers.size(); i++) {
            fast = first;
            if (servers.get(i).getNextAvailableTime() < servers.get(fast).getNextAvailableTime()) {
                fast = i;
            }
        }
        return servers.get(fast);
    }

    @Override 
    public double getWaitTime(ImList<ServerClass> servers) {
        if (this.server.isHuman()) {
            double time = servers.get(this.server.getServerID() - 1).getNextAvailableTime();
            time = time - this.customer.getArrivalTimes();
            return time;
        } else {
            double time = this.firstSelfCheckout(servers).getNextAvailableTime();
            time = time - this.customer.getArrivalTimes();
            return time;
        }
    }
 

    @Override
    public Pair<Event, ImList<ServerClass>> execute(ImList<ServerClass> servers) {
        ServerClass currentServer = servers.get(server.getServerID() - 1); 
        double startTime = currentServer.getNextAvailableTime(); 

        if (currentServer.isHuman()) {
            if (this.customer.getArrivalTimes() >= startTime) {
                ServeEvent se = new ServeEvent(this.customer.updateWait(startTime), currentServer);
                return new Pair<>(se, servers);
            } else {
                WaitEvent we = new WaitEvent(this.customer.updateWait(startTime), 
                    currentServer, 
                    true);
                return new Pair<>(we, servers);
            }
        } else {
            ServerClass fast = this.firstSelfCheckout(servers);
            if (this.customer.getArrivalTimes() >= fast.getNextAvailableTime()) {
                ServeEvent se = new ServeEvent(
                    this.customer.updateWait(fast.getNextAvailableTime()), 
                    fast);
                return new Pair<>(se, servers);
            } else {
                WaitEvent we = new WaitEvent(
                    this.customer.updateWait(fast.getNextAvailableTime()), 
                    fast, 
                    true);
                return new Pair<>(we, servers);
            }
        }
    }

    @Override
    public String toString() {
        if (this.secondWaitEvent == false) {
            return String.format("%.3f", this.customer.getArrivalTimes()) + " "
                + this.customer.getCustomerNo() + " waits at " 
                + this.server.toString() + this.server.getServerID() + "\n";
        } else {
            return "";
        }
    }

    @Override 
    public boolean isWaitEvent() {
        return true;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
