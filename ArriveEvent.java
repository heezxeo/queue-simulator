public class ArriveEvent extends Event {
    private final int qMax;

    public ArriveEvent(Customer customer, int qMax) {
        super(customer);
        this.qMax = qMax;
    }

    //update self checkout queue
    public ImList<ServerClass> updateSelfCheckout(ImList<ServerClass> servers) {
        int first = 0;
        for (int i = 0; i < servers.size(); i++) {
            if (!servers.get(i).isHuman()) { 
                first = i;
                break;
            }
        }

        //update all the following self checkout
        for (int i = first; i < servers.size(); i++) { 
            ServerClass currServer = servers.get(i);
            servers = servers.set(i, currServer.addQueue(customer));
        }

        return servers;
    }

    public Pair<Event, ImList<ServerClass>> execute(ImList<ServerClass> servers) {
        
        for (int i = 0; i < servers.size(); i++) {
            ServerClass currentServer = servers.get(i);

            if (this.customer.getArrivalTimes() >= currentServer.getNextAvailableTime()) {
                // If served, set the event to ServeEvent 
                ServeEvent serveEvent = new ServeEvent(this.customer, currentServer);
                return new Pair<>(serveEvent, servers);
            } 
            
        }

        //If there is empty place in queue, the event is set to WaitEvent 
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).getQueue().size() < this.qMax) {
                ServerClass pickServer = servers.get(i);

                //Human and self checkout update different server list (servers)
                if (pickServer.isHuman()) {
                    ServerClass updateServer = pickServer.addQueue(this.customer);
                    servers = servers.set(i, updateServer);
                } else {
                    servers = this.updateSelfCheckout(servers);
                }
                // System.out.println("Customer wait at "+ pickServer.getServerID());
                WaitEvent waitEvent = new WaitEvent(this.customer, pickServer, false);
                return new Pair<>(waitEvent, servers); 
            }
        }

        // If no server or queue available, set the event to LeaveEvent
        LeaveEvent leaveEvent = new LeaveEvent(this.customer);
        return new Pair<>(leaveEvent, servers);
    }

    public String toString() {
        return String.format("%.3f", this.customer.getArrivalTimes()) + " " 
            + this.customer.getCustomerNo() + " arrives" + "\n";
    }

    public boolean isTerminal() {
        return false;
    }
}
