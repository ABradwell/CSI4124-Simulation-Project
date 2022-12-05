
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.*;

public class PatientQueue {

    private int max_number_of_users;

    private int longest_queue_length;
    private int current_user_count;
    private int total_wait_time;

    private int total_time_waited = 0;
    private int time_empty = 0;

    //private Queue<User> users;
    private PriorityQueue<User> users = new PriorityQueue<>();

    public PatientQueue() {

        this.max_number_of_users = Integer.MAX_VALUE;
        this.current_user_count = 0;
        this.total_wait_time = 0;
        this.longest_queue_length = 0;
    }

    public PatientQueue(int max_number_of_users) {

        this.max_number_of_users = max_number_of_users;
        this.current_user_count = 0;
        this.total_wait_time = 0;
    }

    public PatientQueue(int max_number_of_users, int current_user_count, int total_wait_time, int longest_queue_length, PriorityQueue<User> users) {
        this.max_number_of_users = max_number_of_users;
        this.current_user_count = current_user_count;
        this.total_wait_time = total_wait_time;
        this.users = users;
        this.longest_queue_length = longest_queue_length;
    }

    public int getMax_number_of_users() {
        return max_number_of_users;
    }

    public void setMax_number_of_users(int max_number_of_users) {
        this.max_number_of_users = max_number_of_users;
    }

    public int getCurrent_user_count() {
        return current_user_count;
    }

    public void setCurrent_user_count(int current_user_count) {
        this.current_user_count = current_user_count;
    }

    public int getTotal_wait_time() {
        return total_wait_time;
    }

    public void setTotal_wait_time(int total_wait_time) {
        this.total_wait_time = total_wait_time;
    }

    public Queue<User> getUsers() {
        return users;
    }

    public void setUsers(PriorityQueue<User> users) {
        this.users = users;
    }

    public int getLongest_queue_length() {
        return longest_queue_length;
    }

    public void setLongest_queue_length(int longest_queue_length) {
        this.longest_queue_length = longest_queue_length;
    }

    public int getTime_empty() {
        return time_empty;
    }

    public void setTime_empty(int time_empty) {
        this.time_empty = time_empty;
    }

    public int getTotal_time_waited() {
        return total_time_waited;
    }

    public void setTotal_time_waited(int total_time_waited) {
        this.total_time_waited = total_time_waited;
    }

//    ------------------------------------------------------------

    public boolean room_for_more_patients() {

        return this.current_user_count < this.max_number_of_users;
    }

    public void add_user(User u) {
        /**
         * New patient arrives in this queue, updating the current variables accordingly
         *
         * @Param User: new user to be added to the queue
         */

        this.users.add(u);
        this.current_user_count += 1;

        if (this.current_user_count > this.longest_queue_length) {
            this.longest_queue_length = this.current_user_count;
        }
        this.total_wait_time += u.getService_time();

    }

    public User get_next_user() {
        /**
         * Take the next user waiting in line. Since patients are non-critical, it is a FIFO system queue.
         *
         * @return User: User object of next person in line
         */
        User next_user = this.users.peek();
        if (next_user != null){
            this.users.remove();
            this.current_user_count -= 1;
            this.total_wait_time -= next_user.getService_time();

            return next_user;
        } else {
            return null;
        }
    }

    private long getTotalWaitTime() {
        long total_wait_times = 0;
        for (User usr: users) {
            total_wait_times += usr.getTime_waited();
        }
        return total_wait_times;
    }

    private long getTotalInterarrivalTime() {
        long total_interarrival_time = 0;
        for (User usr: users) {
            total_interarrival_time += usr.getInterarrivalTime();
        }
        return total_interarrival_time;
    }

    // Calculates total service time in of the queue given a severity.
    // If severity = 0, returns total service time of the entire queue
    private long getTotalServiceTime(int severity) {
        long total_service_times = 0;
        for (User usr: users) {
            if (severity == 0 || severity == usr.getSeverity()) {
                total_service_times += usr.getTime_serviced();
            }
        }
        return total_service_times;
    }

    // Calculates total time to doctor of the queue given a severity.
    // If severity = 0, returns total service time of the entire queue
    private long getTotalTimeToDoctor(int severity) {
        long total_time_to_doctor = 0;
        for (User usr: users) {
            if (severity == 0 || severity == usr.getSeverity()) {
                total_time_to_doctor += usr.getTimeToDoctor();
            }
        }
        return total_time_to_doctor;
    }

    // Calculates total time in the ED of the queue given a severity.
    // If severity = 0, returns total service time of the entire queue
    private long getTotalTimeInED(int severity) {
        long total_time_in_ED = 0;
        for (User usr: users) {
            if (severity == 0 || severity == usr.getSeverity()) {
                total_time_in_ED += usr.getTimeInED();
            }
        }
        return total_time_in_ED;
    }
    
    // Gets user count in of the queue given a severity.
    // If severity = 0, returns user count of the entire queue
    public long getUserCount(int severity) {
        long usr_count = 0;
        for (User usr: users) {
            if (severity == 0 || severity == usr.getSeverity()) {
                usr_count++;
            }
        }
        return usr_count;
    }

    public String toString() {
        String msg = "";
        if (getUserCount(0) != 0) {
            msg += "Total User Data:\n";
            msg += String.format("  Users Served: %s\n", getUserCount(0));
            msg += String.format("  Average Time To Doctor: %s\n", getTotalTimeToDoctor(0)/getUserCount(0));
            msg += String.format("  Average Time in ED: %s\n", getTotalTimeInED(0)/getUserCount(0));
            msg += String.format("  Average Interarrival Time: %s\n", getTotalInterarrivalTime()/getUserCount(0));

            msg += String.format("  Average Wait Time: %s\n", getTotalWaitTime()/getUserCount(0));
            msg += String.format("  Average Service Time: %s\n\n", getTotalServiceTime(0)/getUserCount(0));
        }
        else {
            msg += String.format("  No Users Were Served\n\n");
        }

        for (int i = 1; i <= 5; i++) {
            if (getUserCount(i) != 0) {
                msg += String.format("CTAS %s User Data:\n", i);
                msg += String.format("  Users Served: %s\n", getUserCount(i));
                msg += String.format("  Average Time To Doctor: %s\n", getTotalTimeToDoctor(i)/getUserCount(i));
                msg += String.format("  Average Time in ED: %s\n", getTotalTimeInED(i)/getUserCount(i));
                msg += String.format("  Average Service Time: %s\n\n", getTotalServiceTime(i)/getUserCount(i));
            }
            else {
                msg += String.format("No users at CTAS %s were served\n\n", i);
            }
        }

        return msg;
    }
}
