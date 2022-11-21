
import java.util.LinkedList;
import java.util.Queue;

public class PatientQueue {

    private int max_number_of_users;
    private int current_user_count;
    private int total_wait_time;


    private Queue<User> users;

    public PatientQueue() {

        this.max_number_of_users = Integer.MAX_VALUE;
        this.current_user_count = 0;
        this.total_wait_time = 0;
        this.users = new LinkedList<User>();
    }

    public PatientQueue(int max_number_of_users) {

        this.max_number_of_users = max_number_of_users;
        this.current_user_count = 0;
        this.total_wait_time = 0;
        this.users = new LinkedList<User>();
    }

    public PatientQueue(int max_number_of_users, int current_user_count, int total_wait_time, Queue<User> users) {
        this.max_number_of_users = max_number_of_users;
        this.current_user_count = current_user_count;
        this.total_wait_time = total_wait_time;
        this.users = users;
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

    public void setUsers(Queue<User> users) {
        this.users = users;
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

    public String toString() {

        String msg = "  Queue Data:\n";

        msg += String.format("      max_number_of_users: %s\n", max_number_of_users);
        msg += String.format("      current_user_count: %s\n", current_user_count);
        msg += String.format("      total_wait_time: %s\n", total_wait_time);

        return msg;
    }
}
