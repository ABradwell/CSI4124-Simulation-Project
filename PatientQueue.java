
import java.util.LinkedList;
import java.util.Queue;

public class PatientQueue {

    private int max_number_of_users;
    private int current_user_count;
    private int total_wait_time;

    private Queue<User> users;

    public PatientQueue() {

        this.max_number_of_users = 150;
        this.current_user_count = 0;
        this.total_wait_time = 0;
        this.users = new LinkedList<>();
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

    public void add_user(User u) {
        /**
         * New patient arrives in this queue, updating the current variables accordingly
         *
         * @Param u: new user to be added to the queue
         */
        this.users.add(u);
        this.current_user_count += 1;
        //total_wait_time; ?
    }

    public User get_next_user() {
        /**
         * return next user waiting in line, if present. Otherwise null.
         */
        User next_user = this.users.peek();
        if (next_user != null){
            this.users.remove();
            this.current_user_count -= 1;
            return next_user;
        } else {
            return null;
        }
    }
}
