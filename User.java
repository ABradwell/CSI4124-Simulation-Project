public class User {

    private int service_time;
    private int severity;
    private long time_waited;
    private long time_arrived;

    private long last_time_waiting_started;

    public User() {
    }

    public User(int service_time, int severity, long time_waited, long time_arrived) {
        this.service_time = service_time;
        this.severity = severity;
        this.time_waited = time_waited;
        this.time_arrived = time_arrived;
    }

    public int getService_time() {
        return service_time;
    }

    public void setService_time(int service_time) {
        this.service_time = service_time;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public long getTime_waited() {
        return time_waited;
    }

    public void setTime_waited(long time_waited) {
        this.time_waited = time_waited;
    }

    public long getTime_arrived() {
        return time_arrived;
    }

    public void setTime_arrived(long time_arrived) {
        this.time_arrived = time_arrived;
    }

    public void start_waiting () {
        /**
         *  Resume time waiting counter by updating last_time_waiting_started
         */

    }

    public void stop_waiting () {
        /**
         * User no longer waiting, add the difference between last_time_waiting_started and current to time_waited
         */
    }
}
