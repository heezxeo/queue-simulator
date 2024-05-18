import java.util.Comparator;

class PQCompare implements Comparator<Event> {
    public int compare(Event e1, Event e2) {
        double diff = e1.getCustomer().getArrivalTimes() - e2.getCustomer().getArrivalTimes();
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        } else { //tie breaking with customer number
            int result =  e1.getCustomer().getCustomerNo() - e2.getCustomer().getCustomerNo();
            if (result < 0) {
                return -1;
            } else if (result > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
