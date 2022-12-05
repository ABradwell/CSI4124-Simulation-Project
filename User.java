import java.util.*;

public class User implements Comparable<User> {

    private int service_time; // service time required by doctor
    private int severity;
    private int interarrival_time;
    private long priority;
    private long time_arrived;
    private long time_waited;
    private long time_serviced;

    private long time_in_ED = -1;
    private long time_to_doctor = -1;
    private long last_time_waiting_started = -1;
    private long last_time_servicing_started = -1;

    public User() {
    }

    public User(int severity, int interarrival_time, long time_arrived, long priority) {
        this.severity = severity;
        this.interarrival_time = interarrival_time;
        this.time_arrived = time_arrived;
        this.priority = priority;
    }

    @Override
    public int compareTo(User u) {
        return u.priority > this.priority ? -1 : 1;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
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

    public int getInterarrivalTime() {
        return interarrival_time;
    }

    public void setInterarrivalTime(int interarrival_time) {
        this.interarrival_time = interarrival_time;
    }

    public long getTime_waited() {
        return time_waited;
    }

    public void setTime_waited(long time_waited) {
        this.time_waited = time_waited;
    }

    public long getTime_serviced() {
        return time_serviced;
    }

    public void setTime_serviced(long time_serviced) {
        this.time_serviced = time_serviced;
    }

    public long getTime_arrived() {
        return time_arrived;
    }

    public void setTime_arrived(long time_arrived) {
        this.time_arrived = time_arrived;
    }

    public long getTimeToDoctor() {
        return time_to_doctor;
    }

    public void setTimeToDoctor(long curr_time) {
        this.time_to_doctor = curr_time - time_arrived;
    }

    public long getTimeInED() {
        return time_in_ED;
    }

    public void setTimeInED(long time_discharged) {
        this.time_in_ED = time_discharged - time_arrived;
    }

    public void start_waiting (int curr_time) {
        /**
         *  Resume time waiting counter by updating last_time_waiting_started
         */
        if (last_time_servicing_started >= 0) {
            time_serviced += (curr_time - last_time_servicing_started);
        }
        last_time_waiting_started = curr_time;

    }

    public void start_service (int curr_time) {
        /**
         * User no longer waiting, add the difference between last_time_waiting_started and current to time_waited
         */

        if (last_time_waiting_started >= 0) {
            time_waited += (curr_time - last_time_waiting_started);
        }
        last_time_servicing_started = curr_time;
    }
}
