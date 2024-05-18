import java.util.function.Supplier;

public class Simulator {
    
    private final int numOfServer;
    private final int numSelfCheckouts;
    private final int qMax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> rest;

    public Simulator(int numOfServer, int numSelfCheckouts, int qMax, 
        ImList<Double> arrivalTimes, Supplier<Double> serviceTimes,
        Supplier<Double> rest) {
        this.numOfServer = numOfServer;
        this.numSelfCheckouts = numSelfCheckouts;
        this.qMax = qMax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.rest = rest;
    }

    //create server list  
    public ImList<ServerClass> addServers() {
        ImList<ServerClass> servers = new ImList<ServerClass>();
        for (int i = 0; i < this.numOfServer; i++) {
            servers = servers.add(new Server(i + 1, 0.0, 
                new ImList<Customer>(), this.rest));
        }

        for (int i = 1; i <= this.numSelfCheckouts; i++) {
            servers = servers.add(new SelfCheckout(this.numOfServer + i,
             0.0, new ImList<Customer>()));
        }
        return servers;
    }

    public String simulate() {
        double averageWaitTime = 0;
        int customerServe = 0;
        int customerLeave = 0;
        String result = "";

        ImList<ServerClass> servers = this.addServers();
        //System.out.println(servers);
        PQ<Event> events = new PQ<Event>(new PQCompare());

        // Populate the queue with arriveEvents
        for (int i = 0; i < arrivalTimes.size(); i++) {
            events = events.add(new ArriveEvent(
                new Customer(i + 1, arrivalTimes.get(i), this.serviceTimes),
                this.qMax));
        }

        while (!events.isEmpty()) {
            Pair<Event, PQ<Event>> polled = events.poll(); // get first event
            Event currentEvent = polled.first();
            events = polled.second();
    
            result += currentEvent.toString();

            if (currentEvent.isLeaveEvent()) {
                customerLeave++;
            } else if (currentEvent.isDoneEvent()) {
                customerServe++;
            } else if (currentEvent.isWaitEvent()) {
                averageWaitTime += currentEvent.getWaitTime(servers);
            } 
            
            if (currentEvent.isTerminal() == false) {
                Pair<Event, ImList<ServerClass>> p = currentEvent.execute(servers);
                Event nextEvent = p.first(); //update new event 
                servers = p.second(); // update the server list
                events = events.add(nextEvent);
            }
        }
        
        averageWaitTime = averageWaitTime / customerServe;
        result += String.format("[%.3f %d %d]", averageWaitTime, customerServe, customerLeave);
        return result; 
    }
}

